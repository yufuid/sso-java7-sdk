package com.yufu.idaas.sdk.bouncycastle.asn1.x500.style;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.AttributeTypeAndValue;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.RDN;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500NameBuilder;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500NameStyle;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;
import com.yufu.idaas.sdk.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by mac on 2017/1/18.
 */
public class IETFUtils {
    public IETFUtils() {
    }

    private static String unescape(String var0) {
        if(var0.length() == 0 || var0.indexOf(92) < 0 && var0.indexOf(34) < 0) {
            return var0.trim();
        } else {
            char[] var1 = var0.toCharArray();
            boolean var2 = false;
            boolean var3 = false;
            StringBuffer var4 = new StringBuffer(var0.length());
            byte var5 = 0;
            if(var1[0] == 92 && var1[1] == 35) {
                var5 = 2;
                var4.append("\\#");
            }

            boolean var6 = false;
            int var7 = 0;
            char var8 = 0;

            for(int var9 = var5; var9 != var1.length; ++var9) {
                char var10 = var1[var9];
                if(var10 != 32) {
                    var6 = true;
                }

                if(var10 == 34) {
                    if(!var2) {
                        var3 = !var3;
                    } else {
                        var4.append(var10);
                    }

                    var2 = false;
                } else if(var10 == 92 && !var2 && !var3) {
                    var2 = true;
                    var7 = var4.length();
                } else if(var10 != 32 || var2 || var6) {
                    if(var2 && isHexDigit(var10)) {
                        if(var8 != 0) {
                            var4.append((char)(convertHex(var8) * 16 + convertHex(var10)));
                            var2 = false;
                            var8 = 0;
                        } else {
                            var8 = var10;
                        }
                    } else {
                        var4.append(var10);
                        var2 = false;
                    }
                }
            }

            if(var4.length() > 0) {
                while(var4.charAt(var4.length() - 1) == 32 && var7 != var4.length() - 1) {
                    var4.setLength(var4.length() - 1);
                }
            }

            return var4.toString();
        }
    }

    private static boolean isHexDigit(char var0) {
        return 48 <= var0 && var0 <= 57 || 97 <= var0 && var0 <= 102 || 65 <= var0 && var0 <= 70;
    }

    private static int convertHex(char var0) {
        return 48 <= var0 && var0 <= 57?var0 - 48:(97 <= var0 && var0 <= 102?var0 - 97 + 10:var0 - 65 + 10);
    }

    public static RDN[] rDNsFromString(String var0, X500NameStyle var1) {
        X500NameTokenizer var2 = new X500NameTokenizer(var0);
        X500NameBuilder var3 = new X500NameBuilder(var1);

        while(true) {
            while(true) {
                while(var2.hasMoreTokens()) {
                    String var4 = var2.nextToken();
                    X500NameTokenizer var5;
                    String var7;
                    if(var4.indexOf(43) > 0) {
                        var5 = new X500NameTokenizer(var4, '+');
                        X500NameTokenizer var12 = new X500NameTokenizer(var5.nextToken(), '=');
                        var7 = var12.nextToken();
                        if(!var12.hasMoreTokens()) {
                            throw new IllegalArgumentException("badly formatted directory string");
                        }

                        String var13 = var12.nextToken();
                        ASN1ObjectIdentifier var9 = var1.attrNameToOID(var7.trim());
                        if(var5.hasMoreTokens()) {
                            Vector var10 = new Vector();
                            Vector var11 = new Vector();
                            var10.addElement(var9);
                            var11.addElement(unescape(var13));

                            while(var5.hasMoreTokens()) {
                                var12 = new X500NameTokenizer(var5.nextToken(), '=');
                                var7 = var12.nextToken();
                                if(!var12.hasMoreTokens()) {
                                    throw new IllegalArgumentException("badly formatted directory string");
                                }

                                var13 = var12.nextToken();
                                var9 = var1.attrNameToOID(var7.trim());
                                var10.addElement(var9);
                                var11.addElement(unescape(var13));
                            }

                            var3.addMultiValuedRDN(toOIDArray(var10), toValueArray(var11));
                        } else {
                            var3.addRDN(var9, unescape(var13));
                        }
                    } else {
                        var5 = new X500NameTokenizer(var4, '=');
                        String var6 = var5.nextToken();
                        if(!var5.hasMoreTokens()) {
                            throw new IllegalArgumentException("badly formatted directory string");
                        }

                        var7 = var5.nextToken();
                        ASN1ObjectIdentifier var8 = var1.attrNameToOID(var6.trim());
                        var3.addRDN(var8, unescape(var7));
                    }
                }

                return var3.build().getRDNs();
            }
        }
    }

    private static String[] toValueArray(Vector var0) {
        String[] var1 = new String[var0.size()];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (String)var0.elementAt(var2);
        }

        return var1;
    }

    private static ASN1ObjectIdentifier[] toOIDArray(Vector var0) {
        ASN1ObjectIdentifier[] var1 = new ASN1ObjectIdentifier[var0.size()];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (ASN1ObjectIdentifier)var0.elementAt(var2);
        }

        return var1;
    }

    public static String[] findAttrNamesForOID(ASN1ObjectIdentifier var0, Hashtable var1) {
        int var2 = 0;
        Enumeration var3 = var1.elements();

        while(var3.hasMoreElements()) {
            if(var0.equals(var3.nextElement())) {
                ++var2;
            }
        }

        String[] var6 = new String[var2];
        var2 = 0;
        Enumeration var4 = var1.keys();

        while(var4.hasMoreElements()) {
            String var5 = (String)var4.nextElement();
            if(var0.equals(var1.get(var5))) {
                var6[var2++] = var5;
            }
        }

        return var6;
    }

    public static ASN1ObjectIdentifier decodeAttrName(String var0, Hashtable var1) {
        if(Strings.toUpperCase(var0).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(var0.substring(4));
        } else if(var0.charAt(0) >= 48 && var0.charAt(0) <= 57) {
            return new ASN1ObjectIdentifier(var0);
        } else {
            ASN1ObjectIdentifier var2 = (ASN1ObjectIdentifier)var1.get(Strings.toLowerCase(var0));
            if(var2 == null) {
                throw new IllegalArgumentException("Unknown object id - " + var0 + " - passed to distinguished name");
            } else {
                return var2;
            }
        }
    }

    public static ASN1Encodable valueFromHexString(String var0, int var1) throws IOException {
        byte[] var2 = new byte[(var0.length() - var1) / 2];

        for(int var3 = 0; var3 != var2.length; ++var3) {
            char var4 = var0.charAt(var3 * 2 + var1);
            char var5 = var0.charAt(var3 * 2 + var1 + 1);
            var2[var3] = (byte)(convertHex(var4) << 4 | convertHex(var5));
        }

        return ASN1Primitive.fromByteArray(var2);
    }

    public static void appendRDN(StringBuffer var0, RDN var1, Hashtable var2) {
        if(var1.isMultiValued()) {
            AttributeTypeAndValue[] var3 = var1.getTypesAndValues();
            boolean var4 = true;

            for(int var5 = 0; var5 != var3.length; ++var5) {
                if(var4) {
                    var4 = false;
                } else {
                    var0.append('+');
                }

                appendTypeAndValue(var0, var3[var5], var2);
            }
        } else if(var1.getFirst() != null) {
            appendTypeAndValue(var0, var1.getFirst(), var2);
        }

    }

    public static void appendTypeAndValue(StringBuffer var0, AttributeTypeAndValue var1, Hashtable var2) {
        String var3 = (String)var2.get(var1.getType());
        if(var3 != null) {
            var0.append(var3);
        } else {
            var0.append(var1.getType().getId());
        }

        var0.append('=');
        var0.append(valueToString(var1.getValue()));
    }

    public static String valueToString(ASN1Encodable var0) {
        StringBuffer var1 = new StringBuffer();
        if(var0 instanceof ASN1String && !(var0 instanceof DERUniversalString)) {
            String var2 = ((ASN1String)var0).getString();
            if(var2.length() > 0 && var2.charAt(0) == 35) {
                var1.append("\\" + var2);
            } else {
                var1.append(var2);
            }
        } else {
            try {
                var1.append("#" + bytesToString(Hex.encode(var0.toASN1Primitive().getEncoded("DER"))));
            } catch (IOException var6) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        }

        int var7 = var1.length();
        int var3 = 0;
        if(var1.length() >= 2 && var1.charAt(0) == 92 && var1.charAt(1) == 35) {
            var3 += 2;
        }

        for(; var3 != var7; ++var3) {
            if(var1.charAt(var3) == 44 || var1.charAt(var3) == 34 || var1.charAt(var3) == 92 || var1.charAt(var3) == 43 || var1.charAt(var3) == 61 || var1.charAt(var3) == 60 || var1.charAt(var3) == 62 || var1.charAt(var3) == 59) {
                var1.insert(var3, "\\");
                ++var3;
                ++var7;
            }
        }

        int var4 = 0;
        if(var1.length() > 0) {
            while(var1.length() > var4 && var1.charAt(var4) == 32) {
                var1.insert(var4, "\\");
                var4 += 2;
            }
        }

        for(int var5 = var1.length() - 1; var5 >= 0 && var1.charAt(var5) == 32; --var5) {
            var1.insert(var5, '\\');
        }

        return var1.toString();
    }

    private static String bytesToString(byte[] var0) {
        char[] var1 = new char[var0.length];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (char)(var0[var2] & 255);
        }

        return new String(var1);
    }

    public static String canonicalize(String var0) {
        String var1 = Strings.toLowerCase(var0);
        if(var1.length() > 0 && var1.charAt(0) == 35) {
            ASN1Primitive var2 = decodeObject(var1);
            if(var2 instanceof ASN1String) {
                var1 = Strings.toLowerCase(((ASN1String)var2).getString());
            }
        }

        if(var1.length() > 1) {
            int var4;
            for(var4 = 0; var4 + 1 < var1.length() && var1.charAt(var4) == 92 && var1.charAt(var4 + 1) == 32; var4 += 2) {
                ;
            }

            int var3;
            for(var3 = var1.length() - 1; var3 - 1 > 0 && var1.charAt(var3 - 1) == 92 && var1.charAt(var3) == 32; var3 -= 2) {
                ;
            }

            if(var4 > 0 || var3 < var1.length() - 1) {
                var1 = var1.substring(var4, var3 + 1);
            }
        }

        var1 = stripInternalSpaces(var1);
        return var1;
    }

    private static ASN1Primitive decodeObject(String var0) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decode(var0.substring(1)));
        } catch (IOException var2) {
            throw new IllegalStateException("unknown encoding in name: " + var2);
        }
    }

    public static String stripInternalSpaces(String var0) {
        StringBuffer var1 = new StringBuffer();
        if(var0.length() != 0) {
            char var2 = var0.charAt(0);
            var1.append(var2);

            for(int var3 = 1; var3 < var0.length(); ++var3) {
                char var4 = var0.charAt(var3);
                if(var2 != 32 || var4 != 32) {
                    var1.append(var4);
                }

                var2 = var4;
            }
        }

        return var1.toString();
    }

    public static boolean rDNAreEqual(RDN var0, RDN var1) {
        if(var0.isMultiValued()) {
            if(var1.isMultiValued()) {
                AttributeTypeAndValue[] var2 = var0.getTypesAndValues();
                AttributeTypeAndValue[] var3 = var1.getTypesAndValues();
                if(var2.length != var3.length) {
                    return false;
                } else {
                    for(int var4 = 0; var4 != var2.length; ++var4) {
                        if(!atvAreEqual(var2[var4], var3[var4])) {
                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        } else {
            return !var1.isMultiValued()?atvAreEqual(var0.getFirst(), var1.getFirst()):false;
        }
    }

    private static boolean atvAreEqual(AttributeTypeAndValue var0, AttributeTypeAndValue var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 == null) {
            return false;
        } else if(var1 == null) {
            return false;
        } else {
            ASN1ObjectIdentifier var2 = var0.getType();
            ASN1ObjectIdentifier var3 = var1.getType();
            if(!var2.equals(var3)) {
                return false;
            } else {
                String var4 = canonicalize(valueToString(var0.getValue()));
                String var5 = canonicalize(valueToString(var1.getValue()));
                return var4.equals(var5);
            }
        }
    }
}

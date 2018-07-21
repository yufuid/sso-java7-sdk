package com.yufu.idaas.sdk.bouncycastle.openssl.jcajce;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.yufu.idaas.sdk.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.yufu.idaas.sdk.bouncycastle.openssl.EncryptionException;
import com.yufu.idaas.sdk.bouncycastle.openssl.PEMException;
import com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util.JcaJceHelper;
import com.yufu.idaas.sdk.bouncycastle.util.Integers;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mac on 2017/1/18.
 */
public class PEMUtilities {
    private static final Map KEYSIZES = new HashMap();
    private static final Set PKCS5_SCHEME_1 = new HashSet();
    private static final Set PKCS5_SCHEME_2 = new HashSet();

    PEMUtilities() {
    }

    static int getKeySize(String var0) {
        if(!KEYSIZES.containsKey(var0)) {
            throw new IllegalStateException("no key size for algorithm: " + var0);
        } else {
            return ((Integer)KEYSIZES.get(var0)).intValue();
        }
    }

    static boolean isPKCS5Scheme1(ASN1ObjectIdentifier var0) {
        return PKCS5_SCHEME_1.contains(var0);
    }

    static boolean isPKCS5Scheme2(ASN1ObjectIdentifier var0) {
        return PKCS5_SCHEME_2.contains(var0);
    }

    public static boolean isPKCS12(ASN1ObjectIdentifier var0) {
        return var0.getId().startsWith(PKCSObjectIdentifiers.pkcs_12PbeIds.getId());
    }

    public static SecretKey generateSecretKeyForPKCS5Scheme2(JcaJceHelper var0, String var1, char[] var2, byte[] var3, int var4) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory var5 = var0.createSecretKeyFactory("PBKDF2with8BIT");
        SecretKey var6 = var5.generateSecret(new PBEKeySpec(var2, var3, var4, getKeySize(var1)));
        return new SecretKeySpec(var6.getEncoded(), var1);
    }

    static byte[] crypt(boolean var0, JcaJceHelper var1, byte[] var2, char[] var3, String var4, byte[] var5) throws PEMException {
        Object var6 = new IvParameterSpec(var5);
        String var8 = "CBC";
        String var9 = "PKCS5Padding";
        if(var4.endsWith("-CFB")) {
            var8 = "CFB";
            var9 = "NoPadding";
        }

        if(var4.endsWith("-ECB") || "DES-EDE".equals(var4) || "DES-EDE3".equals(var4)) {
            var8 = "ECB";
            var6 = null;
        }

        if(var4.endsWith("-OFB")) {
            var8 = "OFB";
            var9 = "NoPadding";
        }

        String var7;
        SecretKey var10;
        if(var4.startsWith("DES-EDE")) {
            var7 = "DESede";
            boolean var11 = !var4.startsWith("DES-EDE3");
            var10 = getKey(var1, var3, var7, 24, var5, var11);
        } else if(var4.startsWith("DES-")) {
            var7 = "DES";
            var10 = getKey(var1, var3, var7, 8, var5);
        } else if(var4.startsWith("BF-")) {
            var7 = "Blowfish";
            var10 = getKey(var1, var3, var7, 16, var5);
        } else if(var4.startsWith("RC2-")) {
            var7 = "RC2";
            short var15 = 128;
            if(var4.startsWith("RC2-40-")) {
                var15 = 40;
            } else if(var4.startsWith("RC2-64-")) {
                var15 = 64;
            }

            var10 = getKey(var1, var3, var7, var15 / 8, var5);
            if(var6 == null) {
                var6 = new RC2ParameterSpec(var15);
            } else {
                var6 = new RC2ParameterSpec(var15, var5);
            }
        } else {
            if(!var4.startsWith("AES-")) {
                throw new EncryptionException("unknown encryption with private key");
            }

            var7 = "AES";
            byte[] var16 = var5;
            if(var5.length > 8) {
                var16 = new byte[8];
                System.arraycopy(var5, 0, var16, 0, 8);
            }

            short var12;
            if(var4.startsWith("AES-128-")) {
                var12 = 128;
            } else if(var4.startsWith("AES-192-")) {
                var12 = 192;
            } else {
                if(!var4.startsWith("AES-256-")) {
                    throw new EncryptionException("unknown AES encryption with private key");
                }

                var12 = 256;
            }

            var10 = getKey(var1, var3, "AES", var12 / 8, var16);
        }

        String var18 = var7 + "/" + var8 + "/" + var9;

        try {
            Cipher var17 = var1.createCipher(var18);
            int var13 = var0?1:2;
            if(var6 == null) {
                var17.init(var13, var10);
            } else {
                var17.init(var13, var10, (AlgorithmParameterSpec)var6);
            }

            return var17.doFinal(var2);
        } catch (Exception var14) {
            throw new EncryptionException("exception using cipher - please check password and data.", var14);
        }
    }

    private static SecretKey getKey(JcaJceHelper var0, char[] var1, String var2, int var3, byte[] var4) throws PEMException {
        return getKey(var0, var1, var2, var3, var4, false);
    }

    private static SecretKey getKey(JcaJceHelper var0, char[] var1, String var2, int var3, byte[] var4, boolean var5) throws PEMException {
        try {
            PBEKeySpec var6 = new PBEKeySpec(var1, var4, 1, var3 * 8);
            SecretKeyFactory var7 = var0.createSecretKeyFactory("PBKDF-OpenSSL");
            byte[] var8 = var7.generateSecret(var6).getEncoded();
            if(var5 && var8.length >= 24) {
                System.arraycopy(var8, 0, var8, 16, 8);
            }

            return new SecretKeySpec(var8, var2);
        } catch (GeneralSecurityException var9) {
            throw new PEMException("Unable to create OpenSSL PBDKF: " + var9.getMessage(), var9);
        }
    }

    static {
        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
        PKCS5_SCHEME_2.add(PKCSObjectIdentifiers.id_PBES2);
        PKCS5_SCHEME_2.add(PKCSObjectIdentifiers.des_EDE3_CBC);
        PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes128_CBC);
        PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes192_CBC);
        PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes256_CBC);
        KEYSIZES.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), Integers.valueOf(192));
        KEYSIZES.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), Integers.valueOf(128));
        KEYSIZES.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), Integers.valueOf(192));
        KEYSIZES.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), Integers.valueOf(256));
        KEYSIZES.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4.getId(), Integers.valueOf(128));
        KEYSIZES.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, Integers.valueOf(40));
        KEYSIZES.put(PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, Integers.valueOf(128));
        KEYSIZES.put(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, Integers.valueOf(192));
        KEYSIZES.put(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC2_CBC, Integers.valueOf(128));
        KEYSIZES.put(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC2_CBC, Integers.valueOf(40));
    }
}

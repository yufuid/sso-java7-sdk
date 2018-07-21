package com.yufu.idaas.sdk.bouncycastle.openssl;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Integer;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Primitive;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Sequence;
import com.yufu.idaas.sdk.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.yufu.idaas.sdk.bouncycastle.asn1.sec.ECPrivateKey;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.yufu.idaas.sdk.bouncycastle.asn1.x9.X9ECParameters;
import com.yufu.idaas.sdk.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.yufu.idaas.sdk.bouncycastle.util.io.pem.PemObject;
import com.yufu.idaas.sdk.bouncycastle.util.io.pem.PemObjectParser;
import com.yufu.idaas.sdk.bouncycastle.util.io.pem.PemReader;
import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.cms.ContentInfo;
import com.yufu.idaas.sdk.bouncycastle.asn1.pkcs.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.DSAParameter;
import com.yufu.idaas.sdk.bouncycastle.cert.X509AttributeCertificateHolder;
import com.yufu.idaas.sdk.bouncycastle.cert.X509CRLHolder;
import com.yufu.idaas.sdk.bouncycastle.cert.X509CertificateHolder;
import com.yufu.idaas.sdk.bouncycastle.util.encoders.Hex;
import com.yufu.idaas.sdk.bouncycastle.util.io.pem.PemHeader;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by mac on 2017/1/18.
 */
public class PEMParser extends PemReader {
    private final Map parsers = new HashMap();

    public PEMParser(Reader var1) {
        super(var1);
        this.parsers.put("CERTIFICATE REQUEST", new PKCS10CertificationRequestParser());
        this.parsers.put("NEW CERTIFICATE REQUEST", new PKCS10CertificationRequestParser());
        this.parsers.put("CERTIFICATE", new X509CertificateParser());
        this.parsers.put("TRUSTED CERTIFICATE", new X509TrustedCertificateParser());
        this.parsers.put("X509 CERTIFICATE", new X509CertificateParser());
        this.parsers.put("X509 CRL", new X509CRLParser());
        this.parsers.put("PKCS7", new PKCS7Parser());
        this.parsers.put("CMS", new PKCS7Parser());
        this.parsers.put("ATTRIBUTE CERTIFICATE", new X509AttributeCertificateParser());
        this.parsers.put("EC PARAMETERS", new ECCurveParamsParser());
        this.parsers.put("PUBLIC KEY", new PublicKeyParser());
        this.parsers.put("RSA PUBLIC KEY", new RSAPublicKeyParser());
        this.parsers.put("RSA PRIVATE KEY", new KeyPairParser(new RSAKeyPairParser()));
        this.parsers.put("DSA PRIVATE KEY", new KeyPairParser(new DSAKeyPairParser()));
        this.parsers.put("EC PRIVATE KEY", new KeyPairParser(new ECDSAKeyPairParser()));
        this.parsers.put("ENCRYPTED PRIVATE KEY", new EncryptedPrivateKeyParser());
        this.parsers.put("PRIVATE KEY", new PrivateKeyParser());
    }

    public Object readObject() throws IOException {
        PemObject var1 = this.readPemObject();
        if(var1 != null) {
            String var2 = var1.getType();
            if(this.parsers.containsKey(var2)) {
                return ((PemObjectParser)this.parsers.get(var2)).parseObject(var1);
            } else {
                throw new IOException("unrecognised object: " + var2);
            }
        } else {
            return null;
        }
    }

    private class DSAKeyPairParser implements PEMKeyPairParser {
        private DSAKeyPairParser() {
        }

        public PEMKeyPair parse(byte[] var1) throws IOException {
            try {
                ASN1Sequence var2 = ASN1Sequence.getInstance(var1);
                if(var2.size() != 6) {
                    throw new PEMException("malformed sequence in DSA private key");
                } else {
                    ASN1Integer var3 = ASN1Integer.getInstance(var2.getObjectAt(1));
                    ASN1Integer var4 = ASN1Integer.getInstance(var2.getObjectAt(2));
                    ASN1Integer var5 = ASN1Integer.getInstance(var2.getObjectAt(3));
                    ASN1Integer var6 = ASN1Integer.getInstance(var2.getObjectAt(4));
                    ASN1Integer var7 = ASN1Integer.getInstance(var2.getObjectAt(5));
                    return new PEMKeyPair(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(var3.getValue(), var4.getValue(), var5.getValue())), var6), new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(var3.getValue(), var4.getValue(), var5.getValue())), var7));
                }
            } catch (IOException var8) {
                throw var8;
            } catch (Exception var9) {
                throw new PEMException("problem creating DSA private key: " + var9.toString(), var9);
            }
        }
    }

    private class ECCurveParamsParser implements PemObjectParser {
        private ECCurveParamsParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                ASN1Primitive var2 = ASN1Primitive.fromByteArray(var1.getContent());
                return var2 instanceof ASN1ObjectIdentifier
                    ?ASN1Primitive.fromByteArray(var1.getContent()):(var2 instanceof ASN1Sequence? X9ECParameters.getInstance(var2):null);
            } catch (IOException var3) {
                throw var3;
            } catch (Exception var4) {
                throw new PEMException("exception extracting EC named curve: " + var4.toString());
            }
        }
    }

    private class ECDSAKeyPairParser implements PEMKeyPairParser {
        private ECDSAKeyPairParser() {
        }

        public PEMKeyPair parse(byte[] var1) throws IOException {
            try {
                ASN1Sequence var2 = ASN1Sequence.getInstance(var1);
                ECPrivateKey var3 = ECPrivateKey.getInstance(var2);
                AlgorithmIdentifier var4 = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, var3.getParameters());
                PrivateKeyInfo var5 = new PrivateKeyInfo(var4, var3);
                SubjectPublicKeyInfo var6 = new SubjectPublicKeyInfo(var4, var3.getPublicKey().getBytes());
                return new PEMKeyPair(var6, var5);
            } catch (IOException var7) {
                throw var7;
            } catch (Exception var8) {
                throw new PEMException("problem creating EC private key: " + var8.toString(), var8);
            }
        }
    }

    private class EncryptedPrivateKeyParser implements PemObjectParser {
        public EncryptedPrivateKeyParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                return new PKCS8EncryptedPrivateKeyInfo(EncryptedPrivateKeyInfo.getInstance(var1.getContent()));
            } catch (Exception var3) {
                throw new PEMException("problem parsing ENCRYPTED PRIVATE KEY: " + var3.toString(), var3);
            }
        }
    }

    private class KeyPairParser implements PemObjectParser {
        private final PEMKeyPairParser pemKeyPairParser;

        public KeyPairParser(PEMKeyPairParser var2) {
            this.pemKeyPairParser = var2;
        }

        public Object parseObject(PemObject var1) throws IOException {
            boolean var2 = false;
            String var3 = null;
            List var4 = var1.getHeaders();
            Iterator var5 = var4.iterator();

            while(true) {
                while(var5.hasNext()) {
                    PemHeader var6 = (PemHeader)var5.next();
                    if(var6.getName().equals("Proc-Type") && var6.getValue().equals("4,ENCRYPTED")) {
                        var2 = true;
                    } else if(var6.getName().equals("DEK-Info")) {
                        var3 = var6.getValue();
                    }
                }

                byte[] var11 = var1.getContent();

                try {
                    if(var2) {
                        StringTokenizer var12 = new StringTokenizer(var3, ",");
                        String var7 = var12.nextToken();
                        byte[] var8 = Hex.decode(var12.nextToken());
                        return new PEMEncryptedKeyPair(var7, var8, var11, this.pemKeyPairParser);
                    }

                    return this.pemKeyPairParser.parse(var11);
                } catch (IOException var9) {
                    if(var2) {
                        throw new PEMException("exception decoding - please check password and data.", var9);
                    }

                    throw new PEMException(var9.getMessage(), var9);
                } catch (IllegalArgumentException var10) {
                    if(var2) {
                        throw new PEMException("exception decoding - please check password and data.", var10);
                    }

                    throw new PEMException(var10.getMessage(), var10);
                }
            }
        }
    }

    private class PKCS10CertificationRequestParser implements PemObjectParser {
        private PKCS10CertificationRequestParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                return new PKCS10CertificationRequest(var1.getContent());
            } catch (Exception var3) {
                throw new PEMException("problem parsing certrequest: " + var3.toString(), var3);
            }
        }
    }

    private class PKCS7Parser implements PemObjectParser {
        private PKCS7Parser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                ASN1InputStream var2 = new ASN1InputStream(var1.getContent());
                return ContentInfo.getInstance(var2.readObject());
            } catch (Exception var3) {
                throw new PEMException("problem parsing PKCS7 object: " + var3.toString(), var3);
            }
        }
    }

    private class PrivateKeyParser implements PemObjectParser {
        public PrivateKeyParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                return PrivateKeyInfo.getInstance(var1.getContent());
            } catch (Exception var3) {
                throw new PEMException("problem parsing PRIVATE KEY: " + var3.toString(), var3);
            }
        }
    }

    private class PublicKeyParser implements PemObjectParser {
        public PublicKeyParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            return SubjectPublicKeyInfo.getInstance(var1.getContent());
        }
    }

    private class RSAKeyPairParser implements PEMKeyPairParser {
        private RSAKeyPairParser() {
        }

        public PEMKeyPair parse(byte[] var1) throws IOException {
            try {
                ASN1Sequence var2 = ASN1Sequence.getInstance(var1);
                if(var2.size() != 9) {
                    throw new PEMException("malformed sequence in RSA private key");
                } else {
                    RSAPrivateKey var3 = RSAPrivateKey.getInstance(var2);
                    RSAPublicKey var4 = new RSAPublicKey(var3.getModulus(), var3.getPublicExponent());
                    AlgorithmIdentifier var5 = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
                    return new PEMKeyPair(new SubjectPublicKeyInfo(var5, var4), new PrivateKeyInfo(var5, var3));
                }
            } catch (IOException var6) {
                throw var6;
            } catch (Exception var7) {
                throw new PEMException("problem creating RSA private key: " + var7.toString(), var7);
            }
        }
    }

    private class RSAPublicKeyParser implements PemObjectParser {
        public RSAPublicKeyParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                RSAPublicKey var2 = RSAPublicKey.getInstance(var1.getContent());
                return new SubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE), var2);
            } catch (IOException var3) {
                throw var3;
            } catch (Exception var4) {
                throw new PEMException("problem extracting key: " + var4.toString(), var4);
            }
        }
    }

    private class X509AttributeCertificateParser implements PemObjectParser {
        private X509AttributeCertificateParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            return new X509AttributeCertificateHolder(var1.getContent());
        }
    }

    private class X509CRLParser implements PemObjectParser {
        private X509CRLParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                return new X509CRLHolder(var1.getContent());
            } catch (Exception var3) {
                throw new PEMException("problem parsing cert: " + var3.toString(), var3);
            }
        }
    }

    private class X509CertificateParser implements PemObjectParser {
        private X509CertificateParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                return new X509CertificateHolder(var1.getContent());
            } catch (Exception var3) {
                throw new PEMException("problem parsing cert: " + var3.toString(), var3);
            }
        }
    }

    private class X509TrustedCertificateParser implements PemObjectParser {
        private X509TrustedCertificateParser() {
        }

        public Object parseObject(PemObject var1) throws IOException {
            try {
                return new X509TrustedCertificateBlock(var1.getContent());
            } catch (Exception var3) {
                throw new PEMException("problem parsing cert: " + var3.toString(), var3);
            }
        }
    }
}

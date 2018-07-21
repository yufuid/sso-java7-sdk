package com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util;

import javax.crypto.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by mac on 2017/1/18.
 */
public class DefaultJcaJceHelper implements JcaJceHelper {
    public DefaultJcaJceHelper() {
    }

    public Cipher createCipher(String var1) throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance(var1);
    }

    public Mac createMac(String var1) throws NoSuchAlgorithmException {
        return Mac.getInstance(var1);
    }

    public KeyAgreement createKeyAgreement(String var1) throws NoSuchAlgorithmException {
        return KeyAgreement.getInstance(var1);
    }

    public AlgorithmParameterGenerator createAlgorithmParameterGenerator(String var1) throws NoSuchAlgorithmException {
        return AlgorithmParameterGenerator.getInstance(var1);
    }

    public AlgorithmParameters createAlgorithmParameters(String var1) throws NoSuchAlgorithmException {
        return AlgorithmParameters.getInstance(var1);
    }

    public KeyGenerator createKeyGenerator(String var1) throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(var1);
    }

    public KeyFactory createKeyFactory(String var1) throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(var1);
    }

    public SecretKeyFactory createSecretKeyFactory(String var1) throws NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance(var1);
    }

    public KeyPairGenerator createKeyPairGenerator(String var1) throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(var1);
    }

    public MessageDigest createDigest(String var1) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(var1);
    }

    public Signature createSignature(String var1) throws NoSuchAlgorithmException {
        return Signature.getInstance(var1);
    }

    public CertificateFactory createCertificateFactory(String var1) throws CertificateException {
        return CertificateFactory.getInstance(var1);
    }
}


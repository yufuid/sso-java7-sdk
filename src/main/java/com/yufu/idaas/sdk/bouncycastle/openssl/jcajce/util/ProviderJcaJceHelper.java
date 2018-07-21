package com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util;

import javax.crypto.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by mac on 2017/1/18.
 */
public class ProviderJcaJceHelper implements JcaJceHelper {
    protected final Provider provider;

    public ProviderJcaJceHelper(Provider var1) {
        this.provider = var1;
    }

    public Cipher createCipher(String var1) throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance(var1, this.provider);
    }

    public Mac createMac(String var1) throws NoSuchAlgorithmException {
        return Mac.getInstance(var1, this.provider);
    }

    public KeyAgreement createKeyAgreement(String var1) throws NoSuchAlgorithmException {
        return KeyAgreement.getInstance(var1, this.provider);
    }

    public AlgorithmParameterGenerator createAlgorithmParameterGenerator(String var1) throws NoSuchAlgorithmException {
        return AlgorithmParameterGenerator.getInstance(var1, this.provider);
    }

    public AlgorithmParameters createAlgorithmParameters(String var1) throws NoSuchAlgorithmException {
        return AlgorithmParameters.getInstance(var1, this.provider);
    }

    public KeyGenerator createKeyGenerator(String var1) throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(var1, this.provider);
    }

    public KeyFactory createKeyFactory(String var1) throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(var1, this.provider);
    }

    public SecretKeyFactory createSecretKeyFactory(String var1) throws NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance(var1, this.provider);
    }

    public KeyPairGenerator createKeyPairGenerator(String var1) throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(var1, this.provider);
    }

    public MessageDigest createDigest(String var1) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(var1, this.provider);
    }

    public Signature createSignature(String var1) throws NoSuchAlgorithmException {
        return Signature.getInstance(var1, this.provider);
    }

    public CertificateFactory createCertificateFactory(String var1) throws CertificateException {
        return CertificateFactory.getInstance(var1, this.provider);
    }
}


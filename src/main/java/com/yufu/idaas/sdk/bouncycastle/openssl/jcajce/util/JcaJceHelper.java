package com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util;

import javax.crypto.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by mac on 2017/1/18.
 */
public interface JcaJceHelper {
    Cipher createCipher(String var1) throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException;

    Mac createMac(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    KeyAgreement createKeyAgreement(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    AlgorithmParameterGenerator createAlgorithmParameterGenerator(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    AlgorithmParameters createAlgorithmParameters(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    KeyGenerator createKeyGenerator(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    KeyFactory createKeyFactory(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    SecretKeyFactory createSecretKeyFactory(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    KeyPairGenerator createKeyPairGenerator(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    MessageDigest createDigest(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    Signature createSignature(String var1) throws NoSuchAlgorithmException, NoSuchProviderException;

    CertificateFactory createCertificateFactory(String var1) throws NoSuchProviderException, CertificateException;
}

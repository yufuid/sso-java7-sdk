package com.yufu.idaas.sdk.bouncycastle.operator;

import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.InputStream;

/**
 * Created by mac on 2017/1/18.
 */
public interface InputDecryptor {
    AlgorithmIdentifier getAlgorithmIdentifier();

    InputStream getInputStream(InputStream var1);
}
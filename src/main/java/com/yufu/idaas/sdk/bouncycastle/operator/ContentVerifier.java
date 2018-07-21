package com.yufu.idaas.sdk.bouncycastle.operator;

import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.OutputStream;

/**
 * Created by mac on 2017/1/18.
 */
public interface ContentVerifier {
    AlgorithmIdentifier getAlgorithmIdentifier();

    OutputStream getOutputStream();

    boolean verify(byte[] var1);
}

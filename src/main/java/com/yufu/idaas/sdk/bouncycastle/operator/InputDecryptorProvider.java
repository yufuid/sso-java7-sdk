package com.yufu.idaas.sdk.bouncycastle.operator;

import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;

/**
 * Created by mac on 2017/1/18.
 */
public interface InputDecryptorProvider {
    InputDecryptor get(AlgorithmIdentifier var1) throws OperatorCreationException;
}
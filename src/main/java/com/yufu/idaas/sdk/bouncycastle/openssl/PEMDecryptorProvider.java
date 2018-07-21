package com.yufu.idaas.sdk.bouncycastle.openssl;

import com.yufu.idaas.sdk.bouncycastle.operator.OperatorCreationException;

/**
 * Created by mac on 2017/1/18.
 */
public interface PEMDecryptorProvider {
    PEMDecryptor get(String var1) throws OperatorCreationException;
}


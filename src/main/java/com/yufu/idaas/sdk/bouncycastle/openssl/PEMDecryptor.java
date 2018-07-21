package com.yufu.idaas.sdk.bouncycastle.openssl;

/**
 * Created by mac on 2017/1/18.
 */
public interface PEMDecryptor {
    byte[] decrypt(byte[] var1, byte[] var2) throws PEMException;
}

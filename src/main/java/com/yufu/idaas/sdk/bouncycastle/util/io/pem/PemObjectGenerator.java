package com.yufu.idaas.sdk.bouncycastle.util.io.pem;

/**
 * Created by mac on 2017/1/18.
 */
public interface PemObjectGenerator {
    PemObject generate() throws PemGenerationException;
}

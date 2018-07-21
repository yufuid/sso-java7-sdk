package com.yufu.idaas.sdk.bouncycastle.openssl;

import java.io.IOException;

/**
 * Created by mac on 2017/1/18.
 */
interface PEMKeyPairParser {
    PEMKeyPair parse(byte[] var1) throws IOException;
}
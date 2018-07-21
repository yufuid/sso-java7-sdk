package com.yufu.idaas.sdk.bouncycastle.util.io.pem;

import java.io.IOException;

/**
 * Created by mac on 2017/1/18.
 */
public interface PemObjectParser {
    Object parseObject(PemObject var1) throws IOException;
}
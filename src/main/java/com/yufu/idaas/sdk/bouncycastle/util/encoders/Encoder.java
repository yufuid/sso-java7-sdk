package com.yufu.idaas.sdk.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mac on 2017/1/17.
 */
public interface Encoder {
    int encode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException;

    int decode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException;

    int decode(String var1, OutputStream var2) throws IOException;
}

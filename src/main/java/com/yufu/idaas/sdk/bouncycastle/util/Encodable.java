package com.yufu.idaas.sdk.bouncycastle.util;

/**
 * Created by mac on 2017/1/17.
 */
import java.io.IOException;

public interface Encodable {
    byte[] getEncoded() throws IOException;
}

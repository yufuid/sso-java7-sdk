package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public interface InMemoryRepresentable {
    ASN1Primitive getLoadedObject() throws IOException;
}

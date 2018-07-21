package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public interface ASN1ApplicationSpecificParser extends ASN1Encodable, InMemoryRepresentable {
    ASN1Encodable readObject() throws IOException;
}

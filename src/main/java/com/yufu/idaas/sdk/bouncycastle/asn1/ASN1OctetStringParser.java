package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.InputStream;

/**
 * Created by mac on 2017/1/17.
 */
public interface ASN1OctetStringParser extends ASN1Encodable, InMemoryRepresentable {
    InputStream getOctetStream();
}
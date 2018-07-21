package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public interface ASN1TaggedObjectParser extends ASN1Encodable, InMemoryRepresentable {
    int getTagNo();

    ASN1Encodable getObjectParser(int var1, boolean var2) throws IOException;
}

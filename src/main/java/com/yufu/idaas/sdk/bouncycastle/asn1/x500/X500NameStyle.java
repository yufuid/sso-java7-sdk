package com.yufu.idaas.sdk.bouncycastle.asn1.x500;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Encodable;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;

/**
 * Created by mac on 2017/1/18.
 */
public interface X500NameStyle {
    ASN1Encodable stringToValue(ASN1ObjectIdentifier var1, String var2);

    ASN1ObjectIdentifier attrNameToOID(String var1);

    RDN[] fromString(String var1);

    boolean areEqual(X500Name var1, X500Name var2);

    int calculateHashCode(X500Name var1);

    String toString(X500Name var1);

    String oidToDisplayName(ASN1ObjectIdentifier var1);

    String[] oidToAttrNames(ASN1ObjectIdentifier var1);
}

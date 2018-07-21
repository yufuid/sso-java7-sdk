package com.yufu.idaas.sdk.bouncycastle.asn1;

/**
 * Created by mac on 2017/1/17.
 */
class DERFactory {
    static final ASN1Sequence EMPTY_SEQUENCE = new DERSequence();
    static final ASN1Set EMPTY_SET = new DERSet();

    DERFactory() {
    }

    static ASN1Sequence createSequence(ASN1EncodableVector var0) {
        return (ASN1Sequence)(var0.size() < 1?EMPTY_SEQUENCE:new DLSequence(var0));
    }

    static ASN1Set createSet(ASN1EncodableVector var0) {
        return (ASN1Set)(var0.size() < 1?EMPTY_SET:new DLSet(var0));
    }
}
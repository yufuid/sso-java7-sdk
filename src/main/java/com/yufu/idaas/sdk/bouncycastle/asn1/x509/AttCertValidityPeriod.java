package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

/**
 * Created by mac on 2017/1/18.
 */
public class AttCertValidityPeriod extends ASN1Object {
    ASN1GeneralizedTime notBeforeTime;
    ASN1GeneralizedTime notAfterTime;

    public static AttCertValidityPeriod getInstance(Object var0) {
        return var0 instanceof AttCertValidityPeriod?(AttCertValidityPeriod)var0:(var0 != null?new AttCertValidityPeriod(ASN1Sequence.getInstance(var0)):null);
    }

    private AttCertValidityPeriod(ASN1Sequence var1) {
        if(var1.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        } else {
            this.notBeforeTime = ASN1GeneralizedTime.getInstance(var1.getObjectAt(0));
            this.notAfterTime = ASN1GeneralizedTime.getInstance(var1.getObjectAt(1));
        }
    }

    public AttCertValidityPeriod(ASN1GeneralizedTime var1, ASN1GeneralizedTime var2) {
        this.notBeforeTime = var1;
        this.notAfterTime = var2;
    }

    public ASN1GeneralizedTime getNotBeforeTime() {
        return this.notBeforeTime;
    }

    public ASN1GeneralizedTime getNotAfterTime() {
        return this.notAfterTime;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.notBeforeTime);
        var1.add(this.notAfterTime);
        return new DERSequence(var1);
    }
}

package com.yufu.idaas.sdk.bouncycastle.asn1.x500;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

/**
 * Created by mac on 2017/1/18.
 */
public class RDN extends ASN1Object {
    private ASN1Set values;

    private RDN(ASN1Set var1) {
        this.values = var1;
    }

    public static RDN getInstance(Object var0) {
        return var0 instanceof RDN?(RDN)var0:(var0 != null?new RDN(ASN1Set.getInstance(var0)):null);
    }

    public RDN(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
        ASN1EncodableVector var3 = new ASN1EncodableVector();
        var3.add(var1);
        var3.add(var2);
        this.values = new DERSet(new DERSequence(var3));
    }

    public RDN(AttributeTypeAndValue var1) {
        this.values = new DERSet(var1);
    }

    public RDN(AttributeTypeAndValue[] var1) {
        this.values = new DERSet(var1);
    }

    public boolean isMultiValued() {
        return this.values.size() > 1;
    }

    public int size() {
        return this.values.size();
    }

    public AttributeTypeAndValue getFirst() {
        return this.values.size() == 0?null:AttributeTypeAndValue.getInstance(this.values.getObjectAt(0));
    }

    public AttributeTypeAndValue[] getTypesAndValues() {
        AttributeTypeAndValue[] var1 = new AttributeTypeAndValue[this.values.size()];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = AttributeTypeAndValue.getInstance(this.values.getObjectAt(var2));
        }

        return var1;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.values;
    }
}

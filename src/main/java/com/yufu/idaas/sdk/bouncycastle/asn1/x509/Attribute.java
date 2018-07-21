package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

/**
 * Created by mac on 2017/1/18.
 */
public class Attribute extends ASN1Object {
    private ASN1ObjectIdentifier attrType;
    private ASN1Set attrValues;

    public static Attribute getInstance(Object var0) {
        return var0 instanceof Attribute?(Attribute)var0:(var0 != null?new Attribute(ASN1Sequence.getInstance(var0)):null);
    }

    private Attribute(ASN1Sequence var1) {
        if(var1.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        } else {
            this.attrType = ASN1ObjectIdentifier.getInstance(var1.getObjectAt(0));
            this.attrValues = ASN1Set.getInstance(var1.getObjectAt(1));
        }
    }

    public Attribute(ASN1ObjectIdentifier var1, ASN1Set var2) {
        this.attrType = var1;
        this.attrValues = var2;
    }

    public ASN1ObjectIdentifier getAttrType() {
        return new ASN1ObjectIdentifier(this.attrType.getId());
    }

    public ASN1Encodable[] getAttributeValues() {
        return this.attrValues.toArray();
    }

    public ASN1Set getAttrValues() {
        return this.attrValues;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.attrType);
        var1.add(this.attrValues);
        return new DERSequence(var1);
    }
}


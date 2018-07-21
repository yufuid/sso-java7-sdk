package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

import java.math.BigInteger;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/18.
 */
public class DSAParameter extends ASN1Object {
    ASN1Integer p;
    ASN1Integer q;
    ASN1Integer g;

    public static DSAParameter getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static DSAParameter getInstance(Object var0) {
        return var0 instanceof DSAParameter?(DSAParameter)var0:(var0 != null?new DSAParameter(ASN1Sequence.getInstance(var0)):null);
    }

    public DSAParameter(BigInteger var1, BigInteger var2, BigInteger var3) {
        this.p = new ASN1Integer(var1);
        this.q = new ASN1Integer(var2);
        this.g = new ASN1Integer(var3);
    }

    private DSAParameter(ASN1Sequence var1) {
        if(var1.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        } else {
            Enumeration var2 = var1.getObjects();
            this.p = ASN1Integer.getInstance(var2.nextElement());
            this.q = ASN1Integer.getInstance(var2.nextElement());
            this.g = ASN1Integer.getInstance(var2.nextElement());
        }
    }

    public BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.q.getPositiveValue();
    }

    public BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.p);
        var1.add(this.q);
        var1.add(this.g);
        return new DERSequence(var1);
    }
}


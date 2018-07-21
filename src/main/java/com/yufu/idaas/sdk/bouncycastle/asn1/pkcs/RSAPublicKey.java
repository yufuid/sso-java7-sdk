package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

import java.math.BigInteger;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/18.
 */
public class RSAPublicKey extends ASN1Object {
    private BigInteger modulus;
    private BigInteger publicExponent;

    public static RSAPublicKey getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static RSAPublicKey getInstance(Object var0) {
        return var0 instanceof RSAPublicKey?(RSAPublicKey)var0:(var0 != null?new RSAPublicKey(ASN1Sequence.getInstance(var0)):null);
    }

    public RSAPublicKey(BigInteger var1, BigInteger var2) {
        this.modulus = var1;
        this.publicExponent = var2;
    }

    private RSAPublicKey(ASN1Sequence var1) {
        if(var1.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        } else {
            Enumeration var2 = var1.getObjects();
            this.modulus = ASN1Integer.getInstance(var2.nextElement()).getPositiveValue();
            this.publicExponent = ASN1Integer.getInstance(var2.nextElement()).getPositiveValue();
        }
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(new ASN1Integer(this.getModulus()));
        var1.add(new ASN1Integer(this.getPublicExponent()));
        return new DERSequence(var1);
    }
}

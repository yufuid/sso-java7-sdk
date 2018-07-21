package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

import java.math.BigInteger;
import java.util.Enumeration;

public class RSAPrivateKey extends ASN1Object {
    private BigInteger version;
    private BigInteger modulus;
    private BigInteger publicExponent;
    private BigInteger privateExponent;
    private BigInteger prime1;
    private BigInteger prime2;
    private BigInteger exponent1;
    private BigInteger exponent2;
    private BigInteger coefficient;
    private ASN1Sequence otherPrimeInfos = null;

    public static RSAPrivateKey getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static RSAPrivateKey getInstance(Object var0) {
        return var0 instanceof RSAPrivateKey?(RSAPrivateKey)var0:(var0 != null?new RSAPrivateKey(ASN1Sequence.getInstance(var0)):null);
    }

    public RSAPrivateKey(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4, BigInteger var5, BigInteger var6, BigInteger var7, BigInteger var8) {
        this.version = BigInteger.valueOf(0L);
        this.modulus = var1;
        this.publicExponent = var2;
        this.privateExponent = var3;
        this.prime1 = var4;
        this.prime2 = var5;
        this.exponent1 = var6;
        this.exponent2 = var7;
        this.coefficient = var8;
    }

    private RSAPrivateKey(ASN1Sequence var1) {
        Enumeration var2 = var1.getObjects();
        BigInteger var3 = ((ASN1Integer)var2.nextElement()).getValue();
        if(var3.intValue() != 0 && var3.intValue() != 1) {
            throw new IllegalArgumentException("wrong version for RSA private key");
        } else {
            this.version = var3;
            this.modulus = ((ASN1Integer)var2.nextElement()).getValue();
            this.publicExponent = ((ASN1Integer)var2.nextElement()).getValue();
            this.privateExponent = ((ASN1Integer)var2.nextElement()).getValue();
            this.prime1 = ((ASN1Integer)var2.nextElement()).getValue();
            this.prime2 = ((ASN1Integer)var2.nextElement()).getValue();
            this.exponent1 = ((ASN1Integer)var2.nextElement()).getValue();
            this.exponent2 = ((ASN1Integer)var2.nextElement()).getValue();
            this.coefficient = ((ASN1Integer)var2.nextElement()).getValue();
            if(var2.hasMoreElements()) {
                this.otherPrimeInfos = (ASN1Sequence)var2.nextElement();
            }

        }
    }

    public BigInteger getVersion() {
        return this.version;
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }

    public BigInteger getPrime1() {
        return this.prime1;
    }

    public BigInteger getPrime2() {
        return this.prime2;
    }

    public BigInteger getExponent1() {
        return this.exponent1;
    }

    public BigInteger getExponent2() {
        return this.exponent2;
    }

    public BigInteger getCoefficient() {
        return this.coefficient;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(new ASN1Integer(this.version));
        var1.add(new ASN1Integer(this.getModulus()));
        var1.add(new ASN1Integer(this.getPublicExponent()));
        var1.add(new ASN1Integer(this.getPrivateExponent()));
        var1.add(new ASN1Integer(this.getPrime1()));
        var1.add(new ASN1Integer(this.getPrime2()));
        var1.add(new ASN1Integer(this.getExponent1()));
        var1.add(new ASN1Integer(this.getExponent2()));
        var1.add(new ASN1Integer(this.getCoefficient()));
        if(this.otherPrimeInfos != null) {
            var1.add(this.otherPrimeInfos);
        }

        return new DERSequence(var1);
    }
}

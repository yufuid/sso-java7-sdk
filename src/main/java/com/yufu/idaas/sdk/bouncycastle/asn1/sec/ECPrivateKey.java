package com.yufu.idaas.sdk.bouncycastle.asn1.sec;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.util.BigIntegers;

import java.math.BigInteger;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/18.
 */
public class ECPrivateKey extends ASN1Object {
    private ASN1Sequence seq;

    private ECPrivateKey(ASN1Sequence var1) {
        this.seq = var1;
    }

    public static ECPrivateKey getInstance(Object var0) {
        return var0 instanceof ECPrivateKey?(ECPrivateKey)var0:(var0 != null?new ECPrivateKey(ASN1Sequence.getInstance(var0)):null);
    }

    /** @deprecated */
    public ECPrivateKey(BigInteger var1) {
        this(var1.bitLength(), var1);
    }

    public ECPrivateKey(int var1, BigInteger var2) {
        byte[] var3 = BigIntegers.asUnsignedByteArray((var1 + 7) / 8, var2);
        ASN1EncodableVector var4 = new ASN1EncodableVector();
        var4.add(new ASN1Integer(1L));
        var4.add(new DEROctetString(var3));
        this.seq = new DERSequence(var4);
    }

    /** @deprecated */
    public ECPrivateKey(BigInteger var1, ASN1Encodable var2) {
        this(var1, (DERBitString)null, var2);
    }

    /** @deprecated */
    public ECPrivateKey(BigInteger var1, DERBitString var2, ASN1Encodable var3) {
        this(var1.bitLength(), var1, var2, var3);
    }

    public ECPrivateKey(int var1, BigInteger var2, ASN1Encodable var3) {
        this(var1, var2, (DERBitString)null, var3);
    }

    public ECPrivateKey(int var1, BigInteger var2, DERBitString var3, ASN1Encodable var4) {
        byte[] var5 = BigIntegers.asUnsignedByteArray((var1 + 7) / 8, var2);
        ASN1EncodableVector var6 = new ASN1EncodableVector();
        var6.add(new ASN1Integer(1L));
        var6.add(new DEROctetString(var5));
        if(var4 != null) {
            var6.add(new DERTaggedObject(true, 0, var4));
        }

        if(var3 != null) {
            var6.add(new DERTaggedObject(true, 1, var3));
        }

        this.seq = new DERSequence(var6);
    }

    public BigInteger getKey() {
        ASN1OctetString var1 = (ASN1OctetString)this.seq.getObjectAt(1);
        return new BigInteger(1, var1.getOctets());
    }

    public DERBitString getPublicKey() {
        return (DERBitString)this.getObjectInTag(1);
    }

    public ASN1Primitive getParameters() {
        return this.getObjectInTag(0);
    }

    private ASN1Primitive getObjectInTag(int var1) {
        Enumeration var2 = this.seq.getObjects();

        while(var2.hasMoreElements()) {
            ASN1Encodable var3 = (ASN1Encodable)var2.nextElement();
            if(var3 instanceof ASN1TaggedObject) {
                ASN1TaggedObject var4 = (ASN1TaggedObject)var3;
                if(var4.getTagNo() == var1) {
                    return var4.getObject().toASN1Primitive();
                }
            }
        }

        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}

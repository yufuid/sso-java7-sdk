package com.yufu.idaas.sdk.bouncycastle.asn1.x9;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECAlgorithms;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECCurve;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
public class X9Curve extends ASN1Object implements X9ObjectIdentifiers {
    private ECCurve curve;
    private byte[] seed;
    private ASN1ObjectIdentifier fieldIdentifier = null;

    public X9Curve(ECCurve var1) {
        this.curve = var1;
        this.seed = null;
        this.setFieldIdentifier();
    }

    public X9Curve(ECCurve var1, byte[] var2) {
        this.curve = var1;
        this.seed = var2;
        this.setFieldIdentifier();
    }

    public X9Curve(X9FieldID var1, ASN1Sequence var2) {
        this.fieldIdentifier = var1.getIdentifier();
        if(this.fieldIdentifier.equals(prime_field)) {
            BigInteger var3 = ((ASN1Integer)var1.getParameters()).getValue();
            X9FieldElement var4 = new X9FieldElement(var3, (ASN1OctetString)var2.getObjectAt(0));
            X9FieldElement var5 = new X9FieldElement(var3, (ASN1OctetString)var2.getObjectAt(1));
            this.curve = new ECCurve.Fp(var3, var4.getValue().toBigInteger(), var5.getValue().toBigInteger());
        } else {
            if(!this.fieldIdentifier.equals(characteristic_two_field)) {
                throw new IllegalArgumentException("This type of ECCurve is not implemented");
            }

            ASN1Sequence var11 = ASN1Sequence.getInstance(var1.getParameters());
            int var12 = ((ASN1Integer)var11.getObjectAt(0)).getValue().intValue();
            ASN1ObjectIdentifier var13 = (ASN1ObjectIdentifier)var11.getObjectAt(1);
            boolean var6 = false;
            int var7 = 0;
            int var8 = 0;
            int var14;
            if(var13.equals(tpBasis)) {
                var14 = ASN1Integer.getInstance(var11.getObjectAt(2)).getValue().intValue();
            } else {
                if(!var13.equals(ppBasis)) {
                    throw new IllegalArgumentException("This type of EC basis is not implemented");
                }

                ASN1Sequence var9 = ASN1Sequence.getInstance(var11.getObjectAt(2));
                var14 = ASN1Integer.getInstance(var9.getObjectAt(0)).getValue().intValue();
                var7 = ASN1Integer.getInstance(var9.getObjectAt(1)).getValue().intValue();
                var8 = ASN1Integer.getInstance(var9.getObjectAt(2)).getValue().intValue();
            }

            X9FieldElement var15 = new X9FieldElement(var12, var14, var7, var8, (ASN1OctetString)var2.getObjectAt(0));
            X9FieldElement var10 = new X9FieldElement(var12, var14, var7, var8, (ASN1OctetString)var2.getObjectAt(1));
            this.curve = new ECCurve.F2m(var12, var14, var7, var8, var15.getValue().toBigInteger(), var10.getValue().toBigInteger());
        }

        if(var2.size() == 3) {
            this.seed = ((DERBitString)var2.getObjectAt(2)).getBytes();
        }

    }

    private void setFieldIdentifier() {
        if(ECAlgorithms.isFpCurve(this.curve)) {
            this.fieldIdentifier = prime_field;
        } else {
            if(!ECAlgorithms.isF2mCurve(this.curve)) {
                throw new IllegalArgumentException("This type of ECCurve is not implemented");
            }

            this.fieldIdentifier = characteristic_two_field;
        }

    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        if(this.fieldIdentifier.equals(prime_field)) {
            var1.add((new X9FieldElement(this.curve.getA())).toASN1Primitive());
            var1.add((new X9FieldElement(this.curve.getB())).toASN1Primitive());
        } else if(this.fieldIdentifier.equals(characteristic_two_field)) {
            var1.add((new X9FieldElement(this.curve.getA())).toASN1Primitive());
            var1.add((new X9FieldElement(this.curve.getB())).toASN1Primitive());
        }

        if(this.seed != null) {
            var1.add(new DERBitString(this.seed));
        }

        return new DERSequence(var1);
    }
}

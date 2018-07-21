package com.yufu.idaas.sdk.bouncycastle.asn1.x9;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECAlgorithms;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECCurve;
import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;
import com.yufu.idaas.sdk.bouncycastle.math.field.PolynomialExtensionField;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
public class X9ECParameters extends ASN1Object implements X9ObjectIdentifiers {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private X9FieldID fieldID;
    private ECCurve curve;
    private X9ECPoint g;
    private BigInteger n;
    private BigInteger h;
    private byte[] seed;

    private X9ECParameters(ASN1Sequence var1) {
        if(var1.getObjectAt(0) instanceof ASN1Integer && ((ASN1Integer)var1.getObjectAt(0)).getValue().equals(ONE)) {
            X9Curve var2 = new X9Curve(X9FieldID.getInstance(var1.getObjectAt(1)), ASN1Sequence.getInstance(var1.getObjectAt(2)));
            this.curve = var2.getCurve();
            ASN1Encodable var3 = var1.getObjectAt(3);
            if(var3 instanceof X9ECPoint) {
                this.g = (X9ECPoint)var3;
            } else {
                this.g = new X9ECPoint(this.curve, (ASN1OctetString)var3);
            }

            this.n = ((ASN1Integer)var1.getObjectAt(4)).getValue();
            this.seed = var2.getSeed();
            if(var1.size() == 6) {
                this.h = ((ASN1Integer)var1.getObjectAt(5)).getValue();
            }

        } else {
            throw new IllegalArgumentException("bad version in X9ECParameters");
        }
    }

    public static X9ECParameters getInstance(Object var0) {
        return var0 instanceof X9ECParameters?(X9ECParameters)var0:(var0 != null?new X9ECParameters(ASN1Sequence.getInstance(var0)):null);
    }

    public X9ECParameters(ECCurve var1, ECPoint var2, BigInteger var3) {
        this(var1, (ECPoint)var2, var3, (BigInteger)null, (byte[])null);
    }

    public X9ECParameters(ECCurve var1, X9ECPoint var2, BigInteger var3, BigInteger var4) {
        this(var1, (X9ECPoint)var2, var3, var4, (byte[])null);
    }

    public X9ECParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4) {
        this(var1, (ECPoint)var2, var3, var4, (byte[])null);
    }

    public X9ECParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4, byte[] var5) {
        this(var1, new X9ECPoint(var2), var3, var4, var5);
    }

    public X9ECParameters(ECCurve var1, X9ECPoint var2, BigInteger var3, BigInteger var4, byte[] var5) {
        this.curve = var1;
        this.g = var2;
        this.n = var3;
        this.h = var4;
        this.seed = var5;
        if(ECAlgorithms.isFpCurve(var1)) {
            this.fieldID = new X9FieldID(var1.getField().getCharacteristic());
        } else {
            if(!ECAlgorithms.isF2mCurve(var1)) {
                throw new IllegalArgumentException("\'curve\' is of an unsupported type");
            }

            PolynomialExtensionField var6 = (PolynomialExtensionField)var1.getField();
            int[] var7 = var6.getMinimalPolynomial().getExponentsPresent();
            if(var7.length == 3) {
                this.fieldID = new X9FieldID(var7[2], var7[1]);
            } else {
                if(var7.length != 5) {
                    throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
                }

                this.fieldID = new X9FieldID(var7[4], var7[1], var7[2], var7[3]);
            }
        }

    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public ECPoint getG() {
        return this.g.getPoint();
    }

    public BigInteger getN() {
        return this.n;
    }

    public BigInteger getH() {
        return this.h;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public X9Curve getCurveEntry() {
        return new X9Curve(this.curve, this.seed);
    }

    public X9FieldID getFieldIDEntry() {
        return this.fieldID;
    }

    public X9ECPoint getBaseEntry() {
        return this.g;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(new ASN1Integer(ONE));
        var1.add(this.fieldID);
        var1.add(new X9Curve(this.curve, this.seed));
        var1.add(this.g);
        var1.add(new ASN1Integer(this.n));
        if(this.h != null) {
            var1.add(new ASN1Integer(this.h));
        }

        return new DERSequence(var1);
    }
}

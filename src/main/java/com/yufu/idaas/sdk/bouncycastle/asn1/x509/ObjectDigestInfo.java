package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

/**
 * Created by mac on 2017/1/18.
 */
public class ObjectDigestInfo extends ASN1Object {
    public static final int publicKey = 0;
    public static final int publicKeyCert = 1;
    public static final int otherObjectDigest = 2;
    ASN1Enumerated digestedObjectType;
    ASN1ObjectIdentifier otherObjectTypeID;
    AlgorithmIdentifier digestAlgorithm;
    DERBitString objectDigest;

    public static ObjectDigestInfo getInstance(Object var0) {
        return var0 instanceof ObjectDigestInfo?(ObjectDigestInfo)var0:(var0 != null?new ObjectDigestInfo(ASN1Sequence.getInstance(var0)):null);
    }

    public static ObjectDigestInfo getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public ObjectDigestInfo(int var1, ASN1ObjectIdentifier var2, AlgorithmIdentifier var3, byte[] var4) {
        this.digestedObjectType = new ASN1Enumerated(var1);
        if(var1 == 2) {
            this.otherObjectTypeID = var2;
        }

        this.digestAlgorithm = var3;
        this.objectDigest = new DERBitString(var4);
    }

    private ObjectDigestInfo(ASN1Sequence var1) {
        if(var1.size() <= 4 && var1.size() >= 3) {
            this.digestedObjectType = ASN1Enumerated.getInstance(var1.getObjectAt(0));
            int var2 = 0;
            if(var1.size() == 4) {
                this.otherObjectTypeID = ASN1ObjectIdentifier.getInstance(var1.getObjectAt(1));
                ++var2;
            }

            this.digestAlgorithm = AlgorithmIdentifier.getInstance(var1.getObjectAt(1 + var2));
            this.objectDigest = DERBitString.getInstance(var1.getObjectAt(2 + var2));
        } else {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        }
    }

    public ASN1Enumerated getDigestedObjectType() {
        return this.digestedObjectType;
    }

    public ASN1ObjectIdentifier getOtherObjectTypeID() {
        return this.otherObjectTypeID;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public DERBitString getObjectDigest() {
        return this.objectDigest;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.digestedObjectType);
        if(this.otherObjectTypeID != null) {
            var1.add(this.otherObjectTypeID);
        }

        var1.add(this.digestAlgorithm);
        var1.add(this.objectDigest);
        return new DERSequence(var1);
    }
}


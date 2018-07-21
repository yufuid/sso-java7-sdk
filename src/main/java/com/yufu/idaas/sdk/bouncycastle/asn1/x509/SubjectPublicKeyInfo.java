package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/18.
 */
public class SubjectPublicKeyInfo extends ASN1Object {
    private AlgorithmIdentifier algId;
    private DERBitString keyData;

    public static SubjectPublicKeyInfo getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static SubjectPublicKeyInfo getInstance(Object var0) {
        return var0 instanceof SubjectPublicKeyInfo?(SubjectPublicKeyInfo)var0:(var0 != null?new SubjectPublicKeyInfo(ASN1Sequence.getInstance(var0)):null);
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier var1, ASN1Encodable var2) throws IOException {
        this.keyData = new DERBitString(var2);
        this.algId = var1;
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier var1, byte[] var2) {
        this.keyData = new DERBitString(var2);
        this.algId = var1;
    }

    /** @deprecated */
    public SubjectPublicKeyInfo(ASN1Sequence var1) {
        if(var1.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        } else {
            Enumeration var2 = var1.getObjects();
            this.algId = AlgorithmIdentifier.getInstance(var2.nextElement());
            this.keyData = DERBitString.getInstance(var2.nextElement());
        }
    }

    public AlgorithmIdentifier getAlgorithm() {
        return this.algId;
    }

    /** @deprecated */
    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public ASN1Primitive parsePublicKey() throws IOException {
        ASN1InputStream var1 = new ASN1InputStream(this.keyData.getOctets());
        return var1.readObject();
    }

    /** @deprecated */
    public ASN1Primitive getPublicKey() throws IOException {
        ASN1InputStream var1 = new ASN1InputStream(this.keyData.getOctets());
        return var1.readObject();
    }

    public DERBitString getPublicKeyData() {
        return this.keyData;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.algId);
        var1.add(this.keyData);
        return new DERSequence(var1);
    }
}

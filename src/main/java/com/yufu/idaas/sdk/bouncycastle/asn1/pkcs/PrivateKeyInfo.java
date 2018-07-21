package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/17.
 */
public class PrivateKeyInfo extends ASN1Object {
    private ASN1OctetString privKey;
    private AlgorithmIdentifier algId;
    private ASN1Set attributes;

    public static PrivateKeyInfo getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static PrivateKeyInfo getInstance(Object var0) {
        return var0 instanceof PrivateKeyInfo?(PrivateKeyInfo)var0:(var0 != null?new PrivateKeyInfo(ASN1Sequence.getInstance(var0)):null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier var1, ASN1Encodable var2) throws IOException {
        this(var1, var2, (ASN1Set)null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier var1, ASN1Encodable var2, ASN1Set var3) throws IOException {
        this.privKey = new DEROctetString(var2.toASN1Primitive().getEncoded("DER"));
        this.algId = var1;
        this.attributes = var3;
    }

    /** @deprecated */
    public PrivateKeyInfo(ASN1Sequence var1) {
        Enumeration var2 = var1.getObjects();
        BigInteger var3 = ((ASN1Integer)var2.nextElement()).getValue();
        if(var3.intValue() != 0) {
            throw new IllegalArgumentException("wrong version for private key info");
        } else {
            this.algId = AlgorithmIdentifier.getInstance(var2.nextElement());
            this.privKey = ASN1OctetString.getInstance(var2.nextElement());
            if(var2.hasMoreElements()) {
                this.attributes = ASN1Set.getInstance((ASN1TaggedObject)var2.nextElement(), false);
            }

        }
    }

    public AlgorithmIdentifier getPrivateKeyAlgorithm() {
        return this.algId;
    }

    /** @deprecated */
    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public ASN1Encodable parsePrivateKey() throws IOException {
        return ASN1Primitive.fromByteArray(this.privKey.getOctets());
    }

    /** @deprecated */
    public ASN1Primitive getPrivateKey() {
        try {
            return this.parsePrivateKey().toASN1Primitive();
        } catch (IOException var2) {
            throw new IllegalStateException("unable to parse private key");
        }
    }

    public ASN1Set getAttributes() {
        return this.attributes;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(new ASN1Integer(0L));
        var1.add(this.algId);
        var1.add(this.privKey);
        if(this.attributes != null) {
            var1.add(new DERTaggedObject(false, 0, this.attributes));
        }

        return new DERSequence(var1);
    }
}

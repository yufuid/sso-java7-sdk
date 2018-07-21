package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;

/**
 * Created by mac on 2017/1/18.
 */
public class Certificate extends ASN1Object {
    ASN1Sequence seq;
    TBSCertificate tbsCert;
    AlgorithmIdentifier sigAlgId;
    DERBitString sig;

    public static Certificate getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static Certificate getInstance(Object var0) {
        return var0 instanceof Certificate?(Certificate)var0:(var0 != null?new Certificate(ASN1Sequence.getInstance(var0)):null);
    }

    private Certificate(ASN1Sequence var1) {
        this.seq = var1;
        if(var1.size() == 3) {
            this.tbsCert = TBSCertificate.getInstance(var1.getObjectAt(0));
            this.sigAlgId = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
            this.sig = DERBitString.getInstance(var1.getObjectAt(2));
        } else {
            throw new IllegalArgumentException("sequence wrong size for a certificate");
        }
    }

    public TBSCertificate getTBSCertificate() {
        return this.tbsCert;
    }

    public ASN1Integer getVersion() {
        return this.tbsCert.getVersion();
    }

    public int getVersionNumber() {
        return this.tbsCert.getVersionNumber();
    }

    public ASN1Integer getSerialNumber() {
        return this.tbsCert.getSerialNumber();
    }

    public X500Name getIssuer() {
        return this.tbsCert.getIssuer();
    }

    public Time getStartDate() {
        return this.tbsCert.getStartDate();
    }

    public Time getEndDate() {
        return this.tbsCert.getEndDate();
    }

    public X500Name getSubject() {
        return this.tbsCert.getSubject();
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.tbsCert.getSubjectPublicKeyInfo();
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public DERBitString getSignature() {
        return this.sig;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}

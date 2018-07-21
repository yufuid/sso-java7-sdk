package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;

/**
 * Created by mac on 2017/1/18.
 */
public class TBSCertificate extends ASN1Object {
    ASN1Sequence seq;
    ASN1Integer version;
    ASN1Integer serialNumber;
    AlgorithmIdentifier signature;
    X500Name issuer;
    Time startDate;
    Time endDate;
    X500Name subject;
    SubjectPublicKeyInfo subjectPublicKeyInfo;
    DERBitString issuerUniqueId;
    DERBitString subjectUniqueId;
    Extensions extensions;

    public static TBSCertificate getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static TBSCertificate getInstance(Object var0) {
        return var0 instanceof TBSCertificate?(TBSCertificate)var0:(var0 != null?new TBSCertificate(ASN1Sequence.getInstance(var0)):null);
    }

    private TBSCertificate(ASN1Sequence var1) {
        byte var2 = 0;
        this.seq = var1;
        if(var1.getObjectAt(0) instanceof DERTaggedObject) {
            this.version = ASN1Integer.getInstance((ASN1TaggedObject)var1.getObjectAt(0), true);
        } else {
            var2 = -1;
            this.version = new ASN1Integer(0L);
        }

        this.serialNumber = ASN1Integer.getInstance(var1.getObjectAt(var2 + 1));
        this.signature = AlgorithmIdentifier.getInstance(var1.getObjectAt(var2 + 2));
        this.issuer = X500Name.getInstance(var1.getObjectAt(var2 + 3));
        ASN1Sequence var3 = (ASN1Sequence)var1.getObjectAt(var2 + 4);
        this.startDate = Time.getInstance(var3.getObjectAt(0));
        this.endDate = Time.getInstance(var3.getObjectAt(1));
        this.subject = X500Name.getInstance(var1.getObjectAt(var2 + 5));
        this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(var2 + 6));

        for(int var4 = var1.size() - (var2 + 6) - 1; var4 > 0; --var4) {
            DERTaggedObject var5 = (DERTaggedObject)var1.getObjectAt(var2 + 6 + var4);
            switch(var5.getTagNo()) {
                case 1:
                    this.issuerUniqueId = DERBitString.getInstance(var5, false);
                    break;
                case 2:
                    this.subjectUniqueId = DERBitString.getInstance(var5, false);
                    break;
                case 3:
                    this.extensions = Extensions.getInstance(ASN1Sequence.getInstance(var5, true));
            }
        }

    }

    public int getVersionNumber() {
        return this.version.getValue().intValue() + 1;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public Time getStartDate() {
        return this.startDate;
    }

    public Time getEndDate() {
        return this.endDate;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public DERBitString getIssuerUniqueId() {
        return this.issuerUniqueId;
    }

    public DERBitString getSubjectUniqueId() {
        return this.subjectUniqueId;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}


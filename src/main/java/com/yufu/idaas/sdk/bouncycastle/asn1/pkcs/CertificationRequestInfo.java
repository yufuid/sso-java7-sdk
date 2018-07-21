package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.X509Name;

public class CertificationRequestInfo extends ASN1Object {
    ASN1Integer version = new ASN1Integer(0L);
    X500Name subject;
    SubjectPublicKeyInfo subjectPKInfo;
    ASN1Set attributes = null;

    public static CertificationRequestInfo getInstance(Object var0) {
        return var0 instanceof CertificationRequestInfo?(CertificationRequestInfo)var0:(var0 != null?new CertificationRequestInfo(ASN1Sequence.getInstance(var0)):null);
    }

    public CertificationRequestInfo(X500Name var1, SubjectPublicKeyInfo var2, ASN1Set var3) {
        if(var1 != null && var2 != null) {
            this.subject = var1;
            this.subjectPKInfo = var2;
            this.attributes = var3;
        } else {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    /** @deprecated */
    public CertificationRequestInfo(X509Name var1, SubjectPublicKeyInfo var2, ASN1Set var3) {
        if(var1 != null && var2 != null) {
            this.subject = X500Name.getInstance(var1.toASN1Primitive());
            this.subjectPKInfo = var2;
            this.attributes = var3;
        } else {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    /** @deprecated */
    public CertificationRequestInfo(ASN1Sequence var1) {
        this.version = (ASN1Integer)var1.getObjectAt(0);
        this.subject = X500Name.getInstance(var1.getObjectAt(1));
        this.subjectPKInfo = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(2));
        if(var1.size() > 3) {
            DERTaggedObject var2 = (DERTaggedObject)var1.getObjectAt(3);
            this.attributes = ASN1Set.getInstance(var2, false);
        }

        if(this.subject == null || this.version == null || this.subjectPKInfo == null) {
            throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
        }
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPKInfo;
    }

    public ASN1Set getAttributes() {
        return this.attributes;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.version);
        var1.add(this.subject);
        var1.add(this.subjectPKInfo);
        if(this.attributes != null) {
            var1.add(new DERTaggedObject(false, 0, this.attributes));
        }

        return new DERSequence(var1);
    }
}

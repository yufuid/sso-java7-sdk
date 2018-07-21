package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;

import java.util.Enumeration;

/**
 * Created by mac on 2017/1/18.
 */
public class CertificateList extends ASN1Object {
    TBSCertList tbsCertList;
    AlgorithmIdentifier sigAlgId;
    DERBitString sig;
    boolean isHashCodeSet = false;
    int hashCodeValue;

    public static CertificateList getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static CertificateList getInstance(Object var0) {
        return var0 instanceof CertificateList?(CertificateList)var0:(var0 != null?new CertificateList(ASN1Sequence.getInstance(var0)):null);
    }

    /** @deprecated */
    public CertificateList(ASN1Sequence var1) {
        if(var1.size() == 3) {
            this.tbsCertList = TBSCertList.getInstance(var1.getObjectAt(0));
            this.sigAlgId = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
            this.sig = DERBitString.getInstance(var1.getObjectAt(2));
        } else {
            throw new IllegalArgumentException("sequence wrong size for CertificateList");
        }
    }

    public TBSCertList getTBSCertList() {
        return this.tbsCertList;
    }

    public TBSCertList.CRLEntry[] getRevokedCertificates() {
        return this.tbsCertList.getRevokedCertificates();
    }

    public Enumeration getRevokedCertificateEnumeration() {
        return this.tbsCertList.getRevokedCertificateEnumeration();
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public DERBitString getSignature() {
        return this.sig;
    }

    public int getVersionNumber() {
        return this.tbsCertList.getVersionNumber();
    }

    public X500Name getIssuer() {
        return this.tbsCertList.getIssuer();
    }

    public Time getThisUpdate() {
        return this.tbsCertList.getThisUpdate();
    }

    public Time getNextUpdate() {
        return this.tbsCertList.getNextUpdate();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.tbsCertList);
        var1.add(this.sigAlgId);
        var1.add(this.sig);
        return new DERSequence(var1);
    }

    public int hashCode() {
        if(!this.isHashCodeSet) {
            this.hashCodeValue = super.hashCode();
            this.isHashCodeSet = true;
        }

        return this.hashCodeValue;
    }
}


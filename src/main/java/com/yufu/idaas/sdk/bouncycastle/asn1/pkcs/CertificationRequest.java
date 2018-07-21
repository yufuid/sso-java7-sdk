package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertificationRequest extends ASN1Object {
    protected CertificationRequestInfo reqInfo = null;
    protected AlgorithmIdentifier sigAlgId = null;
    protected DERBitString sigBits = null;

    public static CertificationRequest getInstance(Object var0) {
        return var0 instanceof CertificationRequest?(CertificationRequest)var0:(var0 != null?new CertificationRequest(ASN1Sequence.getInstance(var0)):null);
    }

    protected CertificationRequest() {
    }

    public CertificationRequest(CertificationRequestInfo var1, AlgorithmIdentifier var2, DERBitString var3) {
        this.reqInfo = var1;
        this.sigAlgId = var2;
        this.sigBits = var3;
    }

    public CertificationRequest(ASN1Sequence var1) {
        this.reqInfo = CertificationRequestInfo.getInstance(var1.getObjectAt(0));
        this.sigAlgId = AlgorithmIdentifier.getInstance(var1.getObjectAt(1));
        this.sigBits = (DERBitString)var1.getObjectAt(2);
    }

    public CertificationRequestInfo getCertificationRequestInfo() {
        return this.reqInfo;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public DERBitString getSignature() {
        return this.sigBits;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        var1.add(this.reqInfo);
        var1.add(this.sigAlgId);
        var1.add(this.sigBits);
        return new DERSequence(var1);
    }
}
package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

/**
 * Created by mac on 2017/1/18.
 */
public class AttributeCertificateInfo extends ASN1Object {
    private ASN1Integer version;
    private Holder holder;
    private AttCertIssuer issuer;
    private AlgorithmIdentifier signature;
    private ASN1Integer serialNumber;
    private AttCertValidityPeriod attrCertValidityPeriod;
    private ASN1Sequence attributes;
    private DERBitString issuerUniqueID;
    private Extensions extensions;

    public static AttributeCertificateInfo getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static AttributeCertificateInfo getInstance(Object var0) {
        return var0 instanceof AttributeCertificateInfo?(AttributeCertificateInfo)var0:(var0 != null?new AttributeCertificateInfo(ASN1Sequence.getInstance(var0)):null);
    }

    private AttributeCertificateInfo(ASN1Sequence var1) {
        if(var1.size() >= 6 && var1.size() <= 9) {
            byte var2;
            if(var1.getObjectAt(0) instanceof ASN1Integer) {
                this.version = ASN1Integer.getInstance(var1.getObjectAt(0));
                var2 = 1;
            } else {
                this.version = new ASN1Integer(0L);
                var2 = 0;
            }

            this.holder = Holder.getInstance(var1.getObjectAt(var2));
            this.issuer = AttCertIssuer.getInstance(var1.getObjectAt(var2 + 1));
            this.signature = AlgorithmIdentifier.getInstance(var1.getObjectAt(var2 + 2));
            this.serialNumber = ASN1Integer.getInstance(var1.getObjectAt(var2 + 3));
            this.attrCertValidityPeriod = AttCertValidityPeriod.getInstance(var1.getObjectAt(var2 + 4));
            this.attributes = ASN1Sequence.getInstance(var1.getObjectAt(var2 + 5));

            for(int var3 = var2 + 6; var3 < var1.size(); ++var3) {
                ASN1Encodable var4 = var1.getObjectAt(var3);
                if(var4 instanceof DERBitString) {
                    this.issuerUniqueID = DERBitString.getInstance(var1.getObjectAt(var3));
                } else if(var4 instanceof ASN1Sequence || var4 instanceof Extensions) {
                    this.extensions = Extensions.getInstance(var1.getObjectAt(var3));
                }
            }

        } else {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        }
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public Holder getHolder() {
        return this.holder;
    }

    public AttCertIssuer getIssuer() {
        return this.issuer;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public AttCertValidityPeriod getAttrCertValidityPeriod() {
        return this.attrCertValidityPeriod;
    }

    public ASN1Sequence getAttributes() {
        return this.attributes;
    }

    public DERBitString getIssuerUniqueID() {
        return this.issuerUniqueID;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        if(this.version.getValue().intValue() != 0) {
            var1.add(this.version);
        }

        var1.add(this.holder);
        var1.add(this.issuer);
        var1.add(this.signature);
        var1.add(this.serialNumber);
        var1.add(this.attrCertValidityPeriod);
        var1.add(this.attributes);
        if(this.issuerUniqueID != null) {
            var1.add(this.issuerUniqueID);
        }

        if(this.extensions != null) {
            var1.add(this.extensions);
        }

        return new DERSequence(var1);
    }
}


package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Created by mac on 2017/1/18.
 */
public class TBSCertList extends ASN1Object {
    ASN1Integer version;
    AlgorithmIdentifier signature;
    X500Name issuer;
    Time thisUpdate;
    Time nextUpdate;
    ASN1Sequence revokedCertificates;
    Extensions crlExtensions;

    public static TBSCertList getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static TBSCertList getInstance(Object var0) {
        return var0 instanceof TBSCertList?(TBSCertList)var0:(var0 != null?new TBSCertList(ASN1Sequence.getInstance(var0)):null);
    }

    public TBSCertList(ASN1Sequence var1) {
        if(var1.size() >= 3 && var1.size() <= 7) {
            int var2 = 0;
            if(var1.getObjectAt(var2) instanceof ASN1Integer) {
                this.version = ASN1Integer.getInstance(var1.getObjectAt(var2++));
            } else {
                this.version = null;
            }

            this.signature = AlgorithmIdentifier.getInstance(var1.getObjectAt(var2++));
            this.issuer = X500Name.getInstance(var1.getObjectAt(var2++));
            this.thisUpdate = Time.getInstance(var1.getObjectAt(var2++));
            if(var2 < var1.size() && (var1.getObjectAt(var2) instanceof ASN1UTCTime || var1.getObjectAt(var2) instanceof ASN1GeneralizedTime || var1.getObjectAt(var2) instanceof Time)) {
                this.nextUpdate = Time.getInstance(var1.getObjectAt(var2++));
            }

            if(var2 < var1.size() && !(var1.getObjectAt(var2) instanceof DERTaggedObject)) {
                this.revokedCertificates = ASN1Sequence.getInstance(var1.getObjectAt(var2++));
            }

            if(var2 < var1.size() && var1.getObjectAt(var2) instanceof DERTaggedObject) {
                this.crlExtensions = Extensions.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)var1.getObjectAt(var2), true));
            }

        } else {
            throw new IllegalArgumentException("Bad sequence size: " + var1.size());
        }
    }

    public int getVersionNumber() {
        return this.version == null?1:this.version.getValue().intValue() + 1;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public Time getThisUpdate() {
        return this.thisUpdate;
    }

    public Time getNextUpdate() {
        return this.nextUpdate;
    }

    public CRLEntry[] getRevokedCertificates() {
        if(this.revokedCertificates == null) {
            return new CRLEntry[0];
        } else {
            CRLEntry[] var1 = new CRLEntry[this.revokedCertificates.size()];

            for(int var2 = 0; var2 < var1.length; ++var2) {
                var1[var2] = CRLEntry.getInstance(this.revokedCertificates.getObjectAt(var2));
            }

            return var1;
        }
    }

    public Enumeration getRevokedCertificateEnumeration() {
        return (Enumeration)(this.revokedCertificates == null?new EmptyEnumeration():new RevokedCertificatesEnumeration(this.revokedCertificates.getObjects()));
    }

    public Extensions getExtensions() {
        return this.crlExtensions;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        if(this.version != null) {
            var1.add(this.version);
        }

        var1.add(this.signature);
        var1.add(this.issuer);
        var1.add(this.thisUpdate);
        if(this.nextUpdate != null) {
            var1.add(this.nextUpdate);
        }

        if(this.revokedCertificates != null) {
            var1.add(this.revokedCertificates);
        }

        if(this.crlExtensions != null) {
            var1.add(new DERTaggedObject(0, this.crlExtensions));
        }

        return new DERSequence(var1);
    }

    public static class CRLEntry extends ASN1Object {
        ASN1Sequence seq;
        Extensions crlEntryExtensions;

        private CRLEntry(ASN1Sequence var1) {
            if(var1.size() >= 2 && var1.size() <= 3) {
                this.seq = var1;
            } else {
                throw new IllegalArgumentException("Bad sequence size: " + var1.size());
            }
        }

        public static CRLEntry getInstance(Object var0) {
            return var0 instanceof CRLEntry?(CRLEntry)var0:(var0 != null?new CRLEntry(ASN1Sequence.getInstance(var0)):null);
        }

        public ASN1Integer getUserCertificate() {
            return ASN1Integer.getInstance(this.seq.getObjectAt(0));
        }

        public Time getRevocationDate() {
            return Time.getInstance(this.seq.getObjectAt(1));
        }

        public Extensions getExtensions() {
            if(this.crlEntryExtensions == null && this.seq.size() == 3) {
                this.crlEntryExtensions = Extensions.getInstance(this.seq.getObjectAt(2));
            }

            return this.crlEntryExtensions;
        }

        public ASN1Primitive toASN1Primitive() {
            return this.seq;
        }

        public boolean hasExtensions() {
            return this.seq.size() == 3;
        }
    }

    private class EmptyEnumeration implements Enumeration {
        private EmptyEnumeration() {
        }

        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            throw new NoSuchElementException("Empty Enumeration");
        }
    }

    private class RevokedCertificatesEnumeration implements Enumeration {
        private final Enumeration en;

        RevokedCertificatesEnumeration(Enumeration var2) {
            this.en = var2;
        }

        public boolean hasMoreElements() {
            return this.en.hasMoreElements();
        }

        public Object nextElement() {
            return CRLEntry.getInstance(this.en.nextElement());
        }
    }
}


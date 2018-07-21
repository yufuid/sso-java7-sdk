package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by mac on 2017/1/18.
 */
public class X509Extensions extends ASN1Object {
    /** @deprecated */
    public static final ASN1ObjectIdentifier SubjectDirectoryAttributes = new ASN1ObjectIdentifier("2.5.29.9");
    /** @deprecated */
    public static final ASN1ObjectIdentifier SubjectKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.14");
    /** @deprecated */
    public static final ASN1ObjectIdentifier KeyUsage = new ASN1ObjectIdentifier("2.5.29.15");
    /** @deprecated */
    public static final ASN1ObjectIdentifier PrivateKeyUsagePeriod = new ASN1ObjectIdentifier("2.5.29.16");
    /** @deprecated */
    public static final ASN1ObjectIdentifier SubjectAlternativeName = new ASN1ObjectIdentifier("2.5.29.17");
    /** @deprecated */
    public static final ASN1ObjectIdentifier IssuerAlternativeName = new ASN1ObjectIdentifier("2.5.29.18");
    /** @deprecated */
    public static final ASN1ObjectIdentifier BasicConstraints = new ASN1ObjectIdentifier("2.5.29.19");
    /** @deprecated */
    public static final ASN1ObjectIdentifier CRLNumber = new ASN1ObjectIdentifier("2.5.29.20");
    /** @deprecated */
    public static final ASN1ObjectIdentifier ReasonCode = new ASN1ObjectIdentifier("2.5.29.21");
    /** @deprecated */
    public static final ASN1ObjectIdentifier InstructionCode = new ASN1ObjectIdentifier("2.5.29.23");
    /** @deprecated */
    public static final ASN1ObjectIdentifier InvalidityDate = new ASN1ObjectIdentifier("2.5.29.24");
    /** @deprecated */
    public static final ASN1ObjectIdentifier DeltaCRLIndicator = new ASN1ObjectIdentifier("2.5.29.27");
    /** @deprecated */
    public static final ASN1ObjectIdentifier IssuingDistributionPoint = new ASN1ObjectIdentifier("2.5.29.28");
    /** @deprecated */
    public static final ASN1ObjectIdentifier CertificateIssuer = new ASN1ObjectIdentifier("2.5.29.29");
    /** @deprecated */
    public static final ASN1ObjectIdentifier NameConstraints = new ASN1ObjectIdentifier("2.5.29.30");
    /** @deprecated */
    public static final ASN1ObjectIdentifier CRLDistributionPoints = new ASN1ObjectIdentifier("2.5.29.31");
    /** @deprecated */
    public static final ASN1ObjectIdentifier CertificatePolicies = new ASN1ObjectIdentifier("2.5.29.32");
    /** @deprecated */
    public static final ASN1ObjectIdentifier PolicyMappings = new ASN1ObjectIdentifier("2.5.29.33");
    /** @deprecated */
    public static final ASN1ObjectIdentifier AuthorityKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.35");
    /** @deprecated */
    public static final ASN1ObjectIdentifier PolicyConstraints = new ASN1ObjectIdentifier("2.5.29.36");
    /** @deprecated */
    public static final ASN1ObjectIdentifier ExtendedKeyUsage = new ASN1ObjectIdentifier("2.5.29.37");
    /** @deprecated */
    public static final ASN1ObjectIdentifier FreshestCRL = new ASN1ObjectIdentifier("2.5.29.46");
    /** @deprecated */
    public static final ASN1ObjectIdentifier InhibitAnyPolicy = new ASN1ObjectIdentifier("2.5.29.54");
    /** @deprecated */
    public static final ASN1ObjectIdentifier AuthorityInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
    /** @deprecated */
    public static final ASN1ObjectIdentifier SubjectInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
    /** @deprecated */
    public static final ASN1ObjectIdentifier LogoType = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    /** @deprecated */
    public static final ASN1ObjectIdentifier BiometricInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    /** @deprecated */
    public static final ASN1ObjectIdentifier QCStatements = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
    /** @deprecated */
    public static final ASN1ObjectIdentifier AuditIdentity = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
    /** @deprecated */
    public static final ASN1ObjectIdentifier NoRevAvail = new ASN1ObjectIdentifier("2.5.29.56");
    /** @deprecated */
    public static final ASN1ObjectIdentifier TargetInformation = new ASN1ObjectIdentifier("2.5.29.55");
    private Hashtable extensions;
    private Vector ordering;

    public static X509Extensions getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static X509Extensions getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof X509Extensions)) {
            if(var0 instanceof ASN1Sequence) {
                return new X509Extensions((ASN1Sequence)var0);
            } else if(var0 instanceof Extensions) {
                return new X509Extensions((ASN1Sequence)((Extensions)var0).toASN1Primitive());
            } else if(var0 instanceof ASN1TaggedObject) {
                return getInstance(((ASN1TaggedObject)var0).getObject());
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (X509Extensions)var0;
        }
    }

    public X509Extensions(ASN1Sequence var1) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();

        ASN1Sequence var3;
        for(Enumeration var2 = var1.getObjects(); var2.hasMoreElements(); this.ordering.addElement(var3.getObjectAt(0))) {
            var3 = ASN1Sequence.getInstance(var2.nextElement());
            if(var3.size() == 3) {
                this.extensions.put(var3.getObjectAt(0), new X509Extension(ASN1Boolean.getInstance(var3.getObjectAt(1)), ASN1OctetString.getInstance(var3.getObjectAt(2))));
            } else {
                if(var3.size() != 2) {
                    throw new IllegalArgumentException("Bad sequence size: " + var3.size());
                }

                this.extensions.put(var3.getObjectAt(0), new X509Extension(false, ASN1OctetString.getInstance(var3.getObjectAt(1))));
            }
        }

    }

    public X509Extensions(Hashtable var1) {
        this((Vector)null, (Hashtable)var1);
    }

    /** @deprecated */
    public X509Extensions(Vector var1, Hashtable var2) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration var3;
        if(var1 == null) {
            var3 = var2.keys();
        } else {
            var3 = var1.elements();
        }

        while(var3.hasMoreElements()) {
            this.ordering.addElement(ASN1ObjectIdentifier.getInstance(var3.nextElement()));
        }

        var3 = this.ordering.elements();

        while(var3.hasMoreElements()) {
            ASN1ObjectIdentifier var4 = ASN1ObjectIdentifier.getInstance(var3.nextElement());
            X509Extension var5 = (X509Extension)var2.get(var4);
            this.extensions.put(var4, var5);
        }

    }

    /** @deprecated */
    public X509Extensions(Vector var1, Vector var2) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration var3 = var1.elements();

        while(var3.hasMoreElements()) {
            this.ordering.addElement(var3.nextElement());
        }

        int var4 = 0;

        for(var3 = this.ordering.elements(); var3.hasMoreElements(); ++var4) {
            ASN1ObjectIdentifier var5 = (ASN1ObjectIdentifier)var3.nextElement();
            X509Extension var6 = (X509Extension)var2.elementAt(var4);
            this.extensions.put(var5, var6);
        }

    }

    public Enumeration oids() {
        return this.ordering.elements();
    }

    public X509Extension getExtension(ASN1ObjectIdentifier var1) {
        return (X509Extension)this.extensions.get(var1);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector var1 = new ASN1EncodableVector();
        Enumeration var2 = this.ordering.elements();

        while(var2.hasMoreElements()) {
            ASN1ObjectIdentifier var3 = (ASN1ObjectIdentifier)var2.nextElement();
            X509Extension var4 = (X509Extension)this.extensions.get(var3);
            ASN1EncodableVector var5 = new ASN1EncodableVector();
            var5.add(var3);
            if(var4.isCritical()) {
                var5.add(ASN1Boolean.TRUE);
            }

            var5.add(var4.getValue());
            var1.add(new DERSequence(var5));
        }

        return new DERSequence(var1);
    }

    public boolean equivalent(X509Extensions var1) {
        if(this.extensions.size() != var1.extensions.size()) {
            return false;
        } else {
            Enumeration var2 = this.extensions.keys();

            Object var3;
            do {
                if(!var2.hasMoreElements()) {
                    return true;
                }

                var3 = var2.nextElement();
            } while(this.extensions.get(var3).equals(var1.extensions.get(var3)));

            return false;
        }
    }

    public ASN1ObjectIdentifier[] getExtensionOIDs() {
        return this.toOidArray(this.ordering);
    }

    public ASN1ObjectIdentifier[] getNonCriticalExtensionOIDs() {
        return this.getExtensionOIDs(false);
    }

    public ASN1ObjectIdentifier[] getCriticalExtensionOIDs() {
        return this.getExtensionOIDs(true);
    }

    private ASN1ObjectIdentifier[] getExtensionOIDs(boolean var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 != this.ordering.size(); ++var3) {
            Object var4 = this.ordering.elementAt(var3);
            if(((X509Extension)this.extensions.get(var4)).isCritical() == var1) {
                var2.addElement(var4);
            }
        }

        return this.toOidArray(var2);
    }

    private ASN1ObjectIdentifier[] toOidArray(Vector var1) {
        ASN1ObjectIdentifier[] var2 = new ASN1ObjectIdentifier[var1.size()];

        for(int var3 = 0; var3 != var2.length; ++var3) {
            var2[var3] = (ASN1ObjectIdentifier)var1.elementAt(var3);
        }

        return var2;
    }
}

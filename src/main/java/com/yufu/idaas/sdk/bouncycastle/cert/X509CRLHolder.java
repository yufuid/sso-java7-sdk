package com.yufu.idaas.sdk.bouncycastle.cert;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1InputStream;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.DEROutputStream;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.*;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentVerifier;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentVerifierProvider;
import com.yufu.idaas.sdk.bouncycastle.util.Encodable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by mac on 2017/1/18.
 */
public class X509CRLHolder implements Encodable {
    private CertificateList x509CRL;
    private boolean isIndirect;
    private Extensions extensions;
    private GeneralNames issuerName;

    private static CertificateList parseStream(InputStream var0) throws IOException {
        try {
            return CertificateList.getInstance((new ASN1InputStream(var0, true)).readObject());
        } catch (ClassCastException var2) {
            throw new CertIOException("malformed data: " + var2.getMessage(), var2);
        } catch (IllegalArgumentException var3) {
            throw new CertIOException("malformed data: " + var3.getMessage(), var3);
        }
    }

    private static boolean isIndirectCRL(Extensions var0) {
        if(var0 == null) {
            return false;
        } else {
            Extension var1 = var0.getExtension(Extension.issuingDistributionPoint);
            return var1 != null && IssuingDistributionPoint.getInstance(var1.getParsedValue()).isIndirectCRL();
        }
    }

    public X509CRLHolder(byte[] var1) throws IOException {
        this(parseStream(new ByteArrayInputStream(var1)));
    }

    public X509CRLHolder(InputStream var1) throws IOException {
        this(parseStream(var1));
    }

    public X509CRLHolder(CertificateList var1) {
        this.x509CRL = var1;
        this.extensions = var1.getTBSCertList().getExtensions();
        this.isIndirect = isIndirectCRL(this.extensions);
        this.issuerName = new GeneralNames(new GeneralName(var1.getIssuer()));
    }

    public byte[] getEncoded() throws IOException {
        return this.x509CRL.getEncoded();
    }

    public X500Name getIssuer() {
        return X500Name.getInstance(this.x509CRL.getIssuer());
    }

    public X509CRLEntryHolder getRevokedCertificate(BigInteger var1) {
        GeneralNames var2 = this.issuerName;
        Enumeration var3 = this.x509CRL.getRevokedCertificateEnumeration();

        while(var3.hasMoreElements()) {
            TBSCertList.CRLEntry var4 = (TBSCertList.CRLEntry)var3.nextElement();
            if(var4.getUserCertificate().getValue().equals(var1)) {
                return new X509CRLEntryHolder(var4, this.isIndirect, var2);
            }

            if(this.isIndirect && var4.hasExtensions()) {
                Extension var5 = var4.getExtensions().getExtension(Extension.certificateIssuer);
                if(var5 != null) {
                    var2 = GeneralNames.getInstance(var5.getParsedValue());
                }
            }
        }

        return null;
    }

    public Collection getRevokedCertificates() {
        TBSCertList.CRLEntry[] var1 = this.x509CRL.getRevokedCertificates();
        ArrayList var2 = new ArrayList(var1.length);
        GeneralNames var3 = this.issuerName;

        X509CRLEntryHolder var6;
        for(Enumeration var4 = this.x509CRL.getRevokedCertificateEnumeration(); var4.hasMoreElements(); var3 = var6.getCertificateIssuer()) {
            TBSCertList.CRLEntry var5 = (TBSCertList.CRLEntry)var4.nextElement();
            var6 = new X509CRLEntryHolder(var5, this.isIndirect, var3);
            var2.add(var6);
        }

        return var2;
    }

    public boolean hasExtensions() {
        return this.extensions != null;
    }

    public Extension getExtension(ASN1ObjectIdentifier var1) {
        return this.extensions != null?this.extensions.getExtension(var1):null;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public List getExtensionOIDs() {
        return CertUtils.getExtensionOIDs(this.extensions);
    }

    public Set getCriticalExtensionOIDs() {
        return CertUtils.getCriticalExtensionOIDs(this.extensions);
    }

    public Set getNonCriticalExtensionOIDs() {
        return CertUtils.getNonCriticalExtensionOIDs(this.extensions);
    }

    public CertificateList toASN1Structure() {
        return this.x509CRL;
    }

    public boolean isSignatureValid(ContentVerifierProvider var1) throws CertException {
        TBSCertList var2 = this.x509CRL.getTBSCertList();
        if(!CertUtils.isAlgIdEqual(var2.getSignature(), this.x509CRL.getSignatureAlgorithm())) {
            throw new CertException("signature invalid - algorithm identifier mismatch");
        } else {
            ContentVerifier var3;
            try {
                var3 = var1.get(var2.getSignature());
                OutputStream var4 = var3.getOutputStream();
                DEROutputStream var5 = new DEROutputStream(var4);
                var5.writeObject(var2);
                var4.close();
            } catch (Exception var6) {
                throw new CertException("unable to process signature: " + var6.getMessage(), var6);
            }

            return var3.verify(this.x509CRL.getSignature().getOctets());
        }
    }

    public boolean equals(Object var1) {
        if(var1 == this) {
            return true;
        } else if(!(var1 instanceof X509CRLHolder)) {
            return false;
        } else {
            X509CRLHolder var2 = (X509CRLHolder)var1;
            return this.x509CRL.equals(var2.x509CRL);
        }
    }

    public int hashCode() {
        return this.x509CRL.hashCode();
    }
}


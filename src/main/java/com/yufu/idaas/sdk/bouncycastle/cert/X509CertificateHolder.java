package com.yufu.idaas.sdk.bouncycastle.cert;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Primitive;
import com.yufu.idaas.sdk.bouncycastle.asn1.DEROutputStream;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.*;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentVerifier;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentVerifierProvider;
import com.yufu.idaas.sdk.bouncycastle.util.Encodable;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by mac on 2017/1/18.
 */
public class X509CertificateHolder implements Encodable {
    private Certificate x509Certificate;
    private Extensions extensions;

    private static Certificate parseBytes(byte[] var0) throws IOException {
        try {
            return Certificate.getInstance(ASN1Primitive.fromByteArray(var0));
        } catch (ClassCastException var2) {
            throw new CertIOException("malformed data: " + var2.getMessage(), var2);
        } catch (IllegalArgumentException var3) {
            throw new CertIOException("malformed data: " + var3.getMessage(), var3);
        }
    }

    public X509CertificateHolder(byte[] var1) throws IOException {
        this(parseBytes(var1));
    }

    public X509CertificateHolder(Certificate var1) {
        this.x509Certificate = var1;
        this.extensions = var1.getTBSCertificate().getExtensions();
    }

    public int getVersionNumber() {
        return this.x509Certificate.getVersionNumber();
    }

    /** @deprecated */
    public int getVersion() {
        return this.x509Certificate.getVersionNumber();
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

    public BigInteger getSerialNumber() {
        return this.x509Certificate.getSerialNumber().getValue();
    }

    public X500Name getIssuer() {
        return X500Name.getInstance(this.x509Certificate.getIssuer());
    }

    public X500Name getSubject() {
        return X500Name.getInstance(this.x509Certificate.getSubject());
    }

    public Date getNotBefore() {
        return this.x509Certificate.getStartDate().getDate();
    }

    public Date getNotAfter() {
        return this.x509Certificate.getEndDate().getDate();
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.x509Certificate.getSubjectPublicKeyInfo();
    }

    public Certificate toASN1Structure() {
        return this.x509Certificate;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.x509Certificate.getSignatureAlgorithm();
    }

    public byte[] getSignature() {
        return this.x509Certificate.getSignature().getOctets();
    }

    public boolean isValidOn(Date var1) {
        return !var1.before(this.x509Certificate.getStartDate().getDate()) && !var1.after(this.x509Certificate.getEndDate().getDate());
    }

    public boolean isSignatureValid(ContentVerifierProvider var1) throws CertException {
        TBSCertificate var2 = this.x509Certificate.getTBSCertificate();
        if(!CertUtils.isAlgIdEqual(var2.getSignature(), this.x509Certificate.getSignatureAlgorithm())) {
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

            return var3.verify(this.getSignature());
        }
    }

    public boolean equals(Object var1) {
        if(var1 == this) {
            return true;
        } else if(!(var1 instanceof X509CertificateHolder)) {
            return false;
        } else {
            X509CertificateHolder var2 = (X509CertificateHolder)var1;
            return this.x509Certificate.equals(var2.x509Certificate);
        }
    }

    public int hashCode() {
        return this.x509Certificate.hashCode();
    }

    public byte[] getEncoded() throws IOException {
        return this.x509Certificate.getEncoded();
    }
}


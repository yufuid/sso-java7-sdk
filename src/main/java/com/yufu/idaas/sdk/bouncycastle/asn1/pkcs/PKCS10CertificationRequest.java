package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Primitive;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Set;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentVerifier;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentVerifierProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PKCS10CertificationRequest {
    private static Attribute[] EMPTY_ARRAY = new Attribute[0];
    private CertificationRequest certificationRequest;

    private static CertificationRequest parseBytes(byte[] var0) throws IOException {
        try {
            return CertificationRequest.getInstance(ASN1Primitive.fromByteArray(var0));
        } catch (ClassCastException var2) {
            throw new PKCSIOException("malformed data: " + var2.getMessage(), var2);
        } catch (IllegalArgumentException var3) {
            throw new PKCSIOException("malformed data: " + var3.getMessage(), var3);
        }
    }

    public PKCS10CertificationRequest(CertificationRequest var1) {
        this.certificationRequest = var1;
    }

    public PKCS10CertificationRequest(byte[] var1) throws IOException {
        this(parseBytes(var1));
    }

    public CertificationRequest toASN1Structure() {
        return this.certificationRequest;
    }

    public X500Name getSubject() {
        return X500Name.getInstance(this.certificationRequest.getCertificationRequestInfo().getSubject());
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.certificationRequest.getSignatureAlgorithm();
    }

    public byte[] getSignature() {
        return this.certificationRequest.getSignature().getOctets();
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.certificationRequest.getCertificationRequestInfo().getSubjectPublicKeyInfo();
    }

    public Attribute[] getAttributes() {
        ASN1Set var1 = this.certificationRequest.getCertificationRequestInfo().getAttributes();
        if(var1 == null) {
            return EMPTY_ARRAY;
        } else {
            Attribute[] var2 = new Attribute[var1.size()];

            for(int var3 = 0; var3 != var1.size(); ++var3) {
                var2[var3] = Attribute.getInstance(var1.getObjectAt(var3));
            }

            return var2;
        }
    }

    public Attribute[] getAttributes(ASN1ObjectIdentifier var1) {
        ASN1Set var2 = this.certificationRequest.getCertificationRequestInfo().getAttributes();
        if(var2 == null) {
            return EMPTY_ARRAY;
        } else {
            ArrayList var3 = new ArrayList();

            for(int var4 = 0; var4 != var2.size(); ++var4) {
                Attribute var5 = Attribute.getInstance(var2.getObjectAt(var4));
                if(var5.getAttrType().equals(var1)) {
                    var3.add(var5);
                }
            }

            if(var3.size() == 0) {
                return EMPTY_ARRAY;
            } else {
                return (Attribute[])((Attribute[])var3.toArray(new Attribute[var3.size()]));
            }
        }
    }

    public byte[] getEncoded() throws IOException {
        return this.certificationRequest.getEncoded();
    }

    public boolean isSignatureValid(ContentVerifierProvider var1) throws PKCSException {
        CertificationRequestInfo var2 = this.certificationRequest.getCertificationRequestInfo();

        ContentVerifier var3;
        try {
            var3 = var1.get(this.certificationRequest.getSignatureAlgorithm());
            OutputStream var4 = var3.getOutputStream();
            var4.write(var2.getEncoded("DER"));
            var4.close();
        } catch (Exception var5) {
            throw new PKCSException("unable to process signature: " + var5.getMessage(), var5);
        }

        return var3.verify(this.getSignature());
    }

    public boolean equals(Object var1) {
        if(var1 == this) {
            return true;
        } else if(!(var1 instanceof PKCS10CertificationRequest)) {
            return false;
        } else {
            PKCS10CertificationRequest var2 = (PKCS10CertificationRequest)var1;
            return this.toASN1Structure().equals(var2.toASN1Structure());
        }
    }

    public int hashCode() {
        return this.toASN1Structure().hashCode();
    }
}

package com.yufu.idaas.sdk.bouncycastle.cert;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Encodable;
import com.yufu.idaas.sdk.bouncycastle.asn1.x500.X500Name;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.AttCertIssuer;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.GeneralName;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.GeneralNames;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.V2Form;
import com.yufu.idaas.sdk.bouncycastle.util.Selector;

import java.util.ArrayList;

/**
 * Created by mac on 2017/1/18.
 */
public class AttributeCertificateIssuer implements Selector {
    final ASN1Encodable form;

    public AttributeCertificateIssuer(AttCertIssuer var1) {
        this.form = var1.getIssuer();
    }

    public AttributeCertificateIssuer(X500Name var1) {
        this.form = new V2Form(new GeneralNames(new GeneralName(var1)));
    }

    public X500Name[] getNames() {
        GeneralNames var1;
        if(this.form instanceof V2Form) {
            var1 = ((V2Form)this.form).getIssuerName();
        } else {
            var1 = (GeneralNames)this.form;
        }

        GeneralName[] var2 = var1.getNames();
        ArrayList var3 = new ArrayList(var2.length);

        for(int var4 = 0; var4 != var2.length; ++var4) {
            if(var2[var4].getTagNo() == 4) {
                var3.add(X500Name.getInstance(var2[var4].getName()));
            }
        }

        return (X500Name[])((X500Name[])var3.toArray(new X500Name[var3.size()]));
    }

    private boolean matchesDN(X500Name var1, GeneralNames var2) {
        GeneralName[] var3 = var2.getNames();

        for(int var4 = 0; var4 != var3.length; ++var4) {
            GeneralName var5 = var3[var4];
            if(var5.getTagNo() == 4 && X500Name.getInstance(var5.getName()).equals(var1)) {
                return true;
            }
        }

        return false;
    }

    public Object clone() {
        return new AttributeCertificateIssuer(AttCertIssuer.getInstance(this.form));
    }

    public boolean equals(Object var1) {
        if(var1 == this) {
            return true;
        } else if(!(var1 instanceof AttributeCertificateIssuer)) {
            return false;
        } else {
            AttributeCertificateIssuer var2 = (AttributeCertificateIssuer)var1;
            return this.form.equals(var2.form);
        }
    }

    public int hashCode() {
        return this.form.hashCode();
    }

    public boolean match(Object var1) {
        if(!(var1 instanceof X509CertificateHolder)) {
            return false;
        } else {
            X509CertificateHolder var2 = (X509CertificateHolder)var1;
            if(this.form instanceof V2Form) {
                V2Form var3 = (V2Form)this.form;
                if(var3.getBaseCertificateID() != null) {
                    return var3.getBaseCertificateID().getSerial().getValue().equals(var2.getSerialNumber()) && this.matchesDN(var2.getIssuer(), var3.getBaseCertificateID().getIssuer());
                }

                GeneralNames var4 = var3.getIssuerName();
                if(this.matchesDN(var2.getSubject(), var4)) {
                    return true;
                }
            } else {
                GeneralNames var5 = (GeneralNames)this.form;
                if(this.matchesDN(var2.getSubject(), var5)) {
                    return true;
                }
            }

            return false;
        }
    }
}

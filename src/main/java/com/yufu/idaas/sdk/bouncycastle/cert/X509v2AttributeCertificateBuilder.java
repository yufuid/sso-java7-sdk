package com.yufu.idaas.sdk.bouncycastle.cert;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.asn1.x509.*;
import com.yufu.idaas.sdk.bouncycastle.operator.ContentSigner;

import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mac on 2017/1/18.
 */
public class X509v2AttributeCertificateBuilder {
    private V2AttributeCertificateInfoGenerator acInfoGen = new V2AttributeCertificateInfoGenerator();
    private ExtensionsGenerator extGenerator = new ExtensionsGenerator();

    public X509v2AttributeCertificateBuilder(AttributeCertificateHolder var1, AttributeCertificateIssuer var2, BigInteger var3, Date var4, Date var5) {
        this.acInfoGen.setHolder(var1.holder);
        this.acInfoGen.setIssuer(AttCertIssuer.getInstance(var2.form));
        this.acInfoGen.setSerialNumber(new ASN1Integer(var3));
        this.acInfoGen.setStartDate(new ASN1GeneralizedTime(var4));
        this.acInfoGen.setEndDate(new ASN1GeneralizedTime(var5));
    }

    public X509v2AttributeCertificateBuilder(AttributeCertificateHolder var1, AttributeCertificateIssuer var2, BigInteger var3, Date var4, Date var5, Locale var6) {
        this.acInfoGen.setHolder(var1.holder);
        this.acInfoGen.setIssuer(AttCertIssuer.getInstance(var2.form));
        this.acInfoGen.setSerialNumber(new ASN1Integer(var3));
        this.acInfoGen.setStartDate(new ASN1GeneralizedTime(var4, var6));
        this.acInfoGen.setEndDate(new ASN1GeneralizedTime(var5, var6));
    }

    public X509v2AttributeCertificateBuilder addAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
        this.acInfoGen.addAttribute(new Attribute(var1, new DERSet(var2)));
        return this;
    }

    public X509v2AttributeCertificateBuilder addAttribute(ASN1ObjectIdentifier var1, ASN1Encodable[] var2) {
        this.acInfoGen.addAttribute(new Attribute(var1, new DERSet(var2)));
        return this;
    }

    public void setIssuerUniqueId(boolean[] var1) {
        this.acInfoGen.setIssuerUniqueID(CertUtils.booleanToBitString(var1));
    }

    public X509v2AttributeCertificateBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws CertIOException {
        CertUtils.addExtension(this.extGenerator, var1, var2, var3);
        return this;
    }

    public X509v2AttributeCertificateBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, byte[] var3) throws CertIOException {
        this.extGenerator.addExtension(var1, var2, var3);
        return this;
    }

    public X509v2AttributeCertificateBuilder addExtension(Extension var1) throws CertIOException {
        this.extGenerator.addExtension(var1);
        return this;
    }

    public X509AttributeCertificateHolder build(ContentSigner var1) {
        this.acInfoGen.setSignature(var1.getAlgorithmIdentifier());
        if(!this.extGenerator.isEmpty()) {
            this.acInfoGen.setExtensions(this.extGenerator.generate());
        }

        return CertUtils.generateFullAttrCert(var1, this.acInfoGen.generateAttributeCertificateInfo());
    }
}


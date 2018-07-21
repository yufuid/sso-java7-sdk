package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;

/**
 * Created by mac on 2017/1/18.
 */
public interface X509ObjectIdentifiers {
    ASN1ObjectIdentifier commonName = (new ASN1ObjectIdentifier("2.5.4.3")).intern();
    ASN1ObjectIdentifier countryName = (new ASN1ObjectIdentifier("2.5.4.6")).intern();
    ASN1ObjectIdentifier localityName = (new ASN1ObjectIdentifier("2.5.4.7")).intern();
    ASN1ObjectIdentifier stateOrProvinceName = (new ASN1ObjectIdentifier("2.5.4.8")).intern();
    ASN1ObjectIdentifier organization = (new ASN1ObjectIdentifier("2.5.4.10")).intern();
    ASN1ObjectIdentifier organizationalUnitName = (new ASN1ObjectIdentifier("2.5.4.11")).intern();
    ASN1ObjectIdentifier id_at_telephoneNumber = (new ASN1ObjectIdentifier("2.5.4.20")).intern();
    ASN1ObjectIdentifier id_at_name = (new ASN1ObjectIdentifier("2.5.4.41")).intern();
    ASN1ObjectIdentifier id_SHA1 = (new ASN1ObjectIdentifier("1.3.14.3.2.26")).intern();
    ASN1ObjectIdentifier ripemd160 = (new ASN1ObjectIdentifier("1.3.36.3.2.1")).intern();
    ASN1ObjectIdentifier ripemd160WithRSAEncryption = (new ASN1ObjectIdentifier("1.3.36.3.3.1.2")).intern();
    ASN1ObjectIdentifier id_ea_rsa = (new ASN1ObjectIdentifier("2.5.8.1.1")).intern();
    ASN1ObjectIdentifier id_pkix = new ASN1ObjectIdentifier("1.3.6.1.5.5.7");
    ASN1ObjectIdentifier id_pe = id_pkix.branch("1");
    ASN1ObjectIdentifier id_ce = new ASN1ObjectIdentifier("2.5.29");
    ASN1ObjectIdentifier id_ad = id_pkix.branch("48");
    ASN1ObjectIdentifier id_ad_caIssuers = id_ad.branch("2").intern();
    ASN1ObjectIdentifier id_ad_ocsp = id_ad.branch("1").intern();
    ASN1ObjectIdentifier ocspAccessMethod = id_ad_ocsp;
    ASN1ObjectIdentifier crlAccessMethod = id_ad_caIssuers;
}

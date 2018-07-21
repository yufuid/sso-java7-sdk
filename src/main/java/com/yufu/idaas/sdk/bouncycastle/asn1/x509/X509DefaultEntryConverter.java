package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;

import java.io.IOException;

/**
 * Created by mac on 2017/1/18.
 */
public class X509DefaultEntryConverter extends X509NameEntryConverter {
    public X509DefaultEntryConverter() {
    }

    public ASN1Primitive getConvertedValue(ASN1ObjectIdentifier var1, String var2) {
        if(var2.length() != 0 && var2.charAt(0) == 35) {
            try {
                return this.convertHexEncoded(var2, 1);
            } catch (IOException var4) {
                throw new RuntimeException("can\'t recode value for oid " + var1.getId());
            }
        } else {
            if(var2.length() != 0 && var2.charAt(0) == 92) {
                var2 = var2.substring(1);
            }

            return (ASN1Primitive)(!var1.equals(X509Name.EmailAddress) && !var1.equals(X509Name.DC)?(var1.equals(X509Name.DATE_OF_BIRTH)?new DERGeneralizedTime(var2):(!var1.equals(X509Name.C) && !var1.equals(X509Name.SN) && !var1.equals(X509Name.DN_QUALIFIER) && !var1.equals(X509Name.TELEPHONE_NUMBER)?new DERUTF8String(var2):new DERPrintableString(var2))):new DERIA5String(var2));
        }
    }
}
package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1InputStream;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Primitive;
import com.yufu.idaas.sdk.bouncycastle.asn1.DERPrintableString;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;

import java.io.IOException;

/**
 * Created by mac on 2017/1/18.
 */
public abstract class X509NameEntryConverter {
    public X509NameEntryConverter() {
    }

    protected ASN1Primitive convertHexEncoded(String var1, int var2) throws IOException {
        var1 = Strings.toLowerCase(var1);
        byte[] var3 = new byte[(var1.length() - var2) / 2];

        for(int var4 = 0; var4 != var3.length; ++var4) {
            char var5 = var1.charAt(var4 * 2 + var2);
            char var6 = var1.charAt(var4 * 2 + var2 + 1);
            if(var5 < 97) {
                var3[var4] = (byte)(var5 - 48 << 4);
            } else {
                var3[var4] = (byte)(var5 - 97 + 10 << 4);
            }

            if(var6 < 97) {
                var3[var4] |= (byte)(var6 - 48);
            } else {
                var3[var4] |= (byte)(var6 - 97 + 10);
            }
        }

        ASN1InputStream var7 = new ASN1InputStream(var3);
        return var7.readObject();
    }

    protected boolean canBePrintable(String var1) {
        return DERPrintableString.isPrintableString(var1);
    }

    public abstract ASN1Primitive getConvertedValue(ASN1ObjectIdentifier var1, String var2);
}

package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.*;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;

/**
 * Created by mac on 2017/1/18.
 */
public class GeneralNames extends ASN1Object {
    private final GeneralName[] names;

    public static GeneralNames getInstance(Object var0) {
        return var0 instanceof GeneralNames?(GeneralNames)var0:(var0 != null?new GeneralNames(ASN1Sequence.getInstance(var0)):null);
    }

    public static GeneralNames getInstance(ASN1TaggedObject var0, boolean var1) {
        return getInstance(ASN1Sequence.getInstance(var0, var1));
    }

    public static GeneralNames fromExtensions(Extensions var0, ASN1ObjectIdentifier var1) {
        return getInstance(var0.getExtensionParsedValue(var1));
    }

    public GeneralNames(GeneralName var1) {
        this.names = new GeneralName[]{var1};
    }

    public GeneralNames(GeneralName[] var1) {
        this.names = var1;
    }

    private GeneralNames(ASN1Sequence var1) {
        this.names = new GeneralName[var1.size()];

        for(int var2 = 0; var2 != var1.size(); ++var2) {
            this.names[var2] = GeneralName.getInstance(var1.getObjectAt(var2));
        }

    }

    public GeneralName[] getNames() {
        GeneralName[] var1 = new GeneralName[this.names.length];
        System.arraycopy(this.names, 0, var1, 0, this.names.length);
        return var1;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.names);
    }

    public String toString() {
        StringBuffer var1 = new StringBuffer();
        String var2 = Strings.lineSeparator();
        var1.append("GeneralNames:");
        var1.append(var2);

        for(int var3 = 0; var3 != this.names.length; ++var3) {
            var1.append("    ");
            var1.append(this.names[var3]);
            var1.append(var2);
        }

        return var1.toString();
    }
}


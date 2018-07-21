package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/17.
 */
public class DERSet extends ASN1Set {
    private int bodyLength = -1;

    public DERSet() {
    }

    public DERSet(ASN1Encodable var1) {
        super(var1);
    }

    public DERSet(ASN1EncodableVector var1) {
        super(var1, true);
    }

    public DERSet(ASN1Encodable[] var1) {
        super(var1, true);
    }

    DERSet(ASN1EncodableVector var1, boolean var2) {
        super(var1, var2);
    }

    private int getBodyLength() throws IOException {
        if(this.bodyLength < 0) {
            int var1 = 0;

            Object var3;
            for(Enumeration var2 = this.getObjects(); var2.hasMoreElements(); var1 += ((ASN1Encodable)var3).toASN1Primitive().toDERObject().encodedLength()) {
                var3 = var2.nextElement();
            }

            this.bodyLength = var1;
        }

        return this.bodyLength;
    }

    int encodedLength() throws IOException {
        int var1 = this.getBodyLength();
        return 1 + StreamUtil.calculateBodyLength(var1) + var1;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        ASN1OutputStream var2 = var1.getDERSubStream();
        int var3 = this.getBodyLength();
        var1.write(49);
        var1.writeLength(var3);
        Enumeration var4 = this.getObjects();

        while(var4.hasMoreElements()) {
            Object var5 = var4.nextElement();
            var2.writeObject((ASN1Encodable)var5);
        }

    }
}

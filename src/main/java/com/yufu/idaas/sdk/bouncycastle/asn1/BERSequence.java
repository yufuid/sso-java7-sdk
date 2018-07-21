package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/17.
 */
public class BERSequence extends ASN1Sequence {
    public BERSequence() {
    }

    public BERSequence(ASN1Encodable var1) {
        super(var1);
    }

    public BERSequence(ASN1EncodableVector var1) {
        super(var1);
    }

    public BERSequence(ASN1Encodable[] var1) {
        super(var1);
    }

    int encodedLength() throws IOException {
        int var1 = 0;

        for(Enumeration var2 = this.getObjects(); var2.hasMoreElements(); var1 += ((ASN1Encodable)var2.nextElement()).toASN1Primitive().encodedLength()) {
            ;
        }

        return 2 + var1 + 2;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.write(48);
        var1.write(128);
        Enumeration var2 = this.getObjects();

        while(var2.hasMoreElements()) {
            var1.writeObject((ASN1Encodable)var2.nextElement());
        }

        var1.write(0);
        var1.write(0);
    }
}

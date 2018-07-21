package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/17.
 */
class LazyEncodedSequence extends ASN1Sequence {
    private byte[] encoded;

    LazyEncodedSequence(byte[] var1) throws IOException {
        this.encoded = var1;
    }

    private void parse() {
        LazyConstructionEnumeration var1 = new LazyConstructionEnumeration(this.encoded);

        while(var1.hasMoreElements()) {
            this.seq.addElement(var1.nextElement());
        }

        this.encoded = null;
    }

    public synchronized ASN1Encodable getObjectAt(int var1) {
        if(this.encoded != null) {
            this.parse();
        }

        return super.getObjectAt(var1);
    }

    public synchronized Enumeration getObjects() {
        return (Enumeration)(this.encoded == null?super.getObjects():new LazyConstructionEnumeration(this.encoded));
    }

    public synchronized int size() {
        if(this.encoded != null) {
            this.parse();
        }

        return super.size();
    }

    ASN1Primitive toDERObject() {
        if(this.encoded != null) {
            this.parse();
        }

        return super.toDERObject();
    }

    ASN1Primitive toDLObject() {
        if(this.encoded != null) {
            this.parse();
        }

        return super.toDLObject();
    }

    int encodedLength() throws IOException {
        return this.encoded != null?1 + StreamUtil.calculateBodyLength(this.encoded.length) + this.encoded.length:super.toDLObject().encodedLength();
    }

    void encode(ASN1OutputStream var1) throws IOException {
        if(this.encoded != null) {
            var1.writeEncoded(48, this.encoded);
        } else {
            super.toDLObject().encode(var1);
        }

    }
}

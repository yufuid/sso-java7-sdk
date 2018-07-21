package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class BERApplicationSpecific extends ASN1ApplicationSpecific {
    BERApplicationSpecific(boolean var1, int var2, byte[] var3) {
        super(var1, var2, var3);
    }

    public BERApplicationSpecific(int var1, ASN1Encodable var2) throws IOException {
        this(true, var1, var2);
    }

    public BERApplicationSpecific(boolean var1, int var2, ASN1Encodable var3) throws IOException {
        super(var1 || var3.toASN1Primitive().isConstructed(), var2, getEncoding(var1, var3));
    }

    private static byte[] getEncoding(boolean var0, ASN1Encodable var1) throws IOException {
        byte[] var2 = var1.toASN1Primitive().getEncoded("BER");
        if(var0) {
            return var2;
        } else {
            int var3 = getLengthOfHeader(var2);
            byte[] var4 = new byte[var2.length - var3];
            System.arraycopy(var2, var3, var4, 0, var4.length);
            return var4;
        }
    }

    public BERApplicationSpecific(int var1, ASN1EncodableVector var2) {
        super(true, var1, getEncodedVector(var2));
    }

    private static byte[] getEncodedVector(ASN1EncodableVector var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        for(int var2 = 0; var2 != var0.size(); ++var2) {
            try {
                var1.write(((ASN1Object)var0.get(var2)).getEncoded("BER"));
            } catch (IOException var4) {
                throw new ASN1ParsingException("malformed object: " + var4, var4);
            }
        }

        return var1.toByteArray();
    }

    void encode(ASN1OutputStream var1) throws IOException {
        int var2 = 64;
        if(this.isConstructed) {
            var2 |= 32;
        }

        var1.writeTag(var2, this.tag);
        var1.write(128);
        var1.write(this.octets);
        var1.write(0);
        var1.write(0);
    }
}

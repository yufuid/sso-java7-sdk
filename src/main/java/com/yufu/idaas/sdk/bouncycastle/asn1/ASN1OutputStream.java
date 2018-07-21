package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mac on 2017/1/17.
 */
public class ASN1OutputStream {
    private OutputStream os;

    public ASN1OutputStream(OutputStream var1) {
        this.os = var1;
    }

    void writeLength(int var1) throws IOException {
        if(var1 > 127) {
            int var2 = 1;

            for(int var3 = var1; (var3 >>>= 8) != 0; ++var2) {
                ;
            }

            this.write((byte)(var2 | 128));

            for(int var4 = (var2 - 1) * 8; var4 >= 0; var4 -= 8) {
                this.write((byte)(var1 >> var4));
            }
        } else {
            this.write((byte)var1);
        }

    }

    void write(int var1) throws IOException {
        this.os.write(var1);
    }

    void write(byte[] var1) throws IOException {
        this.os.write(var1);
    }

    void write(byte[] var1, int var2, int var3) throws IOException {
        this.os.write(var1, var2, var3);
    }

    void writeEncoded(int var1, byte[] var2) throws IOException {
        this.write(var1);
        this.writeLength(var2.length);
        this.write(var2);
    }

    void writeTag(int var1, int var2) throws IOException {
        if(var2 < 31) {
            this.write(var1 | var2);
        } else {
            this.write(var1 | 31);
            if(var2 < 128) {
                this.write(var2);
            } else {
                byte[] var3 = new byte[5];
                int var4 = var3.length;
                --var4;
                var3[var4] = (byte)(var2 & 127);

                do {
                    var2 >>= 7;
                    --var4;
                    var3[var4] = (byte)(var2 & 127 | 128);
                } while(var2 > 127);

                this.write(var3, var4, var3.length - var4);
            }
        }

    }

    void writeEncoded(int var1, int var2, byte[] var3) throws IOException {
        this.writeTag(var1, var2);
        this.writeLength(var3.length);
        this.write(var3);
    }

    protected void writeNull() throws IOException {
        this.os.write(5);
        this.os.write(0);
    }

    public void writeObject(ASN1Encodable var1) throws IOException {
        if(var1 != null) {
            var1.toASN1Primitive().encode(this);
        } else {
            throw new IOException("null object detected");
        }
    }

    void writeImplicitObject(ASN1Primitive var1) throws IOException {
        if(var1 != null) {
            var1.encode(new ImplicitOutputStream(this.os));
        } else {
            throw new IOException("null object detected");
        }
    }

    public void close() throws IOException {
        this.os.close();
    }

    public void flush() throws IOException {
        this.os.flush();
    }

    ASN1OutputStream getDERSubStream() {
        return new DEROutputStream(this.os);
    }

    ASN1OutputStream getDLSubStream() {
        return new DLOutputStream(this.os);
    }

    private class ImplicitOutputStream extends ASN1OutputStream {
        private boolean first = true;

        public ImplicitOutputStream(OutputStream var2) {
            super(var2);
        }

        public void write(int var1) throws IOException {
            if(this.first) {
                this.first = false;
            } else {
                super.write(var1);
            }

        }
    }
}

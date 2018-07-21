package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by mac on 2017/1/17.
 */
public class BEROctetString extends ASN1OctetString {
    private static final int MAX_LENGTH = 1000;
    private ASN1OctetString[] octs;

    private static byte[] toBytes(ASN1OctetString[] var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        for(int var2 = 0; var2 != var0.length; ++var2) {
            try {
                DEROctetString var3 = (DEROctetString)var0[var2];
                var1.write(var3.getOctets());
            } catch (ClassCastException var4) {
                throw new IllegalArgumentException(var0[var2].getClass().getName() + " found in input should only contain DEROctetString");
            } catch (IOException var5) {
                throw new IllegalArgumentException("exception converting octets " + var5.toString());
            }
        }

        return var1.toByteArray();
    }

    public BEROctetString(byte[] var1) {
        super(var1);
    }

    public BEROctetString(ASN1OctetString[] var1) {
        super(toBytes(var1));
        this.octs = var1;
    }

    public byte[] getOctets() {
        return this.string;
    }

    public Enumeration getObjects() {
        return this.octs == null?this.generateOcts().elements():new Enumeration() {
            int counter = 0;

            public boolean hasMoreElements() {
                return this.counter < BEROctetString.this.octs.length;
            }

            public Object nextElement() {
                return BEROctetString.this.octs[this.counter++];
            }
        };
    }

    private Vector generateOcts() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.string.length; var2 += 1000) {
            int var3;
            if(var2 + 1000 > this.string.length) {
                var3 = this.string.length;
            } else {
                var3 = var2 + 1000;
            }

            byte[] var4 = new byte[var3 - var2];
            System.arraycopy(this.string, var2, var4, 0, var4.length);
            var1.addElement(new DEROctetString(var4));
        }

        return var1;
    }

    boolean isConstructed() {
        return true;
    }

    int encodedLength() throws IOException {
        int var1 = 0;

        for(Enumeration var2 = this.getObjects(); var2.hasMoreElements(); var1 += ((ASN1Encodable)var2.nextElement()).toASN1Primitive().encodedLength()) {
            ;
        }

        return 2 + var1 + 2;
    }

    public void encode(ASN1OutputStream var1) throws IOException {
        var1.write(36);
        var1.write(128);
        Enumeration var2 = this.getObjects();

        while(var2.hasMoreElements()) {
            var1.writeObject((ASN1Encodable)var2.nextElement());
        }

        var1.write(0);
        var1.write(0);
    }

    static BEROctetString fromSequence(ASN1Sequence var0) {
        ASN1OctetString[] var1 = new ASN1OctetString[var0.size()];
        Enumeration var2 = var0.getObjects();

        for(int var3 = 0; var2.hasMoreElements(); var1[var3++] = (ASN1OctetString)var2.nextElement()) {
            ;
        }

        return new BEROctetString(var1);
    }
}

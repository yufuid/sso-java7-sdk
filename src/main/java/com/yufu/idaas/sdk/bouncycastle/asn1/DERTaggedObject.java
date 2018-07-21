package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERTaggedObject extends ASN1TaggedObject {
    private static final byte[] ZERO_BYTES = new byte[0];

    public DERTaggedObject(boolean var1, int var2, ASN1Encodable var3) {
        super(var1, var2, var3);
    }

    public DERTaggedObject(int var1, ASN1Encodable var2) {
        super(true, var1, var2);
    }

    boolean isConstructed() {
        if(!this.empty) {
            if(this.explicit) {
                return true;
            } else {
                ASN1Primitive var1 = this.obj.toASN1Primitive().toDERObject();
                return var1.isConstructed();
            }
        } else {
            return true;
        }
    }

    int encodedLength() throws IOException {
        if(!this.empty) {
            ASN1Primitive var1 = this.obj.toASN1Primitive().toDERObject();
            int var2 = var1.encodedLength();
            if(this.explicit) {
                return StreamUtil.calculateTagLength(this.tagNo) + StreamUtil.calculateBodyLength(var2) + var2;
            } else {
                --var2;
                return StreamUtil.calculateTagLength(this.tagNo) + var2;
            }
        } else {
            return StreamUtil.calculateTagLength(this.tagNo) + 1;
        }
    }

    void encode(ASN1OutputStream var1) throws IOException {
        if(!this.empty) {
            ASN1Primitive var2 = this.obj.toASN1Primitive().toDERObject();
            if(this.explicit) {
                var1.writeTag(160, this.tagNo);
                var1.writeLength(var2.encodedLength());
                var1.writeObject(var2);
            } else {
                short var3;
                if(var2.isConstructed()) {
                    var3 = 160;
                } else {
                    var3 = 128;
                }

                var1.writeTag(var3, this.tagNo);
                var1.writeImplicitObject(var2);
            }
        } else {
            var1.writeEncoded(160, this.tagNo, ZERO_BYTES);
        }

    }
}

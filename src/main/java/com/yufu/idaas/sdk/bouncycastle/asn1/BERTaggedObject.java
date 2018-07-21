package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/17.
 */
public class BERTaggedObject extends ASN1TaggedObject {
    public BERTaggedObject(int var1, ASN1Encodable var2) {
        super(true, var1, var2);
    }

    public BERTaggedObject(boolean var1, int var2, ASN1Encodable var3) {
        super(var1, var2, var3);
    }

    public BERTaggedObject(int var1) {
        super(false, var1, new BERSequence());
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
            ASN1Primitive var1 = this.obj.toASN1Primitive();
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
        var1.writeTag(160, this.tagNo);
        var1.write(128);
        if(!this.empty) {
            if(!this.explicit) {
                Enumeration var2;
                if(this.obj instanceof ASN1OctetString) {
                    if(this.obj instanceof BEROctetString) {
                        var2 = ((BEROctetString)this.obj).getObjects();
                    } else {
                        ASN1OctetString var3 = (ASN1OctetString)this.obj;
                        BEROctetString var4 = new BEROctetString(var3.getOctets());
                        var2 = var4.getObjects();
                    }
                } else if(this.obj instanceof ASN1Sequence) {
                    var2 = ((ASN1Sequence)this.obj).getObjects();
                } else {
                    if(!(this.obj instanceof ASN1Set)) {
                        throw new RuntimeException("not implemented: " + this.obj.getClass().getName());
                    }

                    var2 = ((ASN1Set)this.obj).getObjects();
                }

                while(var2.hasMoreElements()) {
                    var1.writeObject((ASN1Encodable)var2.nextElement());
                }
            } else {
                var1.writeObject(this.obj);
            }
        }

        var1.write(0);
        var1.write(0);
    }
}

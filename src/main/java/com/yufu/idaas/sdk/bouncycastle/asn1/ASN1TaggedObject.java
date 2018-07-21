package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public abstract class ASN1TaggedObject extends ASN1Primitive implements ASN1TaggedObjectParser {
    int tagNo;
    boolean empty = false;
    boolean explicit = true;
    ASN1Encodable obj = null;

    public static ASN1TaggedObject getInstance(ASN1TaggedObject var0, boolean var1) {
        if(var1) {
            return (ASN1TaggedObject)var0.getObject();
        } else {
            throw new IllegalArgumentException("implicitly tagged tagged object");
        }
    }

    public static ASN1TaggedObject getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof ASN1TaggedObject)) {
            if(var0 instanceof byte[]) {
                try {
                    return getInstance(fromByteArray((byte[])((byte[])var0)));
                } catch (IOException var2) {
                    throw new IllegalArgumentException("failed to construct tagged object from byte[]: " + var2.getMessage());
                }
            } else {
                throw new IllegalArgumentException("unknown object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (ASN1TaggedObject)var0;
        }
    }

    public ASN1TaggedObject(boolean var1, int var2, ASN1Encodable var3) {
        if(var3 instanceof ASN1Choice) {
            this.explicit = true;
        } else {
            this.explicit = var1;
        }

        this.tagNo = var2;
        if(this.explicit) {
            this.obj = var3;
        } else {
            ASN1Primitive var4 = var3.toASN1Primitive();
            if(var4 instanceof ASN1Set) {
                Object var5 = null;
            }

            this.obj = var3;
        }

    }

    boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof ASN1TaggedObject)) {
            return false;
        } else {
            ASN1TaggedObject var2 = (ASN1TaggedObject)var1;
            if(this.tagNo == var2.tagNo && this.empty == var2.empty && this.explicit == var2.explicit) {
                if(this.obj == null) {
                    if(var2.obj != null) {
                        return false;
                    }
                } else if(!this.obj.toASN1Primitive().equals(var2.obj.toASN1Primitive())) {
                    return false;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public int hashCode() {
        int var1 = this.tagNo;
        if(this.obj != null) {
            var1 ^= this.obj.hashCode();
        }

        return var1;
    }

    public int getTagNo() {
        return this.tagNo;
    }

    public boolean isExplicit() {
        return this.explicit;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public ASN1Primitive getObject() {
        return this.obj != null?this.obj.toASN1Primitive():null;
    }

    public ASN1Encodable getObjectParser(int var1, boolean var2) throws IOException {
        switch(var1) {
            case 4:
                return ASN1OctetString.getInstance(this, var2).parser();
            case 16:
                return ASN1Sequence.getInstance(this, var2).parser();
            case 17:
                return ASN1Set.getInstance(this, var2).parser();
            default:
                if(var2) {
                    return this.getObject();
                } else {
                    throw new ASN1Exception("implicit tagging not implemented for tag: " + var1);
                }
        }
    }

    public ASN1Primitive getLoadedObject() {
        return this.toASN1Primitive();
    }

    ASN1Primitive toDERObject() {
        return new DERTaggedObject(this.explicit, this.tagNo, this.obj);
    }

    ASN1Primitive toDLObject() {
        return new DLTaggedObject(this.explicit, this.tagNo, this.obj);
    }

    abstract void encode(ASN1OutputStream var1) throws IOException;

    public String toString() {
        return "[" + this.tagNo + "]" + this.obj;
    }
}


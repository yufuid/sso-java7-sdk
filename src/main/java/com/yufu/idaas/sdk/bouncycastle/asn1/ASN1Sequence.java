package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by mac on 2017/1/17.
 */
public abstract class ASN1Sequence extends ASN1Primitive implements Iterable<ASN1Encodable> {
    protected Vector seq = new Vector();

    public static ASN1Sequence getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof ASN1Sequence)) {
            if(var0 instanceof ASN1SequenceParser) {
                return getInstance(((ASN1SequenceParser)var0).toASN1Primitive());
            } else if(var0 instanceof byte[]) {
                try {
                    return getInstance(fromByteArray((byte[])((byte[])var0)));
                } catch (IOException var2) {
                    throw new IllegalArgumentException("failed to construct sequence from byte[]: " + var2.getMessage());
                }
            } else {
                if(var0 instanceof ASN1Encodable) {
                    ASN1Primitive var1 = ((ASN1Encodable)var0).toASN1Primitive();
                    if(var1 instanceof ASN1Sequence) {
                        return (ASN1Sequence)var1;
                    }
                }

                throw new IllegalArgumentException("unknown object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (ASN1Sequence)var0;
        }
    }

    public static ASN1Sequence getInstance(ASN1TaggedObject var0, boolean var1) {
        if(var1) {
            if(!var0.isExplicit()) {
                throw new IllegalArgumentException("object implicit - explicit expected.");
            } else {
                return getInstance(var0.getObject().toASN1Primitive());
            }
        } else if(var0.isExplicit()) {
            return (ASN1Sequence)(var0 instanceof BERTaggedObject?new BERSequence(var0.getObject()):new DLSequence(var0.getObject()));
        } else if(var0.getObject() instanceof ASN1Sequence) {
            return (ASN1Sequence)var0.getObject();
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + var0.getClass().getName());
        }
    }

    protected ASN1Sequence() {
    }

    protected ASN1Sequence(ASN1Encodable var1) {
        this.seq.addElement(var1);
    }

    protected ASN1Sequence(ASN1EncodableVector var1) {
        for(int var2 = 0; var2 != var1.size(); ++var2) {
            this.seq.addElement(var1.get(var2));
        }

    }

    protected ASN1Sequence(ASN1Encodable[] var1) {
        for(int var2 = 0; var2 != var1.length; ++var2) {
            this.seq.addElement(var1[var2]);
        }

    }

    public ASN1Encodable[] toArray() {
        ASN1Encodable[] var1 = new ASN1Encodable[this.size()];

        for(int var2 = 0; var2 != this.size(); ++var2) {
            var1[var2] = this.getObjectAt(var2);
        }

        return var1;
    }

    public Enumeration getObjects() {
        return this.seq.elements();
    }

    public ASN1SequenceParser parser() {
        return new ASN1SequenceParser() {
            private final int max = ASN1Sequence.this.size();
            private int index;

            public ASN1Encodable readObject() throws IOException {
                if(this.index == this.max) {
                    return null;
                } else {
                    ASN1Encodable var1 = ASN1Sequence.this.getObjectAt(this.index++);
                    return (ASN1Encodable)(var1 instanceof ASN1Sequence?((ASN1Sequence)var1).parser():(var1 instanceof ASN1Set?((ASN1Set)var1).parser():var1));
                }
            }

            public ASN1Primitive getLoadedObject() {
                return ASN1Sequence.this;
            }

            public ASN1Primitive toASN1Primitive() {
                return ASN1Sequence.this;
            }
        };
    }

    public ASN1Encodable getObjectAt(int var1) {
        return (ASN1Encodable)this.seq.elementAt(var1);
    }

    public int size() {
        return this.seq.size();
    }

    public int hashCode() {
        Enumeration var1 = this.getObjects();

        int var2;
        ASN1Encodable var3;
        for(var2 = this.size(); var1.hasMoreElements(); var2 ^= var3.hashCode()) {
            var3 = this.getNext(var1);
            var2 *= 17;
        }

        return var2;
    }

    boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof ASN1Sequence)) {
            return false;
        } else {
            ASN1Sequence var2 = (ASN1Sequence)var1;
            if(this.size() != var2.size()) {
                return false;
            } else {
                Enumeration var3 = this.getObjects();
                Enumeration var4 = var2.getObjects();

                ASN1Primitive var7;
                ASN1Primitive var8;
                do {
                    if(!var3.hasMoreElements()) {
                        return true;
                    }

                    ASN1Encodable var5 = this.getNext(var3);
                    ASN1Encodable var6 = this.getNext(var4);
                    var7 = var5.toASN1Primitive();
                    var8 = var6.toASN1Primitive();
                } while(var7 == var8 || var7.equals(var8));

                return false;
            }
        }
    }

    private ASN1Encodable getNext(Enumeration var1) {
        ASN1Encodable var2 = (ASN1Encodable)var1.nextElement();
        return var2;
    }

    ASN1Primitive toDERObject() {
        DERSequence var1 = new DERSequence();
        var1.seq = this.seq;
        return var1;
    }

    ASN1Primitive toDLObject() {
        DLSequence var1 = new DLSequence();
        var1.seq = this.seq;
        return var1;
    }

    boolean isConstructed() {
        return true;
    }

    abstract void encode(ASN1OutputStream var1) throws IOException;

    public String toString() {
        return this.seq.toString();
    }

    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator(this.toArray());
    }
}


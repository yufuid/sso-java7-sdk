package com.yufu.idaas.sdk.bouncycastle.asn1;




import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 2017/1/17.
 */
public class ASN1StreamParser {
    private final InputStream _in;
    private final int _limit;
    private final byte[][] tmpBuffers;

    public ASN1StreamParser(InputStream var1) {
        this(var1, StreamUtil.findLimit(var1));
    }

    public ASN1StreamParser(InputStream var1, int var2) {
        this._in = var1;
        this._limit = var2;
        this.tmpBuffers = new byte[11][];
    }

    public ASN1StreamParser(byte[] var1) {
        this(new ByteArrayInputStream(var1), var1.length);
    }

    ASN1Encodable readIndef(int var1) throws IOException {
        switch(var1) {
            case 4:
                return new BEROctetStringParser(this);
            case 8:
                return new DERExternalParser(this);
            case 16:
                return new BERSequenceParser(this);
            case 17:
                return new BERSetParser(this);
            default:
                throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(var1));
        }
    }

    ASN1Encodable readImplicit(boolean var1, int var2) throws IOException {
        if(this._in instanceof IndefiniteLengthInputStream) {
            if(!var1) {
                throw new IOException("indefinite-length primitive encoding encountered");
            } else {
                return this.readIndef(var2);
            }
        } else {
            if(var1) {
                switch(var2) {
                    case 4:
                        return new BEROctetStringParser(this);
                    case 16:
                        return new DERSequenceParser(this);
                    case 17:
                        return new DERSetParser(this);
                }
            } else {
                switch(var2) {
                    case 4:
                        return new DEROctetStringParser((DefiniteLengthInputStream)this._in);
                    case 16:
                        throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
                    case 17:
                        throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
                }
            }

            throw new ASN1Exception("implicit tagging not implemented");
        }
    }

    ASN1Primitive readTaggedObject(boolean var1, int var2) throws IOException {
        if(!var1) {
            DefiniteLengthInputStream var4 = (DefiniteLengthInputStream)this._in;
            return new DERTaggedObject(false, var2, new DEROctetString(var4.toByteArray()));
        } else {
            ASN1EncodableVector var3 = this.readVector();
            return (ASN1Primitive)(this._in instanceof IndefiniteLengthInputStream?(var3.size() == 1?new BERTaggedObject(true, var2, var3.get(0)):new BERTaggedObject(false, var2, BERFactory.createSequence(var3))):(var3.size() == 1?new DERTaggedObject(true, var2, var3.get(0)):new DERTaggedObject(false, var2, DERFactory.createSequence(var3))));
        }
    }

    public ASN1Encodable readObject() throws IOException {
        int var1 = this._in.read();
        if(var1 == -1) {
            return null;
        } else {
            this.set00Check(false);
            int var2 = ASN1InputStream.readTagNumber(this._in, var1);
            boolean var3 = (var1 & 32) != 0;
            int var4 = ASN1InputStream.readLength(this._in, this._limit);
            if(var4 < 0) {
                if(!var3) {
                    throw new IOException("indefinite-length primitive encoding encountered");
                } else {
                    IndefiniteLengthInputStream var8 = new IndefiniteLengthInputStream(this._in, this._limit);
                    ASN1StreamParser var6 = new ASN1StreamParser(var8, this._limit);
                    return (ASN1Encodable)((var1 & 64) != 0?new BERApplicationSpecificParser(var2, var6):((var1 & 128) != 0?new BERTaggedObjectParser(true, var2, var6):var6.readIndef(var2)));
                }
            } else {
                DefiniteLengthInputStream var5 = new DefiniteLengthInputStream(this._in, var4);
                if((var1 & 64) != 0) {
                    return new DERApplicationSpecific(var3, var2, var5.toByteArray());
                } else if((var1 & 128) != 0) {
                    return new BERTaggedObjectParser(var3, var2, new ASN1StreamParser(var5));
                } else if(var3) {
                    switch(var2) {
                        case 4:
                            return new BEROctetStringParser(new ASN1StreamParser(var5));
                        case 8:
                            return new DERExternalParser(new ASN1StreamParser(var5));
                        case 16:
                            return new DERSequenceParser(new ASN1StreamParser(var5));
                        case 17:
                            return new DERSetParser(new ASN1StreamParser(var5));
                        default:
                            throw new IOException("unknown tag " + var2 + " encountered");
                    }
                } else {
                    switch(var2) {
                        case 4:
                            return new DEROctetStringParser(var5);
                        default:
                            try {
                                return ASN1InputStream.createPrimitiveDERObject(var2, var5, this.tmpBuffers);
                            } catch (IllegalArgumentException var7) {
                                throw new ASN1Exception("corrupted stream detected", var7);
                            }
                    }
                }
            }
        }
    }

    private void set00Check(boolean var1) {
        if(this._in instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream)this._in).setEofOn00(var1);
        }

    }

    ASN1EncodableVector readVector() throws IOException {
        ASN1EncodableVector var1 = new ASN1EncodableVector();

        ASN1Encodable var2;
        while((var2 = this.readObject()) != null) {
            if(var2 instanceof InMemoryRepresentable) {
                var1.add(((InMemoryRepresentable)var2).getLoadedObject());
            } else {
                var1.add(var2.toASN1Primitive());
            }
        }

        return var1;
    }
}

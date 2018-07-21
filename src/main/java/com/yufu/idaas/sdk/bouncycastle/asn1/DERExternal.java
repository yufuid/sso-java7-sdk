package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERExternal extends ASN1Primitive {
    private ASN1ObjectIdentifier directReference;
    private ASN1Integer indirectReference;
    private ASN1Primitive dataValueDescriptor;
    private int encoding;
    private ASN1Primitive externalContent;

    public DERExternal(ASN1EncodableVector var1) {
        int var2 = 0;
        ASN1Primitive var3 = this.getObjFromVector(var1, var2);
        if(var3 instanceof ASN1ObjectIdentifier) {
            this.directReference = (ASN1ObjectIdentifier)var3;
            ++var2;
            var3 = this.getObjFromVector(var1, var2);
        }

        if(var3 instanceof ASN1Integer) {
            this.indirectReference = (ASN1Integer)var3;
            ++var2;
            var3 = this.getObjFromVector(var1, var2);
        }

        if(!(var3 instanceof ASN1TaggedObject)) {
            this.dataValueDescriptor = var3;
            ++var2;
            var3 = this.getObjFromVector(var1, var2);
        }

        if(var1.size() != var2 + 1) {
            throw new IllegalArgumentException("input vector too large");
        } else if(!(var3 instanceof ASN1TaggedObject)) {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn\'t seem to be of type External");
        } else {
            ASN1TaggedObject var4 = (ASN1TaggedObject)var3;
            this.setEncoding(var4.getTagNo());
            this.externalContent = var4.getObject();
        }
    }

    private ASN1Primitive getObjFromVector(ASN1EncodableVector var1, int var2) {
        if(var1.size() <= var2) {
            throw new IllegalArgumentException("too few objects in input vector");
        } else {
            return var1.get(var2).toASN1Primitive();
        }
    }

    public DERExternal(ASN1ObjectIdentifier var1, ASN1Integer var2, ASN1Primitive var3, DERTaggedObject var4) {
        this(var1, var2, var3, var4.getTagNo(), var4.toASN1Primitive());
    }

    public DERExternal(ASN1ObjectIdentifier var1, ASN1Integer var2, ASN1Primitive var3, int var4, ASN1Primitive var5) {
        this.setDirectReference(var1);
        this.setIndirectReference(var2);
        this.setDataValueDescriptor(var3);
        this.setEncoding(var4);
        this.setExternalContent(var5.toASN1Primitive());
    }

    public int hashCode() {
        int var1 = 0;
        if(this.directReference != null) {
            var1 = this.directReference.hashCode();
        }

        if(this.indirectReference != null) {
            var1 ^= this.indirectReference.hashCode();
        }

        if(this.dataValueDescriptor != null) {
            var1 ^= this.dataValueDescriptor.hashCode();
        }

        var1 ^= this.externalContent.hashCode();
        return var1;
    }

    boolean isConstructed() {
        return true;
    }

    int encodedLength() throws IOException {
        return this.getEncoded().length;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        if(this.directReference != null) {
            var2.write(this.directReference.getEncoded("DER"));
        }

        if(this.indirectReference != null) {
            var2.write(this.indirectReference.getEncoded("DER"));
        }

        if(this.dataValueDescriptor != null) {
            var2.write(this.dataValueDescriptor.getEncoded("DER"));
        }

        DERTaggedObject var3 = new DERTaggedObject(true, this.encoding, this.externalContent);
        var2.write(var3.getEncoded("DER"));
        var1.writeEncoded(32, 8, var2.toByteArray());
    }

    boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof DERExternal)) {
            return false;
        } else if(this == var1) {
            return true;
        } else {
            DERExternal var2 = (DERExternal)var1;
            return this.directReference != null && (var2.directReference == null || !var2.directReference.equals(this.directReference))?false:(this.indirectReference == null || var2.indirectReference != null && var2.indirectReference.equals(this.indirectReference)?(this.dataValueDescriptor == null || var2.dataValueDescriptor != null && var2.dataValueDescriptor.equals(this.dataValueDescriptor)?this.externalContent.equals(var2.externalContent):false):false);
        }
    }

    public ASN1Primitive getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public ASN1ObjectIdentifier getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public ASN1Primitive getExternalContent() {
        return this.externalContent;
    }

    public ASN1Integer getIndirectReference() {
        return this.indirectReference;
    }

    private void setDataValueDescriptor(ASN1Primitive var1) {
        this.dataValueDescriptor = var1;
    }

    private void setDirectReference(ASN1ObjectIdentifier var1) {
        this.directReference = var1;
    }

    private void setEncoding(int var1) {
        if(var1 >= 0 && var1 <= 2) {
            this.encoding = var1;
        } else {
            throw new IllegalArgumentException("invalid encoding value: " + var1);
        }
    }

    private void setExternalContent(ASN1Primitive var1) {
        this.externalContent = var1;
    }

    private void setIndirectReference(ASN1Integer var1) {
        this.indirectReference = var1;
    }
}

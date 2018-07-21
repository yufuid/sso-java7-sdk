package com.yufu.idaas.sdk.bouncycastle.asn1.x509;

import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1Encodable;
import com.yufu.idaas.sdk.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.yufu.idaas.sdk.bouncycastle.asn1.DEROctetString;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by mac on 2017/1/18.
 */
public class ExtensionsGenerator {
    private Hashtable extensions = new Hashtable();
    private Vector extOrdering = new Vector();

    public ExtensionsGenerator() {
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }

    public void addExtension(ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws IOException {
        this.addExtension(var1, var2, var3.toASN1Primitive().getEncoded("DER"));
    }

    public void addExtension(ASN1ObjectIdentifier var1, boolean var2, byte[] var3) {
        if(this.extensions.containsKey(var1)) {
            throw new IllegalArgumentException("extension " + var1 + " already added");
        } else {
            this.extOrdering.addElement(var1);
            this.extensions.put(var1, new Extension(var1, var2, new DEROctetString(var3)));
        }
    }

    public void addExtension(Extension var1) {
        if(this.extensions.containsKey(var1.getExtnId())) {
            throw new IllegalArgumentException("extension " + var1.getExtnId() + " already added");
        } else {
            this.extOrdering.addElement(var1.getExtnId());
            this.extensions.put(var1.getExtnId(), var1);
        }
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public Extensions generate() {
        Extension[] var1 = new Extension[this.extOrdering.size()];

        for(int var2 = 0; var2 != this.extOrdering.size(); ++var2) {
            var1[var2] = (Extension)this.extensions.get(this.extOrdering.elementAt(var2));
        }

        return new Extensions(var1);
    }
}

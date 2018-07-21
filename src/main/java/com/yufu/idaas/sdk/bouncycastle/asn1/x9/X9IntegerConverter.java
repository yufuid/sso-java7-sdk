package com.yufu.idaas.sdk.bouncycastle.asn1.x9;

import com.yufu.idaas.sdk.bouncycastle.math.ec.ECCurve;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECFieldElement;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
public class X9IntegerConverter {
    public X9IntegerConverter() {
    }

    public int getByteLength(ECCurve var1) {
        return (var1.getFieldSize() + 7) / 8;
    }

    public int getByteLength(ECFieldElement var1) {
        return (var1.getFieldSize() + 7) / 8;
    }

    public byte[] integerToBytes(BigInteger var1, int var2) {
        byte[] var3 = var1.toByteArray();
        byte[] var4;
        if(var2 < var3.length) {
            var4 = new byte[var2];
            System.arraycopy(var3, var3.length - var4.length, var4, 0, var4.length);
            return var4;
        } else if(var2 > var3.length) {
            var4 = new byte[var2];
            System.arraycopy(var3, 0, var4, var4.length - var3.length, var3.length);
            return var4;
        } else {
            return var3;
        }
    }
}
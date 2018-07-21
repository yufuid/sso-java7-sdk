package com.yufu.idaas.sdk.bouncycastle.math.field;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
class PrimeField implements FiniteField {
    protected final BigInteger characteristic;

    PrimeField(BigInteger var1) {
        this.characteristic = var1;
    }

    public BigInteger getCharacteristic() {
        return this.characteristic;
    }

    public int getDimension() {
        return 1;
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof PrimeField)) {
            return false;
        } else {
            PrimeField var2 = (PrimeField)var1;
            return this.characteristic.equals(var2.characteristic);
        }
    }

    public int hashCode() {
        return this.characteristic.hashCode();
    }
}
package com.yufu.idaas.sdk.bouncycastle.math.field;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;

/**
 * Created by mac on 2017/1/18.
 */

class GF2Polynomial implements Polynomial {
    protected final int[] exponents;

    GF2Polynomial(int[] var1) {
        this.exponents = Arrays.clone(var1);
    }

    public int getDegree() {
        return this.exponents[this.exponents.length - 1];
    }

    public int[] getExponentsPresent() {
        return Arrays.clone(this.exponents);
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof GF2Polynomial)) {
            return false;
        } else {
            GF2Polynomial var2 = (GF2Polynomial)var1;
            return Arrays.areEqual(this.exponents, var2.exponents);
        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.exponents);
    }
}

package com.yufu.idaas.sdk.bouncycastle.math.field;

import com.yufu.idaas.sdk.bouncycastle.util.Integers;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
class GenericPolynomialExtensionField implements PolynomialExtensionField {
    protected final FiniteField subfield;
    protected final Polynomial minimalPolynomial;

    GenericPolynomialExtensionField(FiniteField var1, Polynomial var2) {
        this.subfield = var1;
        this.minimalPolynomial = var2;
    }

    public BigInteger getCharacteristic() {
        return this.subfield.getCharacteristic();
    }

    public int getDimension() {
        return this.subfield.getDimension() * this.minimalPolynomial.getDegree();
    }

    public FiniteField getSubfield() {
        return this.subfield;
    }

    public int getDegree() {
        return this.minimalPolynomial.getDegree();
    }

    public Polynomial getMinimalPolynomial() {
        return this.minimalPolynomial;
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof GenericPolynomialExtensionField)) {
            return false;
        } else {
            GenericPolynomialExtensionField var2 = (GenericPolynomialExtensionField)var1;
            return this.subfield.equals(var2.subfield) && this.minimalPolynomial.equals(var2.minimalPolynomial);
        }
    }

    public int hashCode() {
        return this.subfield.hashCode() ^ Integers.rotateLeft(this.minimalPolynomial.hashCode(), 16);
    }
}

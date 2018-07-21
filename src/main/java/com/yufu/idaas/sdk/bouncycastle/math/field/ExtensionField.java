package com.yufu.idaas.sdk.bouncycastle.math.field;

/**
 * Created by mac on 2017/1/18.
 */
public interface ExtensionField extends FiniteField {
    FiniteField getSubfield();

    int getDegree();
}

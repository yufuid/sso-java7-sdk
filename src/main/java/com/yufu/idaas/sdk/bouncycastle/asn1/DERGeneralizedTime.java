package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.util.Date;

/**
 * Created by mac on 2017/1/18.
 */

public class DERGeneralizedTime extends ASN1GeneralizedTime {
    DERGeneralizedTime(byte[] var1) {
        super(var1);
    }

    public DERGeneralizedTime(Date var1) {
        super(var1);
    }

    public DERGeneralizedTime(String var1) {
        super(var1);
    }
}

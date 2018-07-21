package com.yufu.idaas.sdk.bouncycastle.math.ec.endo;

import com.yufu.idaas.sdk.bouncycastle.math.ec.ECPointMap;

/**
 * Created by mac on 2017/1/18.
 */
public interface ECEndomorphism {
    ECPointMap getPointMap();

    boolean hasEfficientPointMap();
}

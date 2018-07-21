package com.yufu.idaas.sdk.bouncycastle.math.ec;

import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECEndomorphism;
import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;
import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.GLVEndomorphism;
import com.yufu.idaas.sdk.bouncycastle.math.field.FiniteField;
import com.yufu.idaas.sdk.bouncycastle.math.field.FiniteFields;
import com.yufu.idaas.sdk.bouncycastle.util.BigIntegers;
import com.yufu.idaas.sdk.bouncycastle.util.Integers;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;

public abstract class ECCurve {
    public static final int COORD_AFFINE = 0;
    public static final int COORD_HOMOGENEOUS = 1;
    public static final int COORD_JACOBIAN = 2;
    public static final int COORD_JACOBIAN_CHUDNOVSKY = 3;
    public static final int COORD_JACOBIAN_MODIFIED = 4;
    public static final int COORD_LAMBDA_AFFINE = 5;
    public static final int COORD_LAMBDA_PROJECTIVE = 6;
    public static final int COORD_SKEWED = 7;
    protected FiniteField field;
    protected ECFieldElement a;
    protected ECFieldElement b;
    protected BigInteger order;
    protected BigInteger cofactor;
    protected int coord = 0;
    protected ECEndomorphism endomorphism = null;
    protected ECMultiplier multiplier = null;

    public static int[] getAllCoordinateSystems() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    protected ECCurve(FiniteField var1) {
        this.field = var1;
    }

    public abstract int getFieldSize();

    public abstract ECFieldElement fromBigInteger(BigInteger var1);

    public abstract boolean isValidFieldElement(BigInteger var1);

    public synchronized Config configure() {
        return new Config(this.coord, this.endomorphism, this.multiplier);
    }

    public ECPoint validatePoint(BigInteger var1, BigInteger var2) {
        ECPoint var3 = this.createPoint(var1, var2);
        if(!var3.isValid()) {
            throw new IllegalArgumentException("Invalid point coordinates");
        } else {
            return var3;
        }
    }

    /** @deprecated */
    public ECPoint validatePoint(BigInteger var1, BigInteger var2, boolean var3) {
        ECPoint var4 = this.createPoint(var1, var2, var3);
        if(!var4.isValid()) {
            throw new IllegalArgumentException("Invalid point coordinates");
        } else {
            return var4;
        }
    }

    public ECPoint createPoint(BigInteger var1, BigInteger var2) {
        return this.createPoint(var1, var2, false);
    }

    /** @deprecated */
    public ECPoint createPoint(BigInteger var1, BigInteger var2, boolean var3) {
        return this.createRawPoint(this.fromBigInteger(var1), this.fromBigInteger(var2), var3);
    }

    public abstract ECCurve cloneCurve();

    public abstract ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3);

    public abstract ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4);

    public ECMultiplier createDefaultMultiplier() {
        return (ECMultiplier)(this.endomorphism instanceof GLVEndomorphism ?new GLVMultiplier(this, (GLVEndomorphism)this.endomorphism):new WNafL2RMultiplier());
    }

    public boolean supportsCoordinateSystem(int var1) {
        return var1 == 0;
    }

    public PreCompInfo getPreCompInfo(ECPoint var1, String var2) {
        this.checkPoint(var1);
        synchronized(var1) {
            Hashtable var4 = var1.preCompTable;
            return var4 == null?null:(PreCompInfo)var4.get(var2);
        }
    }

    public void setPreCompInfo(ECPoint var1, String var2, PreCompInfo var3) {
        this.checkPoint(var1);
        synchronized(var1) {
            Hashtable var5 = var1.preCompTable;
            if(null == var5) {
                var1.preCompTable = var5 = new Hashtable(4);
            }

            var5.put(var2, var3);
        }
    }

    public ECPoint importPoint(ECPoint var1) {
        if(this == var1.getCurve()) {
            return var1;
        } else if(var1.isInfinity()) {
            return this.getInfinity();
        } else {
            var1 = var1.normalize();
            return this.validatePoint(var1.getXCoord().toBigInteger(), var1.getYCoord().toBigInteger(), var1.withCompression);
        }
    }

    public void normalizeAll(ECPoint[] var1) {
        this.normalizeAll(var1, 0, var1.length, (ECFieldElement)null);
    }

    public void normalizeAll(ECPoint[] var1, int var2, int var3, ECFieldElement var4) {
        this.checkPoints(var1, var2, var3);
        switch(this.getCoordinateSystem()) {
            case 0:
            case 5:
                if(var4 != null) {
                    throw new IllegalArgumentException("\'iso\' not valid for affine coordinates");
                }

                return;
            default:
                ECFieldElement[] var5 = new ECFieldElement[var3];
                int[] var6 = new int[var3];
                int var7 = 0;
                int var8 = 0;

                for(; var8 < var3; ++var8) {
                    ECPoint var9 = var1[var2 + var8];
                    if(null != var9 && (var4 != null || !var9.isNormalized())) {
                        var5[var7] = var9.getZCoord(0);
                        var6[var7++] = var2 + var8;
                    }
                }

                if(var7 != 0) {
                    ECAlgorithms.montgomeryTrick(var5, 0, var7, var4);

                    for(var8 = 0; var8 < var7; ++var8) {
                        int var10 = var6[var8];
                        var1[var10] = var1[var10].normalize(var5[var8]);
                    }

                }
        }
    }

    public abstract ECPoint getInfinity();

    public FiniteField getField() {
        return this.field;
    }

    public ECFieldElement getA() {
        return this.a;
    }

    public ECFieldElement getB() {
        return this.b;
    }

    public BigInteger getOrder() {
        return this.order;
    }

    public BigInteger getCofactor() {
        return this.cofactor;
    }

    public int getCoordinateSystem() {
        return this.coord;
    }

    protected abstract ECPoint decompressPoint(int var1, BigInteger var2);

    public ECEndomorphism getEndomorphism() {
        return this.endomorphism;
    }

    public synchronized ECMultiplier getMultiplier() {
        if(this.multiplier == null) {
            this.multiplier = this.createDefaultMultiplier();
        }

        return this.multiplier;
    }

    public ECPoint decodePoint(byte[] var1) {
        ECPoint var2 = null;
        int var3 = (this.getFieldSize() + 7) / 8;
        byte var4 = var1[0];
        BigInteger var5;
        BigInteger var6;
        switch(var4) {
            case 0:
                if(var1.length != 1) {
                    throw new IllegalArgumentException("Incorrect length for infinity encoding");
                }

                var2 = this.getInfinity();
                break;
            case 1:
            case 5:
            default:
                throw new IllegalArgumentException("Invalid point encoding 0x" + Integer.toString(var4, 16));
            case 2:
            case 3:
                if(var1.length != var3 + 1) {
                    throw new IllegalArgumentException("Incorrect length for compressed encoding");
                }

                int var7 = var4 & 1;
                var6 = BigIntegers.fromUnsignedByteArray(var1, 1, var3);
                var2 = this.decompressPoint(var7, var6);
                if(!var2.satisfiesCofactor()) {
                    throw new IllegalArgumentException("Invalid point");
                }
                break;
            case 4:
                if(var1.length != 2 * var3 + 1) {
                    throw new IllegalArgumentException("Incorrect length for uncompressed encoding");
                }

                var5 = BigIntegers.fromUnsignedByteArray(var1, 1, var3);
                var6 = BigIntegers.fromUnsignedByteArray(var1, 1 + var3, var3);
                var2 = this.validatePoint(var5, var6);
                break;
            case 6:
            case 7:
                if(var1.length != 2 * var3 + 1) {
                    throw new IllegalArgumentException("Incorrect length for hybrid encoding");
                }

                var5 = BigIntegers.fromUnsignedByteArray(var1, 1, var3);
                var6 = BigIntegers.fromUnsignedByteArray(var1, 1 + var3, var3);
                if(var6.testBit(0) != (var4 == 7)) {
                    throw new IllegalArgumentException("Inconsistent Y coordinate in hybrid encoding");
                }

                var2 = this.validatePoint(var5, var6);
        }

        if(var4 != 0 && var2.isInfinity()) {
            throw new IllegalArgumentException("Invalid infinity encoding");
        } else {
            return var2;
        }
    }

    protected void checkPoint(ECPoint var1) {
        if(null == var1 || this != var1.getCurve()) {
            throw new IllegalArgumentException("\'point\' must be non-null and on this curve");
        }
    }

    protected void checkPoints(ECPoint[] var1) {
        this.checkPoints(var1, 0, var1.length);
    }

    protected void checkPoints(ECPoint[] var1, int var2, int var3) {
        if(var1 == null) {
            throw new IllegalArgumentException("\'points\' cannot be null");
        } else if(var2 >= 0 && var3 >= 0 && var2 <= var1.length - var3) {
            for(int var4 = 0; var4 < var3; ++var4) {
                ECPoint var5 = var1[var2 + var4];
                if(null != var5 && this != var5.getCurve()) {
                    throw new IllegalArgumentException("\'points\' entries must be null or on this curve");
                }
            }

        } else {
            throw new IllegalArgumentException("invalid range specified for \'points\'");
        }
    }

    public boolean equals(ECCurve var1) {
        return this == var1 || null != var1 && this.getField().equals(var1.getField()) && this.getA().toBigInteger().equals(var1.getA().toBigInteger()) && this.getB().toBigInteger().equals(var1.getB().toBigInteger());
    }

    public boolean equals(Object var1) {
        return this == var1 || var1 instanceof ECCurve && this.equals((ECCurve)var1);
    }

    public int hashCode() {
        return this.getField().hashCode() ^ Integers.rotateLeft(this.getA().toBigInteger().hashCode(), 8) ^ Integers.rotateLeft(this.getB().toBigInteger().hashCode(), 16);
    }

    public abstract static class AbstractF2m extends ECCurve {
        private BigInteger[] si = null;

        public static BigInteger inverse(int var0, int[] var1, BigInteger var2) {
            return (new LongArray(var2)).modInverse(var0, var1).toBigInteger();
        }

        private static FiniteField buildField(int var0, int var1, int var2, int var3) {
            if(var1 == 0) {
                throw new IllegalArgumentException("k1 must be > 0");
            } else if(var2 == 0) {
                if(var3 != 0) {
                    throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
                } else {
                    return FiniteFields.getBinaryExtensionField(new int[]{0, var1, var0});
                }
            } else if(var2 <= var1) {
                throw new IllegalArgumentException("k2 must be > k1");
            } else if(var3 <= var2) {
                throw new IllegalArgumentException("k3 must be > k2");
            } else {
                return FiniteFields.getBinaryExtensionField(new int[]{0, var1, var2, var3, var0});
            }
        }

        protected AbstractF2m(int var1, int var2, int var3, int var4) {
            super(buildField(var1, var2, var3, var4));
        }

        public boolean isValidFieldElement(BigInteger var1) {
            return var1 != null && var1.signum() >= 0 && var1.bitLength() <= this.getFieldSize();
        }

        public ECPoint createPoint(BigInteger var1, BigInteger var2, boolean var3) {
            ECFieldElement var4 = this.fromBigInteger(var1);
            ECFieldElement var5 = this.fromBigInteger(var2);
            int var6 = this.getCoordinateSystem();
            switch(var6) {
                case 5:
                case 6:
                    if(var4.isZero()) {
                        if(!var5.square().equals(this.getB())) {
                            throw new IllegalArgumentException();
                        }
                    } else {
                        var5 = var5.divide(var4).add(var4);
                    }
                default:
                    return this.createRawPoint(var4, var5, var3);
            }
        }

        protected ECPoint decompressPoint(int var1, BigInteger var2) {
            ECFieldElement var3 = this.fromBigInteger(var2);
            ECFieldElement var4 = null;
            if(var3.isZero()) {
                var4 = this.getB().sqrt();
            } else {
                ECFieldElement var5 = var3.square().invert().multiply(this.getB()).add(this.getA()).add(var3);
                ECFieldElement var6 = this.solveQuadraticEquation(var5);
                if(var6 != null) {
                    if(var6.testBitZero() != (var1 == 1)) {
                        var6 = var6.addOne();
                    }

                    switch(this.getCoordinateSystem()) {
                        case 5:
                        case 6:
                            var4 = var6.add(var3);
                            break;
                        default:
                            var4 = var6.multiply(var3);
                    }
                }
            }

            if(var4 == null) {
                throw new IllegalArgumentException("Invalid point compression");
            } else {
                return this.createRawPoint(var3, var4, true);
            }
        }

        private ECFieldElement solveQuadraticEquation(ECFieldElement var1) {
            if(var1.isZero()) {
                return var1;
            } else {
                ECFieldElement var4 = this.fromBigInteger(ECConstants.ZERO);
                int var5 = this.getFieldSize();
                Random var6 = new Random();

                ECFieldElement var2;
                ECFieldElement var3;
                do {
                    ECFieldElement var7 = this.fromBigInteger(new BigInteger(var5, var6));
                    var3 = var4;
                    ECFieldElement var8 = var1;

                    for(int var9 = 1; var9 < var5; ++var9) {
                        ECFieldElement var10 = var8.square();
                        var3 = var3.square().add(var10.multiply(var7));
                        var8 = var10.add(var1);
                    }

                    if(!var8.isZero()) {
                        return null;
                    }

                    var2 = var3.square().add(var3);
                } while(var2.isZero());

                return var3;
            }
        }

        synchronized BigInteger[] getSi() {
            if(this.si == null) {
                this.si = Tnaf.getSi(this);
            }

            return this.si;
        }

        public boolean isKoblitz() {
            return this.order != null && this.cofactor != null && this.b.isOne() && (this.a.isZero() || this.a.isOne());
        }
    }

    public abstract static class AbstractFp extends ECCurve {
        protected AbstractFp(BigInteger var1) {
            super(FiniteFields.getPrimeField(var1));
        }

        public boolean isValidFieldElement(BigInteger var1) {
            return var1 != null && var1.signum() >= 0 && var1.compareTo(this.getField().getCharacteristic()) < 0;
        }

        protected ECPoint decompressPoint(int var1, BigInteger var2) {
            ECFieldElement var3 = this.fromBigInteger(var2);
            ECFieldElement var4 = var3.square().add(this.a).multiply(var3).add(this.b);
            ECFieldElement var5 = var4.sqrt();
            if(var5 == null) {
                throw new IllegalArgumentException("Invalid point compression");
            } else {
                if(var5.testBitZero() != (var1 == 1)) {
                    var5 = var5.negate();
                }

                return this.createRawPoint(var3, var5, true);
            }
        }
    }

    public class Config {
        protected int coord;
        protected ECEndomorphism endomorphism;
        protected ECMultiplier multiplier;

        Config(int var2, ECEndomorphism var3, ECMultiplier var4) {
            this.coord = var2;
            this.endomorphism = var3;
            this.multiplier = var4;
        }

        public Config setCoordinateSystem(int var1) {
            this.coord = var1;
            return this;
        }

        public Config setEndomorphism(ECEndomorphism var1) {
            this.endomorphism = var1;
            return this;
        }

        public Config setMultiplier(ECMultiplier var1) {
            this.multiplier = var1;
            return this;
        }

        public ECCurve create() {
            if(!ECCurve.this.supportsCoordinateSystem(this.coord)) {
                throw new IllegalStateException("unsupported coordinate system");
            } else {
                ECCurve var1 = ECCurve.this.cloneCurve();
                if(var1 == ECCurve.this) {
                    throw new IllegalStateException("implementation returned current curve");
                } else {
                    synchronized(var1) {
                        var1.coord = this.coord;
                        var1.endomorphism = this.endomorphism;
                        var1.multiplier = this.multiplier;
                        return var1;
                    }
                }
            }
        }
    }

    public static class F2m extends AbstractF2m {
        private static final int F2M_DEFAULT_COORDS = 6;
        private int m;
        private int k1;
        private int k2;
        private int k3;
        private ECPoint.F2m infinity;

        public F2m(int var1, int var2, BigInteger var3, BigInteger var4) {
            this(var1, var2, 0, 0, (BigInteger)var3, (BigInteger)var4, (BigInteger)null, (BigInteger)null);
        }

        public F2m(int var1, int var2, BigInteger var3, BigInteger var4, BigInteger var5, BigInteger var6) {
            this(var1, var2, 0, 0, (BigInteger)var3, (BigInteger)var4, var5, var6);
        }

        public F2m(int var1, int var2, int var3, int var4, BigInteger var5, BigInteger var6) {
            this(var1, var2, var3, var4, (BigInteger)var5, (BigInteger)var6, (BigInteger)null, (BigInteger)null);
        }

        public F2m(int var1, int var2, int var3, int var4, BigInteger var5, BigInteger var6, BigInteger var7, BigInteger var8) {
            super(var1, var2, var3, var4);
            this.m = var1;
            this.k1 = var2;
            this.k2 = var3;
            this.k3 = var4;
            this.order = var7;
            this.cofactor = var8;
            this.infinity = new ECPoint.F2m(this, (ECFieldElement)null, (ECFieldElement)null);
            this.a = this.fromBigInteger(var5);
            this.b = this.fromBigInteger(var6);
            this.coord = 6;
        }

        protected F2m(int var1, int var2, int var3, int var4, ECFieldElement var5, ECFieldElement var6, BigInteger var7, BigInteger var8) {
            super(var1, var2, var3, var4);
            this.m = var1;
            this.k1 = var2;
            this.k2 = var3;
            this.k3 = var4;
            this.order = var7;
            this.cofactor = var8;
            this.infinity = new ECPoint.F2m(this, (ECFieldElement)null, (ECFieldElement)null);
            this.a = var5;
            this.b = var6;
            this.coord = 6;
        }

        public ECCurve cloneCurve() {
            return new F2m(this.m, this.k1, this.k2, this.k3, this.a, this.b, this.order, this.cofactor);
        }

        public boolean supportsCoordinateSystem(int var1) {
            switch(var1) {
                case 0:
                case 1:
                case 6:
                    return true;
                default:
                    return false;
            }
        }

        public ECMultiplier createDefaultMultiplier() {
            return (ECMultiplier)(this.isKoblitz()?new WTauNafMultiplier():super.createDefaultMultiplier());
        }

        public int getFieldSize() {
            return this.m;
        }

        public ECFieldElement fromBigInteger(BigInteger var1) {
            return new ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, var1);
        }

        public ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
            return new ECPoint.F2m(this, var1, var2, var3);
        }

        public ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
            return new ECPoint.F2m(this, var1, var2, var3, var4);
        }

        public ECPoint getInfinity() {
            return this.infinity;
        }

        public int getM() {
            return this.m;
        }

        public boolean isTrinomial() {
            return this.k2 == 0 && this.k3 == 0;
        }

        public int getK1() {
            return this.k1;
        }

        public int getK2() {
            return this.k2;
        }

        public int getK3() {
            return this.k3;
        }

        /** @deprecated */
        public BigInteger getN() {
            return this.order;
        }

        /** @deprecated */
        public BigInteger getH() {
            return this.cofactor;
        }
    }

    public static class Fp extends AbstractFp {
        private static final int FP_DEFAULT_COORDS = 4;
        BigInteger q;
        BigInteger r;
        ECPoint.Fp infinity;

        public Fp(BigInteger var1, BigInteger var2, BigInteger var3) {
            this(var1, var2, var3, (BigInteger)null, (BigInteger)null);
        }

        public Fp(BigInteger var1, BigInteger var2, BigInteger var3, BigInteger var4, BigInteger var5) {
            super(var1);
            this.q = var1;
            this.r = ECFieldElement.Fp.calculateResidue(var1);
            this.infinity = new ECPoint.Fp(this, (ECFieldElement)null, (ECFieldElement)null);
            this.a = this.fromBigInteger(var2);
            this.b = this.fromBigInteger(var3);
            this.order = var4;
            this.cofactor = var5;
            this.coord = 4;
        }

        protected Fp(BigInteger var1, BigInteger var2, ECFieldElement var3, ECFieldElement var4) {
            this(var1, var2, var3, var4, (BigInteger)null, (BigInteger)null);
        }

        protected Fp(BigInteger var1, BigInteger var2, ECFieldElement var3, ECFieldElement var4, BigInteger var5, BigInteger var6) {
            super(var1);
            this.q = var1;
            this.r = var2;
            this.infinity = new ECPoint.Fp(this, (ECFieldElement)null, (ECFieldElement)null);
            this.a = var3;
            this.b = var4;
            this.order = var5;
            this.cofactor = var6;
            this.coord = 4;
        }

        public ECCurve cloneCurve() {
            return new Fp(this.q, this.r, this.a, this.b, this.order, this.cofactor);
        }

        public boolean supportsCoordinateSystem(int var1) {
            switch(var1) {
                case 0:
                case 1:
                case 2:
                case 4:
                    return true;
                case 3:
                default:
                    return false;
            }
        }

        public BigInteger getQ() {
            return this.q;
        }

        public int getFieldSize() {
            return this.q.bitLength();
        }

        public ECFieldElement fromBigInteger(BigInteger var1) {
            return new ECFieldElement.Fp(this.q, this.r, var1);
        }

        public ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3) {
            return new ECPoint.Fp(this, var1, var2, var3);
        }

        public ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4) {
            return new ECPoint.Fp(this, var1, var2, var3, var4);
        }

        public ECPoint importPoint(ECPoint var1) {
            if(this != var1.getCurve() && this.getCoordinateSystem() == 2 && !var1.isInfinity()) {
                switch(var1.getCurve().getCoordinateSystem()) {
                    case 2:
                    case 3:
                    case 4:
                        return new ECPoint.Fp(this, this.fromBigInteger(var1.x.toBigInteger()), this.fromBigInteger(var1.y.toBigInteger()), new ECFieldElement[]{this.fromBigInteger(var1.zs[0].toBigInteger())}, var1.withCompression);
                }
            }

            return super.importPoint(var1);
        }

        public ECPoint getInfinity() {
            return this.infinity;
        }
    }
}

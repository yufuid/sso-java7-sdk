package com.yufu.idaas.sdk.bouncycastle.math.ec.endo;


import com.yufu.idaas.sdk.bouncycastle.math.ec.ECAlgorithms;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECConstants;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECCurve;
import com.yufu.idaas.sdk.bouncycastle.math.ec.ECFieldElement;

import java.math.BigInteger;
import java.util.Hashtable;

public abstract class ECPoint {
    public static ECFieldElement[] EMPTY_ZS = new ECFieldElement[0];
    public ECCurve curve;
    public ECFieldElement x;
    public ECFieldElement y;
    public ECFieldElement[] zs;
    public boolean withCompression;
    public Hashtable preCompTable;

    protected static ECFieldElement[] getInitialZCoords(ECCurve var0) {
        int var1 = null == var0?0:var0.getCoordinateSystem();
        switch(var1) {
            case 0:
            case 5:
                return EMPTY_ZS;
            default:
                ECFieldElement var2 = var0.fromBigInteger(ECConstants.ONE);
                switch(var1) {
                    case 1:
                    case 2:
                    case 6:
                        return new ECFieldElement[]{var2};
                    case 3:
                        return new ECFieldElement[]{var2, var2, var2};
                    case 4:
                        return new ECFieldElement[]{var2, var0.getA()};
                    case 5:
                    default:
                        throw new IllegalArgumentException("unknown coordinate system");
                }
        }
    }

    protected ECPoint(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
        this(var1, var2, var3, getInitialZCoords(var1));
    }

    protected ECPoint(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4) {
        this.preCompTable = null;
        this.curve = var1;
        this.x = var2;
        this.y = var3;
        this.zs = var4;
    }

    public boolean satisfiesCofactor() {
        BigInteger var1 = this.curve.getCofactor();
        return var1 == null || var1.equals(ECConstants.ONE) || !ECAlgorithms.referenceMultiply(this, var1).isInfinity();
    }

    protected abstract boolean satisfiesCurveEquation();

    public final ECPoint getDetachedPoint() {
        return this.normalize().detach();
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    protected abstract ECPoint detach();

    protected int getCurveCoordinateSystem() {
        return null == this.curve?0:this.curve.getCoordinateSystem();
    }

    /** @deprecated */
    public ECFieldElement getX() {
        return this.normalize().getXCoord();
    }

    /** @deprecated */
    public ECFieldElement getY() {
        return this.normalize().getYCoord();
    }

    public ECFieldElement getAffineXCoord() {
        this.checkNormalized();
        return this.getXCoord();
    }

    public ECFieldElement getAffineYCoord() {
        this.checkNormalized();
        return this.getYCoord();
    }

    public ECFieldElement getXCoord() {
        return this.x;
    }

    public ECFieldElement getYCoord() {
        return this.y;
    }

    public ECFieldElement getZCoord(int var1) {
        return var1 >= 0 && var1 < this.zs.length?this.zs[var1]:null;
    }

    public ECFieldElement[] getZCoords() {
        int var1 = this.zs.length;
        if(var1 == 0) {
            return EMPTY_ZS;
        } else {
            ECFieldElement[] var2 = new ECFieldElement[var1];
            System.arraycopy(this.zs, 0, var2, 0, var1);
            return var2;
        }
    }

    public final ECFieldElement getRawXCoord() {
        return this.x;
    }

    public final ECFieldElement getRawYCoord() {
        return this.y;
    }

    protected final ECFieldElement[] getRawZCoords() {
        return this.zs;
    }

    protected void checkNormalized() {
        if(!this.isNormalized()) {
            throw new IllegalStateException("point not in normal form");
        }
    }

    public boolean isNormalized() {
        int var1 = this.getCurveCoordinateSystem();
        return var1 == 0 || var1 == 5 || this.isInfinity() || this.zs[0].isOne();
    }

    public ECPoint normalize() {
        if(this.isInfinity()) {
            return this;
        } else {
            switch(this.getCurveCoordinateSystem()) {
                case 0:
                case 5:
                    return this;
                default:
                    ECFieldElement var1 = this.getZCoord(0);
                    return var1.isOne()?this:this.normalize(var1.invert());
            }
        }
    }

    public ECPoint normalize(ECFieldElement var1) {
        switch(this.getCurveCoordinateSystem()) {
            case 1:
            case 6:
                return this.createScaledPoint(var1, var1);
            case 2:
            case 3:
            case 4:
                ECFieldElement var2 = var1.square();
                ECFieldElement var3 = var2.multiply(var1);
                return this.createScaledPoint(var2, var3);
            case 5:
            default:
                throw new IllegalStateException("not a projective coordinate system");
        }
    }

    protected ECPoint createScaledPoint(ECFieldElement var1, ECFieldElement var2) {
        return this.getCurve().createRawPoint(this.getRawXCoord().multiply(var1), this.getRawYCoord().multiply(var2), this.withCompression);
    }

    public boolean isInfinity() {
        return this.x == null || this.y == null || this.zs.length > 0 && this.zs[0].isZero();
    }

    /** @deprecated */
    public boolean isCompressed() {
        return this.withCompression;
    }

    public boolean isValid() {
        if(this.isInfinity()) {
            return true;
        } else {
            ECCurve var1 = this.getCurve();
            if(var1 != null) {
                if(!this.satisfiesCurveEquation()) {
                    return false;
                }

                if(!this.satisfiesCofactor()) {
                    return false;
                }
            }

            return true;
        }
    }

    public ECPoint scaleX(ECFieldElement var1) {
        return this.isInfinity()?this:this.getCurve().createRawPoint(this.getRawXCoord().multiply(var1), this.getRawYCoord(), this.getRawZCoords(), this.withCompression);
    }

    public ECPoint scaleY(ECFieldElement var1) {
        return this.isInfinity()?this:this.getCurve().createRawPoint(this.getRawXCoord(), this.getRawYCoord().multiply(var1), this.getRawZCoords(), this.withCompression);
    }

    public boolean equals(ECPoint var1) {
        if(null == var1) {
            return false;
        } else {
            ECCurve var2 = this.getCurve();
            ECCurve var3 = var1.getCurve();
            boolean var4 = null == var2;
            boolean var5 = null == var3;
            boolean var6 = this.isInfinity();
            boolean var7 = var1.isInfinity();
            if(!var6 && !var7) {
                ECPoint var8 = this;
                ECPoint var9 = var1;
                if(!var4 || !var5) {
                    if(var4) {
                        var9 = var1.normalize();
                    } else if(var5) {
                        var8 = this.normalize();
                    } else {
                        if(!var2.equals(var3)) {
                            return false;
                        }

                        ECPoint[] var10 = new ECPoint[]{this, var2.importPoint(var1)};
                        var2.normalizeAll(var10);
                        var8 = var10[0];
                        var9 = var10[1];
                    }
                }

                return var8.getXCoord().equals(var9.getXCoord()) && var8.getYCoord().equals(var9.getYCoord());
            } else {
                return var6 && var7 && (var4 || var5 || var2.equals(var3));
            }
        }
    }

    public boolean equals(Object var1) {
        return var1 == this?true:(!(var1 instanceof ECPoint)?false:this.equals((ECPoint)var1));
    }

    public int hashCode() {
        ECCurve var1 = this.getCurve();
        int var2 = null == var1?0:~var1.hashCode();
        if(!this.isInfinity()) {
            ECPoint var3 = this.normalize();
            var2 ^= var3.getXCoord().hashCode() * 17;
            var2 ^= var3.getYCoord().hashCode() * 257;
        }

        return var2;
    }

    public String toString() {
        if(this.isInfinity()) {
            return "INF";
        } else {
            StringBuffer var1 = new StringBuffer();
            var1.append('(');
            var1.append(this.getRawXCoord());
            var1.append(',');
            var1.append(this.getRawYCoord());

            for(int var2 = 0; var2 < this.zs.length; ++var2) {
                var1.append(',');
                var1.append(this.zs[var2]);
            }

            var1.append(')');
            return var1.toString();
        }
    }

    /** @deprecated */
    public byte[] getEncoded() {
        return this.getEncoded(this.withCompression);
    }

    public byte[] getEncoded(boolean var1) {
        if(this.isInfinity()) {
            return new byte[1];
        } else {
            ECPoint var2 = this.normalize();
            byte[] var3 = var2.getXCoord().getEncoded();
            byte[] var4;
            if(var1) {
                var4 = new byte[var3.length + 1];
                var4[0] = (byte)(var2.getCompressionYTilde()?3:2);
                System.arraycopy(var3, 0, var4, 1, var3.length);
                return var4;
            } else {
                var4 = var2.getYCoord().getEncoded();
                byte[] var5 = new byte[var3.length + var4.length + 1];
                var5[0] = 4;
                System.arraycopy(var3, 0, var5, 1, var3.length);
                System.arraycopy(var4, 0, var5, var3.length + 1, var4.length);
                return var5;
            }
        }
    }

    protected abstract boolean getCompressionYTilde();

    public abstract ECPoint add(ECPoint var1);

    public abstract ECPoint negate();

    public abstract ECPoint subtract(ECPoint var1);

    public ECPoint timesPow2(int var1) {
        if(var1 < 0) {
            throw new IllegalArgumentException("\'e\' cannot be negative");
        } else {
            ECPoint var2 = this;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                var2 = var2.twice();
            }
        }
    }

    public abstract ECPoint twice();

    public ECPoint twicePlus(ECPoint var1) {
        return this.twice().add(var1);
    }

    public ECPoint threeTimes() {
        return this.twicePlus(this);
    }

    public ECPoint multiply(BigInteger var1) {
        return this.getCurve().getMultiplier().multiply(this, var1);
    }

    public abstract static class AbstractF2m extends ECPoint {
        public AbstractF2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
            super(var1, var2, var3);
        }

        public AbstractF2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4) {
            super(var1, var2, var3, var4);
        }

        public boolean satisfiesCurveEquation() {
            ECCurve var1 = this.getCurve();
            ECFieldElement var2 = this.x;
            ECFieldElement var3 = var1.getA();
            ECFieldElement var4 = var1.getB();
            int var5 = var1.getCoordinateSystem();
            ECFieldElement var6;
            ECFieldElement var8;
            ECFieldElement var9;
            ECFieldElement var10;
            if(var5 == 6) {
                var6 = this.zs[0];
                boolean var14 = var6.isOne();
                if(var2.isZero()) {
                    var8 = this.y;
                    var9 = var8.square();
                    var10 = var4;
                    if(!var14) {
                        var10 = var4.multiply(var6.square());
                    }

                    return var9.equals(var10);
                } else {
                    var8 = this.y;
                    var9 = var2.square();
                    ECFieldElement var11;
                    if(var14) {
                        var10 = var8.square().add(var8).add(var3);
                        var11 = var9.square().add(var4);
                    } else {
                        ECFieldElement var12 = var6.square();
                        ECFieldElement var13 = var12.square();
                        var10 = var8.add(var6).multiplyPlusProduct(var8, var3, var12);
                        var11 = var9.squarePlusProduct(var4, var13);
                    }

                    var10 = var10.multiply(var9);
                    return var10.equals(var11);
                }
            } else {
                var6 = this.y;
                ECFieldElement var7 = var6.add(var2).multiply(var6);
                switch(var5) {
                    case 1:
                        var8 = this.zs[0];
                        if(!var8.isOne()) {
                            var9 = var8.square();
                            var10 = var8.multiply(var9);
                            var7 = var7.multiply(var8);
                            var3 = var3.multiply(var8);
                            var4 = var4.multiply(var10);
                        }
                    case 0:
                        var8 = var2.add(var3).multiply(var2.square()).add(var4);
                        return var7.equals(var8);
                    default:
                        throw new IllegalStateException("unsupported coordinate system");
                }
            }
        }

        public ECPoint scaleX(ECFieldElement var1) {
            if(this.isInfinity()) {
                return this;
            } else {
                int var2 = this.getCurveCoordinateSystem();
                ECFieldElement var3;
                ECFieldElement var4;
                ECFieldElement var5;
                ECFieldElement var6;
                switch(var2) {
                    case 5:
                        var3 = this.getRawXCoord();
                        var4 = this.getRawYCoord();
                        var5 = var3.multiply(var1);
                        var6 = var4.add(var3).divide(var1).add(var5);
                        return this.getCurve().createRawPoint(var3, var6, this.getRawZCoords(), this.withCompression);
                    case 6:
                        var3 = this.getRawXCoord();
                        var4 = this.getRawYCoord();
                        var5 = this.getRawZCoords()[0];
                        var6 = var3.multiply(var1.square());
                        ECFieldElement var7 = var4.add(var3).add(var6);
                        ECFieldElement var8 = var5.multiply(var1);
                        return this.getCurve().createRawPoint(var6, var7, new ECFieldElement[]{var8}, this.withCompression);
                    default:
                        return super.scaleX(var1);
                }
            }
        }

        public ECPoint scaleY(ECFieldElement var1) {
            if(this.isInfinity()) {
                return this;
            } else {
                int var2 = this.getCurveCoordinateSystem();
                switch(var2) {
                    case 5:
                    case 6:
                        ECFieldElement var3 = this.getRawXCoord();
                        ECFieldElement var4 = this.getRawYCoord();
                        ECFieldElement var5 = var4.add(var3).multiply(var1).add(var3);
                        return this.getCurve().createRawPoint(var3, var5, this.getRawZCoords(), this.withCompression);
                    default:
                        return super.scaleY(var1);
                }
            }
        }

        public ECPoint subtract(ECPoint var1) {
            return (ECPoint)(var1.isInfinity()?this:this.add(var1.negate()));
        }

        public AbstractF2m tau() {
            if(this.isInfinity()) {
                return this;
            } else {
                ECCurve var1 = this.getCurve();
                int var2 = var1.getCoordinateSystem();
                ECFieldElement var3 = this.x;
                ECFieldElement var4;
                switch(var2) {
                    case 0:
                    case 5:
                        var4 = this.y;
                        return (AbstractF2m)var1.createRawPoint(var3.square(), var4.square(), this.withCompression);
                    case 1:
                    case 6:
                        var4 = this.y;
                        ECFieldElement var5 = this.zs[0];
                        return (AbstractF2m)var1.createRawPoint(var3.square(), var4.square(), new ECFieldElement[]{var5.square()}, this.withCompression);
                    case 2:
                    case 3:
                    case 4:
                    default:
                        throw new IllegalStateException("unsupported coordinate system");
                }
            }
        }

        public AbstractF2m tauPow(int var1) {
            if(this.isInfinity()) {
                return this;
            } else {
                ECCurve var2 = this.getCurve();
                int var3 = var2.getCoordinateSystem();
                ECFieldElement var4 = this.x;
                ECFieldElement var5;
                switch(var3) {
                    case 0:
                    case 5:
                        var5 = this.y;
                        return (AbstractF2m)var2.createRawPoint(var4.squarePow(var1), var5.squarePow(var1), this.withCompression);
                    case 1:
                    case 6:
                        var5 = this.y;
                        ECFieldElement var6 = this.zs[0];
                        return (AbstractF2m)var2.createRawPoint(var4.squarePow(var1), var5.squarePow(var1), new ECFieldElement[]{var6.squarePow(var1)}, this.withCompression);
                    case 2:
                    case 3:
                    case 4:
                    default:
                        throw new IllegalStateException("unsupported coordinate system");
                }
            }
        }
    }

    public abstract static class AbstractFp extends ECPoint {
        public AbstractFp(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
            super(var1, var2, var3);
        }

        public AbstractFp(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4) {
            super(var1, var2, var3, var4);
        }

        public boolean getCompressionYTilde() {
            return this.getAffineYCoord().testBitZero();
        }

        public boolean satisfiesCurveEquation() {
            ECFieldElement var1 = this.x;
            ECFieldElement var2 = this.y;
            ECFieldElement var3 = this.curve.getA();
            ECFieldElement var4 = this.curve.getB();
            ECFieldElement var5 = var2.square();
            ECFieldElement var6;
            ECFieldElement var7;
            ECFieldElement var8;
            switch(this.getCurveCoordinateSystem()) {
                case 0:
                    break;
                case 1:
                    var6 = this.zs[0];
                    if(!var6.isOne()) {
                        var7 = var6.square();
                        var8 = var6.multiply(var7);
                        var5 = var5.multiply(var6);
                        var3 = var3.multiply(var7);
                        var4 = var4.multiply(var8);
                    }
                    break;
                case 2:
                case 3:
                case 4:
                    var6 = this.zs[0];
                    if(!var6.isOne()) {
                        var7 = var6.square();
                        var8 = var7.square();
                        ECFieldElement var9 = var7.multiply(var8);
                        var3 = var3.multiply(var8);
                        var4 = var4.multiply(var9);
                    }
                    break;
                default:
                    throw new IllegalStateException("unsupported coordinate system");
            }

            var6 = var1.square().add(var3).multiply(var1).add(var4);
            return var5.equals(var6);
        }

        public ECPoint subtract(ECPoint var1) {
            return (ECPoint)(var1.isInfinity()?this:this.add(var1.negate()));
        }
    }

    public static class F2m extends AbstractF2m {
        /** @deprecated */
        public F2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
            this(var1, var2, var3, false);
        }

        /** @deprecated */
        public F2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
            super(var1, var2, var3);
            if(var2 == null != (var3 == null)) {
                throw new IllegalArgumentException("Exactly one of the field elements is null");
            } else {
                if(var2 != null) {
                    ECFieldElement.F2m.checkFieldElements(this.x, this.y);
                    if(var1 != null) {
                        ECFieldElement.F2m.checkFieldElements(this.x, this.curve.getA());
                    }
                }

                this.withCompression = var4;
            }
        }

        public F2m(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
            super(var1, var2, var3, var4);
            this.withCompression = var5;
        }

        public ECPoint detach() {
            return new F2m((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
        }

        public ECFieldElement getYCoord() {
            int var1 = this.getCurveCoordinateSystem();
            switch(var1) {
                case 5:
                case 6:
                    ECFieldElement var2 = this.x;
                    ECFieldElement var3 = this.y;
                    if(!this.isInfinity() && !var2.isZero()) {
                        ECFieldElement var4 = var3.add(var2).multiply(var2);
                        if(6 == var1) {
                            ECFieldElement var5 = this.zs[0];
                            if(!var5.isOne()) {
                                var4 = var4.divide(var5);
                            }
                        }

                        return var4;
                    }

                    return var3;
                default:
                    return this.y;
            }
        }

        public boolean getCompressionYTilde() {
            ECFieldElement var1 = this.getRawXCoord();
            if(var1.isZero()) {
                return false;
            } else {
                ECFieldElement var2 = this.getRawYCoord();
                switch(this.getCurveCoordinateSystem()) {
                    case 5:
                    case 6:
                        return var2.testBitZero() != var1.testBitZero();
                    default:
                        return var2.divide(var1).testBitZero();
                }
            }
        }

        public ECPoint add(ECPoint var1) {
            if(this.isInfinity()) {
                return var1;
            } else if(var1.isInfinity()) {
                return this;
            } else {
                ECCurve var2 = this.getCurve();
                int var3 = var2.getCoordinateSystem();
                ECFieldElement var4 = this.x;
                ECFieldElement var5 = var1.x;
                ECFieldElement var6;
                ECFieldElement var7;
                ECFieldElement var8;
                ECFieldElement var9;
                boolean var10;
                ECFieldElement var11;
                ECFieldElement var12;
                ECFieldElement var14;
                ECFieldElement var15;
                ECFieldElement var16;
                ECFieldElement var17;
                ECFieldElement var18;
                ECFieldElement var19;
                ECFieldElement var20;
                ECFieldElement var22;
                ECFieldElement var23;
                ECFieldElement var24;
                ECFieldElement var25;
                ECFieldElement var28;
                switch(var3) {
                    case 0:
                        var6 = this.y;
                        var7 = var1.y;
                        var8 = var4.add(var5);
                        var9 = var6.add(var7);
                        if(var8.isZero()) {
                            if(var9.isZero()) {
                                return this.twice();
                            }

                            return var2.getInfinity();
                        }

                        ECFieldElement var26 = var9.divide(var8);
                        var11 = var26.square().add(var26).add(var8).add(var2.getA());
                        var12 = var26.multiply(var4.add(var11)).add(var11).add(var6);
                        return new F2m(var2, var11, var12, this.withCompression);
                    case 1:
                        var6 = this.y;
                        var7 = this.zs[0];
                        var8 = var1.y;
                        var9 = var1.zs[0];
                        var10 = var9.isOne();
                        var11 = var7.multiply(var8);
                        var12 = var10?var6:var6.multiply(var9);
                        ECFieldElement var27 = var11.add(var12);
                        var14 = var7.multiply(var5);
                        var15 = var10?var4:var4.multiply(var9);
                        var16 = var14.add(var15);
                        if(var16.isZero()) {
                            if(var27.isZero()) {
                                return this.twice();
                            }

                            return var2.getInfinity();
                        }

                        var17 = var16.square();
                        var18 = var17.multiply(var16);
                        var19 = var10?var7:var7.multiply(var9);
                        var20 = var27.add(var16);
                        var28 = var20.multiplyPlusProduct(var27, var17, var2.getA()).multiply(var19).add(var18);
                        var22 = var16.multiply(var28);
                        var23 = var10?var17:var17.multiply(var9);
                        var24 = var27.multiplyPlusProduct(var4, var16, var6).multiplyPlusProduct(var23, var20, var28);
                        var25 = var18.multiply(var19);
                        return new F2m(var2, var22, var24, new ECFieldElement[]{var25}, this.withCompression);
                    case 6:
                        if(var4.isZero()) {
                            if(var5.isZero()) {
                                return var2.getInfinity();
                            }

                            return var1.add(this);
                        } else {
                            var6 = this.y;
                            var7 = this.zs[0];
                            var8 = var1.y;
                            var9 = var1.zs[0];
                            var10 = var7.isOne();
                            var11 = var5;
                            var12 = var8;
                            if(!var10) {
                                var11 = var5.multiply(var7);
                                var12 = var8.multiply(var7);
                            }

                            boolean var13 = var9.isOne();
                            var14 = var4;
                            var15 = var6;
                            if(!var13) {
                                var14 = var4.multiply(var9);
                                var15 = var6.multiply(var9);
                            }

                            var16 = var15.add(var12);
                            var17 = var14.add(var11);
                            if(var17.isZero()) {
                                if(var16.isZero()) {
                                    return this.twice();
                                }

                                return var2.getInfinity();
                            } else {
                                if(var5.isZero()) {
                                    ECPoint var21 = this.normalize();
                                    var4 = var21.getXCoord();
                                    var22 = var21.getYCoord();
                                    var24 = var22.add(var8).divide(var4);
                                    var18 = var24.square().add(var24).add(var4).add(var2.getA());
                                    if(var18.isZero()) {
                                        return new F2m(var2, var18, var2.getB().sqrt(), this.withCompression);
                                    }

                                    var25 = var24.multiply(var4.add(var18)).add(var18).add(var22);
                                    var19 = var25.divide(var18).add(var18);
                                    var20 = var2.fromBigInteger(ECConstants.ONE);
                                } else {
                                    var17 = var17.square();
                                    var28 = var16.multiply(var14);
                                    var22 = var16.multiply(var11);
                                    var18 = var28.multiply(var22);
                                    if(var18.isZero()) {
                                        return new F2m(var2, var18, var2.getB().sqrt(), this.withCompression);
                                    }

                                    var23 = var16.multiply(var17);
                                    if(!var13) {
                                        var23 = var23.multiply(var9);
                                    }

                                    var19 = var22.add(var17).squarePlusProduct(var23, var6.add(var7));
                                    var20 = var23;
                                    if(!var10) {
                                        var20 = var23.multiply(var7);
                                    }
                                }

                                return new F2m(var2, var18, var19, new ECFieldElement[]{var20}, this.withCompression);
                            }
                        }
                    default:
                        throw new IllegalStateException("unsupported coordinate system");
                }
            }
        }

        public ECPoint twice() {
            if(this.isInfinity()) {
                return this;
            } else {
                ECCurve var1 = this.getCurve();
                ECFieldElement var2 = this.x;
                if(var2.isZero()) {
                    return var1.getInfinity();
                } else {
                    int var3 = var1.getCoordinateSystem();
                    ECFieldElement var4;
                    ECFieldElement var5;
                    boolean var6;
                    ECFieldElement var7;
                    ECFieldElement var8;
                    ECFieldElement var9;
                    ECFieldElement var10;
                    ECFieldElement var12;
                    ECFieldElement var13;
                    ECFieldElement var14;
                    ECFieldElement var15;
                    ECFieldElement var16;
                    ECFieldElement var17;
                    switch(var3) {
                        case 0:
                            var4 = this.y;
                            var5 = var4.divide(var2).add(var2);
                            ECFieldElement var18 = var5.square().add(var5).add(var1.getA());
                            var7 = var2.squarePlusProduct(var18, var5.addOne());
                            return new F2m(var1, var18, var7, this.withCompression);
                        case 1:
                            var4 = this.y;
                            var5 = this.zs[0];
                            var6 = var5.isOne();
                            var7 = var6?var2:var2.multiply(var5);
                            var8 = var6?var4:var4.multiply(var5);
                            var9 = var2.square();
                            var10 = var9.add(var8);
                            var12 = var7.square();
                            var13 = var10.add(var7);
                            var14 = var13.multiplyPlusProduct(var10, var12, var1.getA());
                            var15 = var7.multiply(var14);
                            var16 = var9.square().multiplyPlusProduct(var7, var14, var13);
                            var17 = var7.multiply(var12);
                            return new F2m(var1, var15, var16, new ECFieldElement[]{var17}, this.withCompression);
                        case 6:
                            var4 = this.y;
                            var5 = this.zs[0];
                            var6 = var5.isOne();
                            var7 = var6?var4:var4.multiply(var5);
                            var8 = var6?var5:var5.square();
                            var9 = var1.getA();
                            var10 = var6?var9:var9.multiply(var8);
                            ECFieldElement var11 = var4.square().add(var7).add(var10);
                            if(var11.isZero()) {
                                return new F2m(var1, var11, var1.getB().sqrt(), this.withCompression);
                            }

                            var12 = var11.square();
                            var13 = var6?var11:var11.multiply(var8);
                            var14 = var1.getB();
                            if(var14.bitLength() < var1.getFieldSize() >> 1) {
                                var16 = var4.add(var2).square();
                                if(var14.isOne()) {
                                    var17 = var10.add(var8).square();
                                } else {
                                    var17 = var10.squarePlusProduct(var14, var8.square());
                                }

                                var15 = var16.add(var11).add(var8).multiply(var16).add(var17).add(var12);
                                if(var9.isZero()) {
                                    var15 = var15.add(var13);
                                } else if(!var9.isOne()) {
                                    var15 = var15.add(var9.addOne().multiply(var13));
                                }
                            } else {
                                var16 = var6?var2:var2.multiply(var5);
                                var15 = var16.squarePlusProduct(var11, var7).add(var12).add(var13);
                            }

                            return new F2m(var1, var12, var15, new ECFieldElement[]{var13}, this.withCompression);
                        default:
                            throw new IllegalStateException("unsupported coordinate system");
                    }
                }
            }
        }

        public ECPoint twicePlus(ECPoint var1) {
            if(this.isInfinity()) {
                return var1;
            } else if(var1.isInfinity()) {
                return this.twice();
            } else {
                ECCurve var2 = this.getCurve();
                ECFieldElement var3 = this.x;
                if(var3.isZero()) {
                    return var1;
                } else {
                    int var4 = var2.getCoordinateSystem();
                    switch(var4) {
                        case 6:
                            ECFieldElement var5 = var1.x;
                            ECFieldElement var6 = var1.zs[0];
                            if(!var5.isZero() && var6.isOne()) {
                                ECFieldElement var7 = this.y;
                                ECFieldElement var8 = this.zs[0];
                                ECFieldElement var9 = var1.y;
                                ECFieldElement var10 = var3.square();
                                ECFieldElement var11 = var7.square();
                                ECFieldElement var12 = var8.square();
                                ECFieldElement var13 = var7.multiply(var8);
                                ECFieldElement var14 = var2.getA().multiply(var12).add(var11).add(var13);
                                ECFieldElement var15 = var9.addOne();
                                ECFieldElement var16 = var2.getA().add(var15).multiply(var12).add(var11).multiplyPlusProduct(var14, var10, var12);
                                ECFieldElement var17 = var5.multiply(var12);
                                ECFieldElement var18 = var17.add(var14).square();
                                if(var18.isZero()) {
                                    if(var16.isZero()) {
                                        return var1.twice();
                                    }

                                    return var2.getInfinity();
                                }

                                if(var16.isZero()) {
                                    return new F2m(var2, var16, var2.getB().sqrt(), this.withCompression);
                                }

                                ECFieldElement var19 = var16.square().multiply(var17);
                                ECFieldElement var20 = var16.multiply(var18).multiply(var12);
                                ECFieldElement var21 = var16.add(var18).square().multiplyPlusProduct(var14, var15, var20);
                                return new F2m(var2, var19, var21, new ECFieldElement[]{var20}, this.withCompression);
                            }

                            return this.twice().add(var1);
                        default:
                            return this.twice().add(var1);
                    }
                }
            }
        }

        public ECPoint negate() {
            if(this.isInfinity()) {
                return this;
            } else {
                ECFieldElement var1 = this.x;
                if(var1.isZero()) {
                    return this;
                } else {
                    ECFieldElement var2;
                    ECFieldElement var3;
                    switch(this.getCurveCoordinateSystem()) {
                        case 0:
                            var2 = this.y;
                            return new F2m(this.curve, var1, var2.add(var1), this.withCompression);
                        case 1:
                            var2 = this.y;
                            var3 = this.zs[0];
                            return new F2m(this.curve, var1, var2.add(var1), new ECFieldElement[]{var3}, this.withCompression);
                        case 2:
                        case 3:
                        case 4:
                        default:
                            throw new IllegalStateException("unsupported coordinate system");
                        case 5:
                            var2 = this.y;
                            return new F2m(this.curve, var1, var2.addOne(), this.withCompression);
                        case 6:
                            var2 = this.y;
                            var3 = this.zs[0];
                            return new F2m(this.curve, var1, var2.add(var3), new ECFieldElement[]{var3}, this.withCompression);
                    }
                }
            }
        }
    }

    public static class Fp extends AbstractFp {
        /** @deprecated */
        public Fp(ECCurve var1, ECFieldElement var2, ECFieldElement var3) {
            this(var1, var2, var3, false);
        }

        /** @deprecated */
        public Fp(ECCurve var1, ECFieldElement var2, ECFieldElement var3, boolean var4) {
            super(var1, var2, var3);
            if(var2 == null != (var3 == null)) {
                throw new IllegalArgumentException("Exactly one of the field elements is null");
            } else {
                this.withCompression = var4;
            }
        }

        public Fp(ECCurve var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement[] var4, boolean var5) {
            super(var1, var2, var3, var4);
            this.withCompression = var5;
        }

        public ECPoint detach() {
            return new Fp((ECCurve)null, this.getAffineXCoord(), this.getAffineYCoord());
        }

        public ECFieldElement getZCoord(int var1) {
            return var1 == 1 && 4 == this.getCurveCoordinateSystem()?this.getJacobianModifiedW():super.getZCoord(var1);
        }

        public ECPoint add(ECPoint var1) {
            if(this.isInfinity()) {
                return var1;
            } else if(var1.isInfinity()) {
                return this;
            } else if(this == var1) {
                return this.twice();
            } else {
                ECCurve var2 = this.getCurve();
                int var3 = var2.getCoordinateSystem();
                ECFieldElement var4 = this.x;
                ECFieldElement var5 = this.y;
                ECFieldElement var6 = var1.x;
                ECFieldElement var7 = var1.y;
                ECFieldElement var8;
                ECFieldElement var9;
                boolean var10;
                ECFieldElement var11;
                ECFieldElement var12;
                ECFieldElement var13;
                ECFieldElement var14;
                ECFieldElement var15;
                ECFieldElement var16;
                ECFieldElement var17;
                ECFieldElement var18;
                ECFieldElement var19;
                ECFieldElement var20;
                ECFieldElement var21;
                ECFieldElement var22;
                ECFieldElement var23;
                ECFieldElement var24;
                ECFieldElement var25;
                switch(var3) {
                    case 0:
                        var8 = var6.subtract(var4);
                        var9 = var7.subtract(var5);
                        if(var8.isZero()) {
                            if(var9.isZero()) {
                                return this.twice();
                            }

                            return var2.getInfinity();
                        }

                        ECFieldElement var27 = var9.divide(var8);
                        var11 = var27.square().subtract(var4).subtract(var6);
                        var12 = var27.multiply(var4.subtract(var11)).subtract(var5);
                        return new Fp(var2, var11, var12, this.withCompression);
                    case 1:
                        var8 = this.zs[0];
                        var9 = var1.zs[0];
                        var10 = var8.isOne();
                        boolean var28 = var9.isOne();
                        var12 = var10?var7:var7.multiply(var8);
                        var13 = var28?var5:var5.multiply(var9);
                        var14 = var12.subtract(var13);
                        var15 = var10?var6:var6.multiply(var8);
                        var16 = var28?var4:var4.multiply(var9);
                        var17 = var15.subtract(var16);
                        if(var17.isZero()) {
                            if(var14.isZero()) {
                                return this.twice();
                            }

                            return var2.getInfinity();
                        }

                        var18 = var10?var9:(var28?var8:var8.multiply(var9));
                        var19 = var17.square();
                        var20 = var19.multiply(var17);
                        var21 = var19.multiply(var16);
                        var22 = var14.square().multiply(var18).subtract(var20).subtract(this.two(var21));
                        var23 = var17.multiply(var22);
                        var24 = var21.subtract(var22).multiplyMinusProduct(var14, var13, var20);
                        var25 = var20.multiply(var18);
                        return new Fp(var2, var23, var24, new ECFieldElement[]{var25}, this.withCompression);
                    case 2:
                    case 4:
                        var8 = this.zs[0];
                        var9 = var1.zs[0];
                        var10 = var8.isOne();
                        var14 = null;
                        if(!var10 && var8.equals(var9)) {
                            var15 = var4.subtract(var6);
                            var16 = var5.subtract(var7);
                            if(var15.isZero()) {
                                if(var16.isZero()) {
                                    return this.twice();
                                }

                                return var2.getInfinity();
                            }

                            var17 = var15.square();
                            var18 = var4.multiply(var17);
                            var19 = var6.multiply(var17);
                            var20 = var18.subtract(var19).multiply(var5);
                            var11 = var16.square().subtract(var18).subtract(var19);
                            var12 = var18.subtract(var11).multiply(var16).subtract(var20);
                            var13 = var15.multiply(var8);
                        } else {
                            if(var10) {
                                var16 = var6;
                                var17 = var7;
                            } else {
                                var15 = var8.square();
                                var16 = var15.multiply(var6);
                                var18 = var15.multiply(var8);
                                var17 = var18.multiply(var7);
                            }

                            boolean var30 = var9.isOne();
                            if(var30) {
                                var20 = var4;
                                var21 = var5;
                            } else {
                                var19 = var9.square();
                                var20 = var19.multiply(var4);
                                var22 = var19.multiply(var9);
                                var21 = var22.multiply(var5);
                            }

                            var22 = var20.subtract(var16);
                            var23 = var21.subtract(var17);
                            if(var22.isZero()) {
                                if(var23.isZero()) {
                                    return this.twice();
                                }

                                return var2.getInfinity();
                            }

                            var24 = var22.square();
                            var25 = var24.multiply(var22);
                            ECFieldElement var26 = var24.multiply(var20);
                            var11 = var23.square().add(var25).subtract(this.two(var26));
                            var12 = var26.subtract(var11).multiplyMinusProduct(var23, var25, var21);
                            var13 = var22;
                            if(!var10) {
                                var13 = var22.multiply(var8);
                            }

                            if(!var30) {
                                var13 = var13.multiply(var9);
                            }

                            if(var13 == var22) {
                                var14 = var24;
                            }
                        }

                        ECFieldElement[] var29;
                        if(var3 == 4) {
                            var16 = this.calculateJacobianModifiedW(var13, var14);
                            var29 = new ECFieldElement[]{var13, var16};
                        } else {
                            var29 = new ECFieldElement[]{var13};
                        }

                        return new Fp(var2, var11, var12, var29, this.withCompression);
                    case 3:
                    default:
                        throw new IllegalStateException("unsupported coordinate system");
                }
            }
        }

        public ECPoint twice() {
            if(this.isInfinity()) {
                return this;
            } else {
                ECCurve var1 = this.getCurve();
                ECFieldElement var2 = this.y;
                if(var2.isZero()) {
                    return var1.getInfinity();
                } else {
                    int var3 = var1.getCoordinateSystem();
                    ECFieldElement var4 = this.x;
                    ECFieldElement var5;
                    boolean var6;
                    ECFieldElement var7;
                    ECFieldElement var8;
                    ECFieldElement var9;
                    ECFieldElement var10;
                    ECFieldElement var11;
                    ECFieldElement var12;
                    ECFieldElement var13;
                    ECFieldElement var14;
                    ECFieldElement var15;
                    switch(var3) {
                        case 0:
                            var5 = var4.square();
                            ECFieldElement var19 = this.three(var5).add(this.getCurve().getA()).divide(this.two(var2));
                            var7 = var19.square().subtract(this.two(var4));
                            var8 = var19.multiply(var4.subtract(var7)).subtract(var2);
                            return new Fp(var1, var7, var8, this.withCompression);
                        case 1:
                            var5 = this.zs[0];
                            var6 = var5.isOne();
                            var7 = var1.getA();
                            if(!var7.isZero() && !var6) {
                                var7 = var7.multiply(var5.square());
                            }

                            var7 = var7.add(this.three(var4.square()));
                            var8 = var6?var2:var2.multiply(var5);
                            var9 = var6?var2.square():var8.multiply(var2);
                            var10 = var4.multiply(var9);
                            var11 = this.four(var10);
                            var12 = var7.square().subtract(this.two(var11));
                            var13 = this.two(var8);
                            var14 = var12.multiply(var13);
                            var15 = this.two(var9);
                            ECFieldElement var16 = var11.subtract(var12).multiply(var7).subtract(this.two(var15.square()));
                            ECFieldElement var17 = var6?this.two(var15):var13.square();
                            ECFieldElement var18 = this.two(var17).multiply(var8);
                            return new Fp(var1, var14, var16, new ECFieldElement[]{var18}, this.withCompression);
                        case 2:
                            var5 = this.zs[0];
                            var6 = var5.isOne();
                            var7 = var2.square();
                            var8 = var7.square();
                            var9 = var1.getA();
                            var10 = var9.negate();
                            if(var10.toBigInteger().equals(BigInteger.valueOf(3L))) {
                                var13 = var6?var5:var5.square();
                                var11 = this.three(var4.add(var13).multiply(var4.subtract(var13)));
                                var12 = this.four(var7.multiply(var4));
                            } else {
                                var13 = var4.square();
                                var11 = this.three(var13);
                                if(var6) {
                                    var11 = var11.add(var9);
                                } else if(!var9.isZero()) {
                                    var14 = var5.square();
                                    var15 = var14.square();
                                    if(var10.bitLength() < var9.bitLength()) {
                                        var11 = var11.subtract(var15.multiply(var10));
                                    } else {
                                        var11 = var11.add(var15.multiply(var9));
                                    }
                                }

                                var12 = this.four(var4.multiply(var7));
                            }

                            var13 = var11.square().subtract(this.two(var12));
                            var14 = var12.subtract(var13).multiply(var11).subtract(this.eight(var8));
                            var15 = this.two(var2);
                            if(!var6) {
                                var15 = var15.multiply(var5);
                            }

                            return new Fp(var1, var13, var14, new ECFieldElement[]{var15}, this.withCompression);
                        case 3:
                        default:
                            throw new IllegalStateException("unsupported coordinate system");
                        case 4:
                            return this.twiceJacobianModified(true);
                    }
                }
            }
        }

        public ECPoint twicePlus(ECPoint var1) {
            if(this == var1) {
                return this.threeTimes();
            } else if(this.isInfinity()) {
                return var1;
            } else if(var1.isInfinity()) {
                return this.twice();
            } else {
                ECFieldElement var2 = this.y;
                if(var2.isZero()) {
                    return var1;
                } else {
                    ECCurve var3 = this.getCurve();
                    int var4 = var3.getCoordinateSystem();
                    switch(var4) {
                        case 0:
                            ECFieldElement var5 = this.x;
                            ECFieldElement var6 = var1.x;
                            ECFieldElement var7 = var1.y;
                            ECFieldElement var8 = var6.subtract(var5);
                            ECFieldElement var9 = var7.subtract(var2);
                            if(var8.isZero()) {
                                if(var9.isZero()) {
                                    return this.threeTimes();
                                }

                                return this;
                            } else {
                                ECFieldElement var10 = var8.square();
                                ECFieldElement var11 = var9.square();
                                ECFieldElement var12 = var10.multiply(this.two(var5).add(var6)).subtract(var11);
                                if(var12.isZero()) {
                                    return var3.getInfinity();
                                }

                                ECFieldElement var13 = var12.multiply(var8);
                                ECFieldElement var14 = var13.invert();
                                ECFieldElement var15 = var12.multiply(var14).multiply(var9);
                                ECFieldElement var16 = this.two(var2).multiply(var10).multiply(var8).multiply(var14).subtract(var15);
                                ECFieldElement var17 = var16.subtract(var15).multiply(var15.add(var16)).add(var6);
                                ECFieldElement var18 = var5.subtract(var17).multiply(var16).subtract(var2);
                                return new Fp(var3, var17, var18, this.withCompression);
                            }
                        case 4:
                            return this.twiceJacobianModified(false).add(var1);
                        default:
                            return this.twice().add(var1);
                    }
                }
            }
        }

        public ECPoint threeTimes() {
            if(this.isInfinity()) {
                return this;
            } else {
                ECFieldElement var1 = this.y;
                if(var1.isZero()) {
                    return this;
                } else {
                    ECCurve var2 = this.getCurve();
                    int var3 = var2.getCoordinateSystem();
                    switch(var3) {
                        case 0:
                            ECFieldElement var4 = this.x;
                            ECFieldElement var5 = this.two(var1);
                            ECFieldElement var6 = var5.square();
                            ECFieldElement var7 = this.three(var4.square()).add(this.getCurve().getA());
                            ECFieldElement var8 = var7.square();
                            ECFieldElement var9 = this.three(var4).multiply(var6).subtract(var8);
                            if(var9.isZero()) {
                                return this.getCurve().getInfinity();
                            }

                            ECFieldElement var10 = var9.multiply(var5);
                            ECFieldElement var11 = var10.invert();
                            ECFieldElement var12 = var9.multiply(var11).multiply(var7);
                            ECFieldElement var13 = var6.square().multiply(var11).subtract(var12);
                            ECFieldElement var14 = var13.subtract(var12).multiply(var12.add(var13)).add(var4);
                            ECFieldElement var15 = var4.subtract(var14).multiply(var13).subtract(var1);
                            return new Fp(var2, var14, var15, this.withCompression);
                        case 4:
                            return this.twiceJacobianModified(false).add(this);
                        default:
                            return this.twice().add(this);
                    }
                }
            }
        }

        public ECPoint timesPow2(int var1) {
            if(var1 < 0) {
                throw new IllegalArgumentException("\'e\' cannot be negative");
            } else if(var1 != 0 && !this.isInfinity()) {
                if(var1 == 1) {
                    return this.twice();
                } else {
                    ECCurve var2 = this.getCurve();
                    ECFieldElement var3 = this.y;
                    if(var3.isZero()) {
                        return var2.getInfinity();
                    } else {
                        int var4 = var2.getCoordinateSystem();
                        ECFieldElement var5 = var2.getA();
                        ECFieldElement var6 = this.x;
                        ECFieldElement var7 = this.zs.length < 1?var2.fromBigInteger(ECConstants.ONE):this.zs[0];
                        ECFieldElement var8;
                        if(!var7.isOne()) {
                            switch(var4) {
                                case 0:
                                    break;
                                case 1:
                                    var8 = var7.square();
                                    var6 = var6.multiply(var7);
                                    var3 = var3.multiply(var8);
                                    var5 = this.calculateJacobianModifiedW(var7, var8);
                                    break;
                                case 2:
                                    var5 = this.calculateJacobianModifiedW(var7, (ECFieldElement)null);
                                    break;
                                case 3:
                                default:
                                    throw new IllegalStateException("unsupported coordinate system");
                                case 4:
                                    var5 = this.getJacobianModifiedW();
                            }
                        }

                        ECFieldElement var9;
                        ECFieldElement var10;
                        for(int var16 = 0; var16 < var1; ++var16) {
                            if(var3.isZero()) {
                                return var2.getInfinity();
                            }

                            var9 = var6.square();
                            var10 = this.three(var9);
                            ECFieldElement var11 = this.two(var3);
                            ECFieldElement var12 = var11.multiply(var3);
                            ECFieldElement var13 = this.two(var6.multiply(var12));
                            ECFieldElement var14 = var12.square();
                            ECFieldElement var15 = this.two(var14);
                            if(!var5.isZero()) {
                                var10 = var10.add(var5);
                                var5 = this.two(var15.multiply(var5));
                            }

                            var6 = var10.square().subtract(this.two(var13));
                            var3 = var10.multiply(var13.subtract(var6)).subtract(var15);
                            var7 = var7.isOne()?var11:var11.multiply(var7);
                        }

                        switch(var4) {
                            case 0:
                                var8 = var7.invert();
                                var9 = var8.square();
                                var10 = var9.multiply(var8);
                                return new Fp(var2, var6.multiply(var9), var3.multiply(var10), this.withCompression);
                            case 1:
                                var6 = var6.multiply(var7);
                                var7 = var7.multiply(var7.square());
                                return new Fp(var2, var6, var3, new ECFieldElement[]{var7}, this.withCompression);
                            case 2:
                                return new Fp(var2, var6, var3, new ECFieldElement[]{var7}, this.withCompression);
                            case 3:
                            default:
                                throw new IllegalStateException("unsupported coordinate system");
                            case 4:
                                return new Fp(var2, var6, var3, new ECFieldElement[]{var7, var5}, this.withCompression);
                        }
                    }
                }
            } else {
                return this;
            }
        }

        protected ECFieldElement two(ECFieldElement var1) {
            return var1.add(var1);
        }

        protected ECFieldElement three(ECFieldElement var1) {
            return this.two(var1).add(var1);
        }

        protected ECFieldElement four(ECFieldElement var1) {
            return this.two(this.two(var1));
        }

        protected ECFieldElement eight(ECFieldElement var1) {
            return this.four(this.two(var1));
        }

        protected ECFieldElement doubleProductFromSquares(ECFieldElement var1, ECFieldElement var2, ECFieldElement var3, ECFieldElement var4) {
            return var1.add(var2).square().subtract(var3).subtract(var4);
        }

        public ECPoint negate() {
            if(this.isInfinity()) {
                return this;
            } else {
                ECCurve var1 = this.getCurve();
                int var2 = var1.getCoordinateSystem();
                return 0 != var2?new Fp(var1, this.x, this.y.negate(), this.zs, this.withCompression):new Fp(var1, this.x, this.y.negate(), this.withCompression);
            }
        }

        protected ECFieldElement calculateJacobianModifiedW(ECFieldElement var1, ECFieldElement var2) {
            ECFieldElement var3 = this.getCurve().getA();
            if(!var3.isZero() && !var1.isOne()) {
                if(var2 == null) {
                    var2 = var1.square();
                }

                ECFieldElement var4 = var2.square();
                ECFieldElement var5 = var3.negate();
                if(var5.bitLength() < var3.bitLength()) {
                    var4 = var4.multiply(var5).negate();
                } else {
                    var4 = var4.multiply(var3);
                }

                return var4;
            } else {
                return var3;
            }
        }

        protected ECFieldElement getJacobianModifiedW() {
            ECFieldElement var1 = this.zs[1];
            if(var1 == null) {
                this.zs[1] = var1 = this.calculateJacobianModifiedW(this.zs[0], (ECFieldElement)null);
            }

            return var1;
        }

        protected Fp twiceJacobianModified(boolean var1) {
            ECFieldElement var2 = this.x;
            ECFieldElement var3 = this.y;
            ECFieldElement var4 = this.zs[0];
            ECFieldElement var5 = this.getJacobianModifiedW();
            ECFieldElement var6 = var2.square();
            ECFieldElement var7 = this.three(var6).add(var5);
            ECFieldElement var8 = this.two(var3);
            ECFieldElement var9 = var8.multiply(var3);
            ECFieldElement var10 = this.two(var2.multiply(var9));
            ECFieldElement var11 = var7.square().subtract(this.two(var10));
            ECFieldElement var12 = var9.square();
            ECFieldElement var13 = this.two(var12);
            ECFieldElement var14 = var7.multiply(var10.subtract(var11)).subtract(var13);
            ECFieldElement var15 = var1?this.two(var13.multiply(var5)):null;
            ECFieldElement var16 = var4.isOne()?var8:var8.multiply(var4);
            return new Fp(this.getCurve(), var11, var14, new ECFieldElement[]{var16, var15}, this.withCompression);
        }
    }
}

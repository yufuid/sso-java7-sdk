package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by mac on 2017/1/17.
 */
public class ASN1GeneralizedTime extends ASN1Primitive {
    private byte[] time;

    public static ASN1GeneralizedTime getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof ASN1GeneralizedTime)) {
            if(var0 instanceof byte[]) {
                try {
                    return (ASN1GeneralizedTime)fromByteArray((byte[])((byte[])var0));
                } catch (Exception var2) {
                    throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
                }
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (ASN1GeneralizedTime)var0;
        }
    }

    public static ASN1GeneralizedTime getInstance(ASN1TaggedObject var0, boolean var1) {
        ASN1Primitive var2 = var0.getObject();
        return !var1 && !(var2 instanceof ASN1GeneralizedTime)?new ASN1GeneralizedTime(((ASN1OctetString)var2).getOctets()):getInstance(var2);
    }

    public ASN1GeneralizedTime(String var1) {
        this.time = Strings.toByteArray(var1);

        try {
            this.getDate();
        } catch (ParseException var3) {
            throw new IllegalArgumentException("invalid date string: " + var3.getMessage());
        }
    }

    public ASN1GeneralizedTime(Date var1) {
        SimpleDateFormat var2 = new SimpleDateFormat("yyyyMMddHHmmss\'Z\'");
        var2.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(var2.format(var1));
    }

    public ASN1GeneralizedTime(Date var1, Locale var2) {
        SimpleDateFormat var3 = new SimpleDateFormat("yyyyMMddHHmmss\'Z\'", var2);
        var3.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(var3.format(var1));
    }

    ASN1GeneralizedTime(byte[] var1) {
        this.time = var1;
    }

    public String getTimeString() {
        return Strings.fromByteArray(this.time);
    }

    public String getTime() {
        String var1 = Strings.fromByteArray(this.time);
        if(var1.charAt(var1.length() - 1) == 90) {
            return var1.substring(0, var1.length() - 1) + "GMT+00:00";
        } else {
            int var2 = var1.length() - 5;
            char var3 = var1.charAt(var2);
            if(var3 != 45 && var3 != 43) {
                var2 = var1.length() - 3;
                var3 = var1.charAt(var2);
                return var3 != 45 && var3 != 43?var1 + this.calculateGMTOffset():var1.substring(0, var2) + "GMT" + var1.substring(var2) + ":00";
            } else {
                return var1.substring(0, var2) + "GMT" + var1.substring(var2, var2 + 3) + ":" + var1.substring(var2 + 3);
            }
        }
    }

    private String calculateGMTOffset() {
        String var1 = "+";
        TimeZone var2 = TimeZone.getDefault();
        int var3 = var2.getRawOffset();
        if(var3 < 0) {
            var1 = "-";
            var3 = -var3;
        }

        int var4 = var3 / 3600000;
        int var5 = (var3 - var4 * 60 * 60 * 1000) / '\uea60';

        try {
            if(var2.useDaylightTime() && var2.inDaylightTime(this.getDate())) {
                var4 += var1.equals("+")?1:-1;
            }
        } catch (ParseException var7) {
            ;
        }

        return "GMT" + var1 + this.convert(var4) + ":" + this.convert(var5);
    }

    private String convert(int var1) {
        return var1 < 10?"0" + var1:Integer.toString(var1);
    }

    public Date getDate() throws ParseException {
        String var2 = Strings.fromByteArray(this.time);
        String var3 = var2;
        SimpleDateFormat var1;
        if(var2.endsWith("Z")) {
            if(this.hasFractionalSeconds()) {
                var1 = new SimpleDateFormat("yyyyMMddHHmmss.SSS\'Z\'");
            } else {
                var1 = new SimpleDateFormat("yyyyMMddHHmmss\'Z\'");
            }

            var1.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else if(var2.indexOf(45) <= 0 && var2.indexOf(43) <= 0) {
            if(this.hasFractionalSeconds()) {
                var1 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
            } else {
                var1 = new SimpleDateFormat("yyyyMMddHHmmss");
            }

            var1.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        } else {
            var3 = this.getTime();
            if(this.hasFractionalSeconds()) {
                var1 = new SimpleDateFormat("yyyyMMddHHmmss.SSSz");
            } else {
                var1 = new SimpleDateFormat("yyyyMMddHHmmssz");
            }

            var1.setTimeZone(new SimpleTimeZone(0, "Z"));
        }

        if(this.hasFractionalSeconds()) {
            String var4 = var3.substring(14);

            int var5;
            for(var5 = 1; var5 < var4.length(); ++var5) {
                char var6 = var4.charAt(var5);
                if(48 > var6 || var6 > 57) {
                    break;
                }
            }

            if(var5 - 1 > 3) {
                var4 = var4.substring(0, 4) + var4.substring(var5);
                var3 = var3.substring(0, 14) + var4;
            } else if(var5 - 1 == 1) {
                var4 = var4.substring(0, var5) + "00" + var4.substring(var5);
                var3 = var3.substring(0, 14) + var4;
            } else if(var5 - 1 == 2) {
                var4 = var4.substring(0, var5) + "0" + var4.substring(var5);
                var3 = var3.substring(0, 14) + var4;
            }
        }

        return var1.parse(var3);
    }

    private boolean hasFractionalSeconds() {
        for(int var1 = 0; var1 != this.time.length; ++var1) {
            if(this.time[var1] == 46 && var1 == 14) {
                return true;
            }
        }

        return false;
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() {
        int var1 = this.time.length;
        return 1 + StreamUtil.calculateBodyLength(var1) + var1;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.writeEncoded(24, this.time);
    }

    boolean asn1Equals(ASN1Primitive var1) {
        return !(var1 instanceof ASN1GeneralizedTime)?false:Arrays.areEqual(this.time, ((ASN1GeneralizedTime)var1).time);
    }

    public int hashCode() {
        return Arrays.hashCode(this.time);
    }
}

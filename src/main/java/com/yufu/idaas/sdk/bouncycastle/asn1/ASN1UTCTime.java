package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by mac on 2017/1/17.
 */
public class ASN1UTCTime extends ASN1Primitive {
    private byte[] time;

    public static ASN1UTCTime getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof ASN1UTCTime)) {
            if(var0 instanceof byte[]) {
                try {
                    return (ASN1UTCTime)fromByteArray((byte[])((byte[])var0));
                } catch (Exception var2) {
                    throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
                }
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (ASN1UTCTime)var0;
        }
    }

    public static ASN1UTCTime getInstance(ASN1TaggedObject var0, boolean var1) {
        ASN1Primitive var2 = var0.getObject();
        return !var1 && !(var2 instanceof ASN1UTCTime)?new ASN1UTCTime(((ASN1OctetString)var2).getOctets()):getInstance(var2);
    }

    public ASN1UTCTime(String var1) {
        this.time = Strings.toByteArray(var1);

        try {
            this.getDate();
        } catch (ParseException var3) {
            throw new IllegalArgumentException("invalid date string: " + var3.getMessage());
        }
    }

    public ASN1UTCTime(Date var1) {
        SimpleDateFormat var2 = new SimpleDateFormat("yyMMddHHmmss\'Z\'");
        var2.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(var2.format(var1));
    }

    public ASN1UTCTime(Date var1, Locale var2) {
        SimpleDateFormat var3 = new SimpleDateFormat("yyMMddHHmmss\'Z\'", var2);
        var3.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.time = Strings.toByteArray(var3.format(var1));
    }

    ASN1UTCTime(byte[] var1) {
        this.time = var1;
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat var1 = new SimpleDateFormat("yyMMddHHmmssz");
        return var1.parse(this.getTime());
    }

    public Date getAdjustedDate() throws ParseException {
        SimpleDateFormat var1 = new SimpleDateFormat("yyyyMMddHHmmssz");
        var1.setTimeZone(new SimpleTimeZone(0, "Z"));
        return var1.parse(this.getAdjustedTime());
    }

    public String getTime() {
        String var1 = Strings.fromByteArray(this.time);
        if(var1.indexOf(45) < 0 && var1.indexOf(43) < 0) {
            return var1.length() == 11?var1.substring(0, 10) + "00GMT+00:00":var1.substring(0, 12) + "GMT+00:00";
        } else {
            int var2 = var1.indexOf(45);
            if(var2 < 0) {
                var2 = var1.indexOf(43);
            }

            String var3 = var1;
            if(var2 == var1.length() - 3) {
                var3 = var1 + "00";
            }

            return var2 == 10?var3.substring(0, 10) + "00GMT" + var3.substring(10, 13) + ":" + var3.substring(13, 15):var3.substring(0, 12) + "GMT" + var3.substring(12, 15) + ":" + var3.substring(15, 17);
        }
    }

    public String getAdjustedTime() {
        String var1 = this.getTime();
        return var1.charAt(0) < 53?"20" + var1:"19" + var1;
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() {
        int var1 = this.time.length;
        return 1 + StreamUtil.calculateBodyLength(var1) + var1;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.write(23);
        int var2 = this.time.length;
        var1.writeLength(var2);

        for(int var3 = 0; var3 != var2; ++var3) {
            var1.write(this.time[var3]);
        }

    }

    boolean asn1Equals(ASN1Primitive var1) {
        return !(var1 instanceof ASN1UTCTime)?false: Arrays.areEqual(this.time, ((ASN1UTCTime)var1).time);
    }

    public int hashCode() {
        return Arrays.hashCode(this.time);
    }

    public String toString() {
        return Strings.fromByteArray(this.time);
    }
}


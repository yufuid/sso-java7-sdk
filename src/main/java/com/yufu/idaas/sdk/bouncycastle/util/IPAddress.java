package com.yufu.idaas.sdk.bouncycastle.util;

/**
 * Created by mac on 2017/1/18.
 */
public class IPAddress {
    public IPAddress() {
    }

    public static boolean isValid(String var0) {
        return isValidIPv4(var0) || isValidIPv6(var0);
    }

    public static boolean isValidWithNetMask(String var0) {
        return isValidIPv4WithNetmask(var0) || isValidIPv6WithNetmask(var0);
    }

    public static boolean isValidIPv4(String var0) {
        if(var0.length() == 0) {
            return false;
        } else {
            int var2 = 0;
            String var3 = var0 + ".";
            int var5 = 0;

            while(true) {
                int var4;
                if(var5 < var3.length() && (var4 = var3.indexOf(46, var5)) > var5) {
                    if(var2 == 4) {
                        return false;
                    }

                    int var1;
                    try {
                        var1 = Integer.parseInt(var3.substring(var5, var4));
                    } catch (NumberFormatException var7) {
                        return false;
                    }

                    if(var1 >= 0 && var1 <= 255) {
                        var5 = var4 + 1;
                        ++var2;
                        continue;
                    }

                    return false;
                }

                return var2 == 4;
            }
        }
    }

    public static boolean isValidIPv4WithNetmask(String var0) {
        int var1 = var0.indexOf("/");
        String var2 = var0.substring(var1 + 1);
        return var1 > 0 && isValidIPv4(var0.substring(0, var1)) && (isValidIPv4(var2) || isMaskValue(var2, 32));
    }

    public static boolean isValidIPv6WithNetmask(String var0) {
        int var1 = var0.indexOf("/");
        String var2 = var0.substring(var1 + 1);
        return var1 > 0 && isValidIPv6(var0.substring(0, var1)) && (isValidIPv6(var2) || isMaskValue(var2, 128));
    }

    private static boolean isMaskValue(String var0, int var1) {
        try {
            int var2 = Integer.parseInt(var0);
            return var2 >= 0 && var2 <= var1;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    public static boolean isValidIPv6(String var0) {
        if(var0.length() == 0) {
            return false;
        } else {
            int var2 = 0;
            String var3 = var0 + ":";
            boolean var4 = false;

            int var5;
            for(int var6 = 0; var6 < var3.length() && (var5 = var3.indexOf(58, var6)) >= var6; ++var2) {
                if(var2 == 8) {
                    return false;
                }

                if(var6 != var5) {
                    String var7 = var3.substring(var6, var5);
                    if(var5 == var3.length() - 1 && var7.indexOf(46) > 0) {
                        if(!isValidIPv4(var7)) {
                            return false;
                        }

                        ++var2;
                    } else {
                        int var1;
                        try {
                            var1 = Integer.parseInt(var3.substring(var6, var5), 16);
                        } catch (NumberFormatException var9) {
                            return false;
                        }

                        if(var1 < 0 || var1 > '\uffff') {
                            return false;
                        }
                    }
                } else {
                    if(var5 != 1 && var5 != var3.length() - 1 && var4) {
                        return false;
                    }

                    var4 = true;
                }

                var6 = var5 + 1;
            }

            return var2 == 8 || var4;
        }
    }
}

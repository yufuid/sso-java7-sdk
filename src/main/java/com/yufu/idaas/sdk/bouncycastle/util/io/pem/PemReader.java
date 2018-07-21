package com.yufu.idaas.sdk.bouncycastle.util.io.pem;


import com.yufu.idaas.sdk.bouncycastle.util.encoders.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by mac on 2017/1/18.
 */
public class PemReader extends BufferedReader {
    private static final String BEGIN = "-----BEGIN ";
    private static final String END = "-----END ";

    public PemReader(Reader var1) {
        super(var1);
    }

    public PemObject readPemObject() throws IOException {
        String var1;
        for(var1 = this.readLine(); var1 != null && !var1.startsWith("-----BEGIN "); var1 = this.readLine()) {
            ;
        }

        if(var1 != null) {
            var1 = var1.substring("-----BEGIN ".length());
            int var2 = var1.indexOf(45);
            String var3 = var1.substring(0, var2);
            if(var2 > 0) {
                return this.loadObject(var3);
            }
        }

        return null;
    }

    private PemObject loadObject(String var1) throws IOException {
        String var3 = "-----END " + var1;
        StringBuffer var4 = new StringBuffer();
        ArrayList var5 = new ArrayList();

        String var2;
        while((var2 = this.readLine()) != null) {
            if(var2.indexOf(":") >= 0) {
                int var6 = var2.indexOf(58);
                String var7 = var2.substring(0, var6);
                String var8 = var2.substring(var6 + 1).trim();
                var5.add(new PemHeader(var7, var8));
            } else {
                if(var2.indexOf(var3) != -1) {
                    break;
                }

                var4.append(var2.trim());
            }
        }

        if(var2 == null) {
            throw new IOException(var3 + " not found");
        } else {
            return new PemObject(var1, var5, Base64.decode(var4.toString()));
        }
    }
}

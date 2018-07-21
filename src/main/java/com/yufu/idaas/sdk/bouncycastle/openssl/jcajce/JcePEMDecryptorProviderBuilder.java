package com.yufu.idaas.sdk.bouncycastle.openssl.jcajce;

import com.yufu.idaas.sdk.bouncycastle.openssl.PEMDecryptor;
import com.yufu.idaas.sdk.bouncycastle.openssl.PEMDecryptorProvider;
import com.yufu.idaas.sdk.bouncycastle.openssl.PEMException;
import com.yufu.idaas.sdk.bouncycastle.openssl.PasswordException;
import com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util.DefaultJcaJceHelper;
import com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util.JcaJceHelper;
import com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util.NamedJcaJceHelper;
import com.yufu.idaas.sdk.bouncycastle.openssl.jcajce.util.ProviderJcaJceHelper;

import java.security.Provider;

/**
 * Created by mac on 2017/1/18.
 */
public class JcePEMDecryptorProviderBuilder {
    private JcaJceHelper helper = new DefaultJcaJceHelper();

    public JcePEMDecryptorProviderBuilder() {
    }

    public JcePEMDecryptorProviderBuilder setProvider(Provider var1) {
        this.helper = new ProviderJcaJceHelper(var1);
        return this;
    }

    public JcePEMDecryptorProviderBuilder setProvider(String var1) {
        this.helper = new NamedJcaJceHelper(var1);
        return this;
    }

    public PEMDecryptorProvider build(final char[] var1) {
        return new PEMDecryptorProvider() {
            public PEMDecryptor get(final String var1x) {
                return new PEMDecryptor() {
                    public byte[] decrypt(byte[] var1xx, byte[] var2) throws PEMException {
                        if (var1 == null) {
                            throw new PasswordException("Password is null, but a password is required");

                        } else {
                            return PEMUtilities.crypt(
                                false,
                                JcePEMDecryptorProviderBuilder.this.helper,
                                var1xx,
                                var1,
                                var1x,
                                var2
                            );
                        }
                    }
                };
            }
        };
    }
}

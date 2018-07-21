package com.yufu.idaas.sdk.init;

import com.yufu.idaas.sdk.constants.YufuSdkRoleConstants;
import com.yufu.idaas.sdk.constants.YufuTokenConstants;
import com.yufu.idaas.sdk.exception.GenerateException;
import com.yufu.idaas.sdk.exception.VerifyException;
import com.yufu.idaas.sdk.exception.YufuInitException;
import com.yufu.idaas.sdk.token.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.yufu.idaas.sdk.constants.YufuSdkRoleConstants.ROLE_IDP;
import static com.yufu.idaas.sdk.constants.YufuSdkRoleConstants.ROLE_SP;

/**
 * Created by shuowang on 2018/5/2.
 */
public class YufuAuth implements IYufuAuth {
    private ITokenGenerator tokenGenerator;
    private ITokenVerifier tokenVerifier;
    private String tenantId;

    private YufuAuth(
        String issuer,
        String keyPath,
        String tenantId,
        String keyFingerPrint
    ) throws YufuInitException {
        this.tenantId = tenantId;
        String keyId = issuer + YufuTokenConstants.KEY_ID_SEPARATOR + keyFingerPrint;
        this.tokenGenerator = new RSATokenGenerator(keyPath, null, issuer, tenantId, keyId);
    }

    private YufuAuth(
        String keyInfo, boolean isFilePath
    ) throws YufuInitException {
        this.tokenVerifier = new RSATokenVerifier(keyInfo, isFilePath);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String generateToken(Map<String, Object> Claims) throws GenerateException {
        return this.tokenGenerator.generate(Claims);
    }

    public URL generateIDPRedirectUrl(Map<String, Object> Claims) throws GenerateException {
        try {

            return new URL((YufuTokenConstants.IDP_TOKEN_CONSUME_URL +
                "?idp_token=" +
                this.tokenGenerator.generate(Claims)).replace("{tenantId}", this.tenantId));
        } catch (MalformedURLException e) {
            throw new GenerateException("Can not generate redirect Url " + e.getMessage());
        }
    }

    public JWT verify(final String id_token) throws VerifyException {
        return this.tokenVerifier.verify(id_token);
    }

    public static class Builder {
        private String privateKeyPath;
        private String publicKeyPath;
        private String publicKeyString;
        private String issuer;
        private String keyFingerPrint;
        private String tenantId;
        private YufuSdkRoleConstants sdkRole;

        public Builder privateKeyPath(String path) {
            this.privateKeyPath = path;
            return this;
        }

        public Builder publicKeyString(String keyString) {
            this.publicKeyString = keyString;
            return this;
        }

        public Builder keyFingerPrint(String keyFingerPrint) {
            this.keyFingerPrint = keyFingerPrint;
            return this;
        }

        public Builder publicKeyPath(String publicKeyPath) {
            this.publicKeyPath = publicKeyPath;
            return this;
        }

        public Builder issuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public Builder tenant(String tnt) {
            this.tenantId = tnt;
            return this;
        }

        public Builder sdkRole(YufuSdkRoleConstants sdkRole) {
            this.sdkRole = sdkRole;
            return this;
        }

        public YufuAuth build() throws YufuInitException {
            if (this.sdkRole == ROLE_SP) {
                if (publicKeyPath != null) {
                    return new YufuAuth(
                        this.publicKeyPath, true
                    );
                } else if (publicKeyString != null) {
                    return new YufuAuth(
                        this.publicKeyString, false
                    );
                } else {
                    throw new YufuInitException("Public Key must be set with SP Role");
                }
            }

            if (this.sdkRole == ROLE_IDP) {
                if (privateKeyPath == null || "".equals(privateKeyPath)) {
                    throw new YufuInitException("Private Key must be set with IDP Role");
                }

                if (keyFingerPrint == null || "".equals(keyFingerPrint)) {
                    throw new YufuInitException("Key fingerPrint must be set with IDP Role");
                }
                return new YufuAuth(
                    this.issuer,
                    this.privateKeyPath,
                    this.tenantId,
                    this.keyFingerPrint
                );
            }

            throw new YufuInitException("SDK Role must be set");
        }
    }

}

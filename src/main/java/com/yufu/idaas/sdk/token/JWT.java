package com.yufu.idaas.sdk.token;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

/**
 * Created by shuowang on 2018/5/2.
 */
@Builder
@Getter
public class JWT {
    /**
     * id of key used to sign token
     */
    private String keyId;

    /**
     * issuer of token, commonly referring to ID of Id/Service Provider
     */
    private String issuer;
    private String audience;
    private String subject;
    private String jwtId;
    private Date expiration;
    private Date issueAt;
    private Date notBefore;

    public Date getExpiration() {
        return (Date) expiration.clone();
    }

    public Date getIssueAt() {
        return (Date) issueAt.clone();
    }

    public Date getNotBefore() {
        return (Date) notBefore.clone();
    }

    /**
     * All claims (registered + custom)
     */
    private Map<String, Object> claims;
}

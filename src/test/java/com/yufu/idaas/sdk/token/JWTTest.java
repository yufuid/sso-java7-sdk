package com.yufu.idaas.sdk.token;

import com.yufu.idaas.sdk.token.JWT;
import org.junit.Test;

import java.util.Date;

/**
 * Created by vegachen on 10/31/18.
 */
public class JWTTest {
    Date now = new Date(System.currentTimeMillis());
    JWT jwt = JWT.builder().expiration(now).notBefore(now).issueAt(now).build();

    @Test
    public void cannotEditExpiration() throws Exception {
        jwt.getExpiration().setTime(System.currentTimeMillis());
        assert jwt.getExpiration().equals(now);
    }

    @Test
    public void cannotEditGetIssueAt() throws Exception {
        jwt.getIssueAt().setTime(System.currentTimeMillis());
        assert jwt.getIssueAt().equals(now);
    }

    @Test
    public void cannotEditGetNotBefore() throws Exception {
        jwt.getNotBefore().setTime(System.currentTimeMillis());
        assert jwt.getNotBefore().equals(now);
    }

}
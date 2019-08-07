package com.yufu.idaas.sdk.token;

import com.google.common.collect.ImmutableList;
import com.yufu.idaas.sdk.exception.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vegachen on 10/29/18.
 */
public class RSATokenVerifierTest {

    private RSATokenVerifier verifier;

    private static String
        longLiveValidToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50IjoiMjk3MjIwIiwiZXhwIjo5OTUzODcxMzY3NywiaWF0IjoxNTI4NzEzMDc3fQ.cSy7Wye3Gl03tu96fpypXJ_WQa0HTMeMgfdzzfFHhHluuak7YdxnYpuybhjyA4pi4HdJRVeermRIuh4e72dpQGkAcX9jem_WKBNCUgFoO7iTGhhb0G4wfv-G0gfE4AKTmdVBWi8SB5JkhTCWZVFkl-kzWUFGDurod2DD-LljBaQ";

    private static String
        expiredValidToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50IjoiMjk3MjIwIiwiZXhwIjoxNTI4NzEzNjc3LCJpYXQiOjE1Mjg3MTMwNzd9.juOba6uJk4tG8tM6biHzThyxoHFigYX3O2FQkOjAyzk_8ZlWX7ijiOPbLaldemZhK2Tcuru8YrP6fhlXMQk5IdZVji0xc_-hc01kEoBHgE2BkQBgtzpCn-VXkthXpLuhBuMGps7V5USpFEDF4tA4Sb3Egu0jSEeeD2GsTz6O2Sw";

    private static String
        tokenWithWrongSign =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50IjoiMjk3MjIwIiwiZXhwIjo5OTUzODcxMzY3NywiaWF0IjoxNTI4NzEzMDc3fQ.cSy7Wye3Gl03tu96fpypXJ_WQa0HTMeMgfdzzfFHhHluuak7YdxnYpuybhjyA4pi4HdJRVeermRIuh4e72dpQGkAcX9jem_WKBNCUgFoO7iTGhhb0G4wfv-G0gfE4AKTmdV";

    private static String
        tooEarlyToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50IjoiMjk3MjIwIiwiZXhwIjo5OTUzODcxMzY3NywibmJmIjo5OTUzODcxMzY3NywiaWF0IjoxNTI4NzEzMDc3fQ.FqfJkZMcukMfHCQ4xsZE4L0X6OCM-opv5golC6G9LEu0AoYV7IXUtFUZE80Nlsjj0Mak1zqaAzZwSQpBrNfzbyxHrc8itc01frbYjyYP0eAeIz5gHzYpLjGHGLv4MDSp6PlHOnRQx3gSrZeYTmFCmKZ7bmdmzrO5K506VIjH44M";
    private static String
        PUBLIC_KEY_STR = "-----BEGIN PUBLIC KEY-----\n" +
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDu4W0SAH5EiYxeYcw+R52MNQvX\n" +
        "8yKXrzuQjYwHYvHHX+HHGI4Xkj5ZUOIGCT6C5nZjbKq+yKXQ63EU+PdPZjlyPimQ\n" +
        "5r0exitnVWnbs/m+JaJIG/urEzJkmBI6nhc9xcQGaoafWp+AiXVscGN5sC+gD+77\n" +
        "gClNOyvznotnKqihUQIDAQAB\n" +
        "-----END PUBLIC KEY-----";

    private void setUpFileKeyCorrectly() throws Exception {
        verifier = new RSATokenVerifier(
            "297220", "yufu", ImmutableList.of("test"),
            this.getClass().getResource("").getPath() + "testPublicKey.pem",
            true
        );
    }

    @Test
    public void verifyWithStringPublicKey() throws Exception {
        // Override verifier
        verifier = new RSATokenVerifier(
            "297220", "yufu", ImmutableList.of("test"),
            PUBLIC_KEY_STR,
            false
        );
        verifyWithFilePublicKey();
    }

    @Test(expected = YufuInitException.class)
    public void failedWithoutKey() throws Exception {
        // Override verifier
        verifier = new RSATokenVerifier(
            "297220", "yufu", ImmutableList.of("test"),
            "",
            true
        );
    }

    @Test(expected = YufuInitException.class)
    public void failedWithKeyInBadFormat() throws Exception {
        // Override verifier
        verifier = new RSATokenVerifier(
            "297220", "yufu", ImmutableList.of("test"),
            PUBLIC_KEY_STR.substring(5),
            false
        );
    }

    @Test
    public void verifyWithFilePublicKey() throws Exception {
        setUpFileKeyCorrectly();
        JWT jwt = verifier.verify(longLiveValidToken);
        Assert.assertEquals("yufu", jwt.getIssuer());
        Assert.assertEquals("test@yufu.com", jwt.getSubject());
        Assert.assertEquals("297220", jwt.getClaims().get("tnt"));
    }

    @Test(expected = BaseVerifyException.class)
    public void verifyWithBlankToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify("    ");
    }

    @Test(expected = BaseVerifyException.class)
    public void verifyWithEmptyToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify("");
    }

    @Test(expected = BaseVerifyException.class)
    public void verifyWithNullToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify(null);
    }

    @Test(expected = TokenExpiredException.class)
    public void verifyWithExpiredToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify(expiredValidToken);
    }

    @Test(expected = TokenTooEarlyException.class)
    public void verifyWithTooEarlyToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify(tooEarlyToken);
    }

    @Test(expected = InvalidSignatureException.class)
    public void verifyWithInvalidSignatureToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify(tokenWithWrongSign);
    }

    @Test(expected = InvalidTokenException.class)
    public void verifyWithBadToken() throws Exception {
        setUpFileKeyCorrectly();
        verifier.verify(longLiveValidToken.replaceAll("eyJ", "ej"));
    }
}
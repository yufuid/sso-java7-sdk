package com.yufu.idaas.sdk.token;

import com.google.common.collect.ImmutableList;
import com.yufu.idaas.sdk.exception.*;
import org.junit.Assert;
import org.junit.Test;

import static com.yufu.idaas.sdk.constants.YufuTokenConstants.TENANT_ID_KEY;

/**
 * Created by vegachen on 10/29/18.
 */
public class RSATokenVerifierTest {

    private RSATokenVerifier verifier;

    private static String
        longLiveValidToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50X2lkIjoidG4teXVmdSIsImV4cCI6OTk1Mzg3MTM2NzcsImlhdCI6MTUyODcxMzA3N30.jcSgJ8Mh19VlYLEcYAgE1v3vQzyuE3MTgfYLewoZ1W57M5Vb-MB5OVb0W-kuJ52pNUYffSax7KaBsjAsIdEVZHajgWIKNO4xeS_vT1nlksbpCchS6F-8vmpxybIBblLpt19_m2Ii7MXNHMCkn2-XSajE_r6Ln7F95TWyVUdRkmI";

    private static String
        expiredValidToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50X2lkIjoidG4teXVmdSIsImV4cCI6MTUyODcxMzY3NywiaWF0IjoxNTI4NzEzMDc3fQ.x-HS8R_C8Zm1QvQ-1YuLQ1KgGZpMiOIgUgx90zQzq0VVtnC81Ialmcxsa6c7y2_Cc0yWoMFcB8z2WGKF_2S4_fwGL_YRVow5Io4VEIveWNQPmUDUHOvJd2wkTZBy9b5MxROjoskDX4USYq5kq146SoVKDgW95flvRgxS-J2iJGI";
    private static String
        tokenWithWrongSign =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50X2lkIjoidG4teXVmdSIsImV4cCI6OTk1Mzg3MTM2NzcsImlhdCI6MTUyODcxMzA3N30.Na0Ckq2nI1c3uel3jAVoxhdG3fMQ6zrGbIAm1azATFcFl_f6UFP8EvkSA3W0jhWDVW_kqpDlGzAVWmDyBPbBG_F68WPtW6B8o40zcpu52Sm4OR3HvNtDqFAanKjFOZkm9VwZe2sqJwqrKRXx6tPZthj7DWIhjqhgdBHvG9lTssw";

    private static String
        tooEarlyToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50X2lkIjoidG4teXVmdSIsImV4cCI6OTk1Mzg3MTM2NzcsIm5iZiI6OTk1Mzg3MTM2NzcsImlhdCI6MTUyODcxMzA3N30.ZVxLaE92IIf_0ZJnYTA7I8NdnjqrmybbmRQMBisEDadK5N51PvIft2wjAtMYOqi1MBiK9qlan82PsbbQG_Pl6EM3Vhw1wJqhgda65Je_MbxE_knpN8INP-TRS9FyI8N3oZeoe52SIuyOYSAAOOxfiaELUM7tYP_qW140ZtJ2LbU";

    private static String
        PUBLIC_KEY_STR = "-----BEGIN PUBLIC KEY-----\n" +
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDu4W0SAH5EiYxeYcw+R52MNQvX\n" +
        "8yKXrzuQjYwHYvHHX+HHGI4Xkj5ZUOIGCT6C5nZjbKq+yKXQ63EU+PdPZjlyPimQ\n" +
        "5r0exitnVWnbs/m+JaJIG/urEzJkmBI6nhc9xcQGaoafWp+AiXVscGN5sC+gD+77\n" +
        "gClNOyvznotnKqihUQIDAQAB\n" +
        "-----END PUBLIC KEY-----";

    private void setUpFileKeyCorrectly() throws Exception {
        verifier = new RSATokenVerifier(
            "tn-yufu", null, "yufu", ImmutableList.of("test"),
            this.getClass().getResource("").getPath() + "testPublicKey.pem",
            true
        );
    }

    @Test
    public void verifyWithStringPublicKey() throws Exception {
        // Override verifier
        verifier = new RSATokenVerifier(
            "tn-yufu", null, "yufu", ImmutableList.of("test"),
            PUBLIC_KEY_STR,
            false
        );
        verifyWithFilePublicKey();
    }

    @Test(expected = YufuInitException.class)
    public void failedWithoutKey() throws Exception {
        // Override verifier
        verifier = new RSATokenVerifier(
            "tn-yufu", null, "yufu", ImmutableList.of("test"),
            "",
            true
        );
    }

    @Test(expected = YufuInitException.class)
    public void failedWithKeyInBadFormat() throws Exception {
        // Override verifier
        verifier = new RSATokenVerifier(
            "tn-yufu", null, "yufu", ImmutableList.of("test"),
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
        Assert.assertEquals("tn-yufu", jwt.getClaims().get(TENANT_ID_KEY));
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
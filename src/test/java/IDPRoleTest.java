import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yufu.idaas.sdk.constants.SDKRole;
import com.yufu.idaas.sdk.exception.YufuInitException;
import com.yufu.idaas.sdk.init.IYufuAuth;
import com.yufu.idaas.sdk.init.YufuAuth;
import com.yufu.idaas.sdk.token.JWT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.yufu.idaas.sdk.constants.YufuTokenConstants.APP_INSTANCE_ID_KEY;
import static com.yufu.idaas.sdk.constants.YufuTokenConstants.TENANT_ID_KEY;
import static com.yufu.idaas.sdk.constants.YufuTokenConstants.TENANT_NAME_KEY;

/**
 * Created by shuowang on 2018/6/4.
 */
public class IDPRoleTest {

    private IYufuAuth yufuAuth;

    @Before
    public void setup() throws YufuInitException {

        String keyPath = IDPRoleTest.class.getResource("").getPath() + "keys/testPrivateKey.pem";

        yufuAuth = YufuAuth.builder()
            .issuer("testIssuer")
            .sdkRole(SDKRole.IDP)
            .tnt("testTenant")
            .privateKeyPath(keyPath)
            .keyFingerPrint("2bf935821aa33e693d39ab569ba557aa0af8e02e")
            .build();
    }

    @Test
    public void testGenerateToken() throws Exception {

        Map<String, Object> claims = new HashMap<String, Object>() {
            {
                put(APP_INSTANCE_ID_KEY, "testAppInstanceId");
                put("customFieldsKey", "customFieldsValue");
            }
        };
        URL url = yufuAuth.generateIDPRedirectUrl(claims);

        String query = url.getQuery();
        String idpToken = query.substring(query.indexOf("idp_token=") + "idp_token=".length());
        verifyClaim(idpToken);
    }

    @Test
    public void testGenerateUrlJWT() throws Exception {
        JWT jwt = JWT.builder().claims(new HashMap<String, Object>() {
            {
                put(APP_INSTANCE_ID_KEY, "testAppInstanceId");
                put("customFieldsKey", "customFieldsValue");
            }
        }).build();
        URL url = yufuAuth.generateIDPRedirectUrl(jwt);
        String query = url.getQuery();
        String idpToken = query.substring(query.indexOf("idp_token=") + "idp_token=".length());
        verifyClaim(idpToken);
    }

    @Test
    public void testGenTokenJWT() throws Exception {
        JWT jwt = JWT.builder().claims(new HashMap<String, Object>() {
            {
                put(APP_INSTANCE_ID_KEY, "testAppInstanceId");
                put("customFieldsKey", "customFieldsValue");
            }
        }).build();
        verifyClaim(yufuAuth.generateToken(jwt));
    }

    public void verifyClaim(String jwtStr) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(jwtStr);
        Assert.assertEquals(
            "testIssuer###2bf935821aa33e693d39ab569ba557aa0af8e02e",
            signedJWT.getHeader().getKeyID()
        );

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        Assert.assertEquals("testAppInstanceId", claimsSet.getStringClaim(APP_INSTANCE_ID_KEY));
        Assert.assertEquals("testIssuer", claimsSet.getIssuer());
        Assert.assertEquals("testTenant", claimsSet.getStringClaim(TENANT_NAME_KEY));
    }
}

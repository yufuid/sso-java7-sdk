import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yufu.idaas.sdk.constants.YufuSdkRoleConstants;
import com.yufu.idaas.sdk.exception.GenerateException;
import com.yufu.idaas.sdk.exception.YufuInitException;
import com.yufu.idaas.sdk.init.IYufuAuth;
import com.yufu.idaas.sdk.init.YufuAuth;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.yufu.idaas.sdk.constants.YufuTokenConstants.APP_INSTANCE_ID_KEY;

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
            .sdkRole(YufuSdkRoleConstants.ROLE_IDP)
            .tenant("testTenant")
            .privateKeyPath(keyPath)
            .keyFingerPrint("2bf935821aa33e693d39ab569ba557aa0af8e02e")
            .build();
    }

    @Test
    public void testGenerateToken() throws GenerateException, ParseException {

        Map<String, Object> claims = new HashMap<String, Object>() {
            {
                put(APP_INSTANCE_ID_KEY, "testAppInstanceId");
                put("customFieldsKey", "customFieldsValue");
            }
        };
        URL url = yufuAuth.generateIDPRedirectUrl(claims);

        String query = url.getQuery();
        String idpToken = query.substring(query.indexOf("idp_token=") + "idp_token=".length());
        SignedJWT signedJWT = SignedJWT.parse(idpToken);
        Assert.assertEquals(
            "testIssuer###2bf935821aa33e693d39ab569ba557aa0af8e02e",
            signedJWT.getHeader().getKeyID()
        );

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        Assert.assertEquals("testAppInstanceId", claimsSet.getStringClaim(APP_INSTANCE_ID_KEY));
        Assert.assertEquals("testIssuer", claimsSet.getIssuer());
        Assert.assertEquals("testTenant", claimsSet.getStringClaim("tnt"));
    }

}

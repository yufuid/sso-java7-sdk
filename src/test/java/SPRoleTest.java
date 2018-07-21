import com.yufu.idaas.sdk.constants.YufuSdkRoleConstants;
import com.yufu.idaas.sdk.exception.YufuInitException;
import com.yufu.idaas.sdk.init.IYufuAuth;
import com.yufu.idaas.sdk.init.YufuAuth;
import com.yufu.idaas.sdk.token.JWT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shuowang on 2018/6/11.
 */
public class SPRoleTest {

    private IYufuAuth yufuAuth;
    //有效期到5000年
    private String
        testToken =
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0Iiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ5dWZ1IiwidG50IjoiMjk3MjIwIiwiZXhwIjo5OTUzODcxMzY3NywiaWF0IjoxNTI4NzEzMDc3fQ.cSy7Wye3Gl03tu96fpypXJ_WQa0HTMeMgfdzzfFHhHluuak7YdxnYpuybhjyA4pi4HdJRVeermRIuh4e72dpQGkAcX9jem_WKBNCUgFoO7iTGhhb0G4wfv-G0gfE4AKTmdVBWi8SB5JkhTCWZVFkl-kzWUFGDurod2DD-LljBaQ";

    @Before
    public void setup() throws YufuInitException {
        String keyPath = SPRoleTest.class.getResource("").getPath() + "keys/testPublicKey.pem";

        yufuAuth = YufuAuth.builder()
            .sdkRole(YufuSdkRoleConstants.ROLE_SP)
            .publicKeyPath(keyPath)
            .build();
    }

    @Test
    public void testVerifyToken() throws Exception {
        JWT jwt = yufuAuth.verify(testToken);
        Assert.assertEquals("yufu", jwt.getIssuer());
        Assert.assertEquals("test@yufu.com", jwt.getSubject());
        Assert.assertEquals("297220", jwt.getClaims().get("tnt"));
    }
}

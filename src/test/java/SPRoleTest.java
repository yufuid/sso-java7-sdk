import com.yufu.idaas.sdk.constants.SDKRole;
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
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0YXVkIiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ0ZXN0aXNzIiwidG50X2lkIjoidG4teXVmdSIsImV4cCI6OTk1Mzg3MTM2NzcsImlhdCI6MTUyODcxMzA3N30.WxdqMa4hI_izBwsg8hcF0_Y6Y56-kLXFLS0SS6cqwA3Zp60pPEQqdaf8XG4hVD-vB6I39TrsTLH7stRNnd1_PkciFryIwht2f4AtSVQlM1HvQPgVhSYJS-Em-ZbIyzQBO7yY9cS0vwmKirspczGkQQKlV52iapjcyoZvo-Fn3PQ";

    @Before
    public void setup() throws YufuInitException {
        String keyPath = SPRoleTest.class.getResource("").getPath() + "keys/testPublicKey.pem";

        yufuAuth = YufuAuth.builder()
            .tenant("tn-yufu")
            .issuer("testiss")
            .sdkRole(SDKRole.SP)
            .audience("testaud")
            .publicKeyPath(keyPath)
            .build();
    }

    @Test
    public void testVerifyToken() throws Exception {
        JWT jwt = yufuAuth.verify(testToken);
        Assert.assertEquals("test@yufu.com", jwt.getSubject());
    }
}

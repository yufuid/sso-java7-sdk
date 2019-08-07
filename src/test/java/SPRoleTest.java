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
        "eyJraWQiOiJ0ZXN0a2V5aWQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ0ZXN0YXVkIiwic3ViIjoidGVzdEB5dWZ1LmNvbSIsInN0eXBlIjoiZW1haWwiLCJpc3MiOiJ0ZXN0aXNzIiwidG50IjoieXVmdSIsImV4cCI6OTk1Mzg3MTM2NzcsImlhdCI6MTUyODcxMzA3N30.BZudnPe6y4kLbKaxfL85yColdxplc2BUoHDkxyO6ezD0ukEuCNV8P7pUnoO_APf8jdclcvTNFlRbVf97Tk5K6jvtUEnvCbRCz2c5UvSzTAxzmanci3ixFDTBeWFpRm6X-GErY9LbCvBEiTciNUhxSFq04g38f2r18Zr3cuQlmhk";

    @Before
    public void setup() throws YufuInitException {
        String keyPath = SPRoleTest.class.getResource("").getPath() + "keys/testPublicKey.pem";

        yufuAuth = YufuAuth.builder()
            .tenant("yufu")
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

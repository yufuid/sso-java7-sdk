玉符单点登录 SDK
======
玉符SDK集成了签署和验证JWT令牌的功能，使得身份提供者（IDP）和服务提供者（SP）只需要用很少的工作量就可以快速将玉符提供的单点登录等功能集成到现有的服务中。

## 单点登录SDK简介

* 作为服务提供者（SP）,可以使用玉符SDK验证JWT令牌的有效性（包括有效期、签名等），验证成功后可取出token中字段进行相应的鉴权。
* 作为身份提供者（IDP）,可以使用玉符SDK灵活进行参数配置，并生成带有token的跳转url，进行单点登录功能。


## 集成SDK
使用库方法
  * 将`javasdk-{version}.jar`文件添加到项目的library中，确保路径配置正确。
  * 若项目使用了maven，则在pom.xml中添加如下dependency
  ```
  <dependency>
       <groupId>com.yufu.idaas</groupId>
       <artifactId>javasdk</artifactId>
       <version>{version}</version>
       <scope>system</scope>
       <systemPath>{jar包路径}</systemPath>
  </dependency>
  ```

## 使用SDK
**服务提供者(SP)**
1. 使用必要信息初始化SDK实例。注：开发阶段可以使用测试公钥`src/test/resources/keys/testPublicyKey.pem`
```
 //方法1:使用公钥路径初始化
 IYufuAuth serviceProvider = YufuAuth.builder()
             .sdkRole(SDKRole.SP)
             .publicKeyPath({keyPath})
             .tenant({tenant})
             .issuer({issuer})
             .audience({audience})
             .build();
             
 //方法2:使用公钥字符串初始化
 IYufuAuth serviceProvider = YufuAuth.builder()
             .sdkRole(SDKRole.SP)
             .publicKeyString({keyString})
             .tenant({tenant})
             .issuer({issuer})
             .audience({audience})
             .build();
```

2. 调用第1步生成的实例`verify`验证单点登录url里的token（自动验证有效期、Issuer、Audience、签名等），如通过，说明该令牌来受信任的有效租户(企业/组织)，样例
```
  String idToken = getIdToken();                // 从URL中获得 ID token(queryParam的key为'id_token')
  JWT claims = serviceProvider.verify(idToken);    // 使用验证玉符SDK实例进行验证, 如果成功会返回包含用户信息的对象，失败则会产生授权错误的异常
  String username = claims.getSubject();        // 用户名称
  String tenant = claims.getClaims().get("tnt"); // 租户名称
```

3. 根据第2步获取的用户信息，服务提供商(SP)在token验证通过后，取出token中用户名称等必要信息，进行相应登录鉴权，否则提示用户登录失败

**身份提供者（IDP)**
1. 使用必要信息初始化SDK（必要参数在玉符初始化后获取）
```
  IYufuAuth idProvider = YufuAuth.builder()
            .issuer("testIdpId")
            .sdkRole(SDKRole.IDP)
            .tenant("testTenant")
            .privateKeyPath(keyPath)
            .keyFingerPrint("testFingerprint")
            .build();
```
2. 传入必要和自定义参数，生成跳转url
```
  Map<String, Object> claims = new HashMap<String, Object>() {
            {
                put(APP_INSTANCE_ID_KEY, "testAppInstanceId");
                put({customFieldsKey}, {customFieldsValue});
            }
        };
  URL url = idProvider.generateIDPRedirectUrl(claims);
```
3. 成功生成url后，进行302重定向跳转，若以上参数配置无误，则可单点登录至相应的应用。
 

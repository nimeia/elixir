---

typora-copy-images-to: img
typora-root-url: ./
---

[toc]

# 架构说明

## 集成说明

| 组件                               | 功能说明                                                  |
| ---------------------------------- | --------------------------------------------------------- |
| JDK 8                              | JDK 最低依赖                                              |
| spring boot 2.4.1                  | 基础架构                                                  |
| spring cloud 2020.0.0              | 微服务基础架构                                            |
| spring cloud alibaba 2.2.1.RELEASE | 微服务基础架构                                            |
| druid datasource 1.2.4             | 数据源，主要集成监控功能，方便问题排除与sql 优化          |
| dynamic-datasource 3.2.1           | 数据库读写分离支持                                        |
| mybatis.plus 3.4.1                 | 支持集成 mybatis ，默认为jpa                              |
| mapstruct  1.4.1.Final             | 用于对象拷贝，替换bean.copy等方法，减少潜在漏洞，安全问题 |
| spring-boot-admin 2.3.1            | spring boot admin , 对spring boot 程序进行监控            |
| ok http                            | 基础 http client                                          |
| retrofit 2.9.0                     | restful http client                                       |
| spring-boot.retrofit 2.2.5         | restful http client                                       |
| p6spy 3.9.1                        | 开发辅助工具，用于打印带参数的sql                         |
| myexcel 3.10.0                     | excel 工具类                                              |
| hutool-all 5.5.2                   | 通用工具类                                                |
| easy-captcha 1.6.2                 | 图片校验码                                                |
| UserAgentUtils 1.21                | 浏览器 user agent 工具                                    |
| jasypt-spring-boot-starter 3.0.3   | 加解密工具                                                |
| sentry 3.2.0                       | 错误统计收集                                              |
| skywalking 8.3.0                   | apm 支持                                                  |
| nacos 0.2.7                        | 配置，注册中心                                            |
| xxl-job 2.2.0                      | 定时任务                                                  |
| lombok 1.18.16                     | 开发辅助                                                  |

## 项目结构说明

### 结构介绍

| 主项目       | 子项目      | 功能说明                                                     |
| ------------ | ----------- | ------------------------------------------------------------ |
| bom          |             | maven 依赖定义                                               |
|              | bom-api     | api定义依赖                                                  |
|              | bom-base    | 基础功能依赖定义                                             |
|              | bom-restful | restful 项目依赖定义                                         |
|              | bom-monitor | 监控管理项目以来定义                                         |
| api          |             | 对外api定义，生成jar,文档会对外提供，应该尽量减少依赖        |
|              | api-base    | 基础api工具类                                                |
|              | api-open    | 外部系统定义api                                              |
| core         |             | 通过封装定义                                                 |
| build-helper |             | 打包辅助                                                     |
| webui        |             | 前端项目                                                     |
| doc          |             | 项目文档                                                     |
| business     |             | 业务系统模块                                                 |
|              | model       | 系统中数据模型定义，常见为jpa ,mybatis 映射类，以jar 方式发布 |
|              | service     | 系统中业务服务定义，以jar 方式发布                           |
|              | admin       | 可选，定义为系统的管理后台，引用service与model,model以scope runtime方式导入，避免错误使用 |
|              | app         | 系统的主功能项目，引用service与model,model以scope runtime方式导入，避免错误使用 |
|              | gateway     | 可选，对外部系统提供接口项目，引用service与model,model以scope runtime方式导入，避免错误使用 |
|              | jobs        | 系统中的定时任务，引用service与model,model以scope runtime方式导入，避免错误使用 |

### 项目管理组织

1. 每个主项目对应git中的一个厂库
2. bom、core 模块只允许部分开发修改，控制错误的变更
3. 非微服务系统时，business 为系统的主模块
4. 微服务系统时，business 应该定义为不同的微服务模块，同时，admin,gateway,jobs 从其中分离出来作为主项目
5. doc 为文档管理项目，建议使用`md`格式，编辑器建议使用 `typora` 方便其它同事合作
6. webui 为前端项目

## MAVEN 配置说明

### YAML 与 pom.xml 交互

```xml
<include>**/application*.yml</include>
<include>**/application*.yaml</include>
<include>**/bootstrap*.yml</include>
<include>**/bootstrap*.yaml</include>
<include>**/application*.properties</include>
<include>**/log4j.xml</include>
<include>**/log4j2.xml</include>
<include>**/log4j2-spring.xml</include>
```

配置文件中支持通过`@@` 号引用 `pom.xml` 中的属性

### MapStruct  与  lombok  插件配置

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <parameters>true</parameters>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${version.lombok}</version>
            </path>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${version.org.mapstruct}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## 日志优化说明

### 日志格式调整

![image-20210113150601524](/img/image-20210113150601524.png)

日志中增加`hostname` `appname` `traceid` 方便在微服务的场景方便日志收集分类

![image-20210113150817696](/img/image-20210113150817696.png)

日志配置中与常见的 `spring boot` 有调整，`name` 都指定日志文件的名称，`path` 设置日志的保存目录，两者拼接为日志的完整路径

`@project.artifactId@` 表示该项目的`pom.xml`中取值

开发时需要输出`sql`时可以调整`p6spy`的日志级别为`debug`,同时需要调整`jdbc` 的驱动配置

![image-20210113151756631](/img/image-20210113151756631.png)

### 错误日志收集

系统集成sentry 对错误日志进行收集，配置如下 

![image-20210113170452269](/img/image-20210113170452269.png)

log4j2 配置如下

![image-20210113170538927](/img/image-20210113170538927.png)

错误日志统计

![image-20210113170744426](/img/image-20210113170744426.png)

![image-20210113170818734](/img/image-20210113170818734.png)

## 数据源相关配置

### 数据库版本管理

系统使用`flyway`进行数据库配置管理，可以通过配置maven plugin 进行更新，默认spring boot 的自动更新配置关闭

```xml
<!--数据库版本管理-->
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>
    </dependencies>
</plugin>
```

```xml
<properties>
    <flyway.url>jdbc:mysql://127.0.0.1:3306/app?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;zeroDateTimeBehavior=CONVERT_TO_NULL&amp;useSSL=false&amp;serverTimezone=CTT</flyway.url>
    <flyway.user>app</flyway.user>
    <flyway.password>app</flyway.password>
</properties>
```

### SQL 日志

​	详见日志格式相关说明

### 配置文件敏感信息加密

* 配置密码

![image-20210113152336213](/img/image-20210113152336213.png)

​	在生产环境中可以通过环境变量注入对应的密码，配置文件中配置为对应的密文，官方以 `ENC()`标记

![image-20210113153423511](/img/image-20210113153423511.png)

* 程序中加解密

  当程序中需要对部分程序手动加密时，可以通过注入 `StringEncryptor` 进行加解密处理

### DRUID 数据库监控说明

druid 数据源默认配置开启监控，配置如下

![image-20210113153731908](/img/image-20210113153731908.png)

可以通过 `/druid/` 路径与 `admin` 用户访问

![image-20210113164748977](/img/image-20210113164748977.png)

可以通过运行sql 进行缓存优化，索引优化等

## MAPSTRUCT 使用

`mapstruct` 主要用于两个`vo`中的值转换，其优势时不使用反射，而是直接生成`set get`方法，性能会比 `bean.copy` 好。同时也减少了反射拷贝的不确定性

### 样例

```java
@Mapper
public interface UserDtoMapper {
    UserDtoMapper mapper = Mappers.getMapper(UserDtoMapper.class);

    UserDto toUserDto(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toUserEntity(UserDto userDto);

    /**
     * 新建对象
     * @param userDto
     * @return
     */
    UserEntity initUserEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    void mergeToUserEntity(UserDto userDto, @MappingTarget UserEntity userEntity);
}
```

### 调用样例

```java
UserEntity userEntity = userRepos.getOne(userDto.getId());
UserDtoMapper.mapper.mergeToUserEntity(userDto, userEntity);
userRepos.save(userEntity);
```

## `CAPTCHA`校验码集成

系统使用 `easy-captcha` 集成校验码功能，通过spring security 集成，没有单独使用controller

### 样例代码

```java
{
    if (!securityProperties.getValidCodeEnabled()) {
        filterChain.doFilter(request, response);
        return;
    }

    String requestUrl = request.getRequestURI();
    if (StringUtil.equalsIgnoreCase(requestUrl, securityProperties.getValidCodeImgUrl())) {
        // 使用gif验证码
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 4);
        CaptchaUtil.out(gifCaptcha, request, response);
        return;
    } else {
        if (StringUtil.equalsIgnoreCase(requestUrl, securityProperties.getLoginProcessUrl())) {
            String validateCode = request.getParameter("validateCode");
            if (!CaptchaUtil.ver(validateCode, request)) {
                CaptchaUtil.clear(request);  // 清除session中的验证码
                ResponseUtils.jsonResponse(response,
                        new ApiSimpleResponse<String>()
                                .message("validateCode not right!")
                                .code(ApiSimpleResponse.RESPONSE_CODE_SUCCESS)
                                .system("app")
                                .data("")
                                .success(true)
                                .requestId(IdUtil.objectId())
                                .businessMessage("验证码不正确"));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
```

## HTTP CLIENT 配置

系统中`resttemplate` 与 `retrofit` 底层调整为 `okhttp` ,其中okhttp 增加打印调用返回报文功能，接口调用用时日志

```java
{
        log.info("--config the http client log--");
        return new RestTemplateCustomizer() {
            @Override
            public void customize(RestTemplate restTemplate) {

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Long start = System.currentTimeMillis();
                                try {
                                    return chain.proceed(chain.request());
                                } finally {
                                    log.info("call api: {} ,use {} ms", chain.request().url().toString(), (System.currentTimeMillis() - start));
                                }
                            }
                        });
                if (restTemplateProperties.getLogContext()) {
                    builder.addInterceptor(loggingInterceptor);
                }
                OkHttpClient client = builder.build();

                OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(client);

                restTemplate.setRequestFactory(okHttp3ClientHttpRequestFactory);

            }
        };
    }
```
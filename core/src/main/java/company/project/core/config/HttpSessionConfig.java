package company.project.core.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import company.project.core.config.properties.HazelcastProperties;
import company.project.core.config.properties.OpenApiProperties;
import company.project.core.config.springsecurity.CustomSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author huang
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "core.hazelcast", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(value = {HazelcastProperties.class})
public class HttpSessionConfig {

    public HttpSessionConfig(){
        log.info("...HttpSessionConfig...");
    }

    @Autowired
    HazelcastProperties hazelcastProperties;

    @Autowired
    ServerProperties serverProperties;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        String[] hazelcastIps = hazelcastProperties.getHazelcastIps();
        String appName = hazelcastProperties.getAppName();
        Config config = new Config();

        config.setClusterName(appName + "-session");
        if (hazelcastIps == null || hazelcastIps.length == 0) {
            config.getNetworkConfig().setPort(0);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
        } else {
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            TcpIpConfig tcpIpConfig = config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
            for (String hazelcastIp : hazelcastIps) {
                tcpIpConfig.addMember(hazelcastIp);
            }
        }

//        Long sessionMaxAge =  serverProperties.getServlet().getSession().getCookie().getMaxAge().getSeconds();
        AttributeConfig attributeConfig = new AttributeConfig()
                .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());
        config.getMapConfig(Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .setMaxIdleSeconds(3600)
                .setBackupCount(2)
                .setReadBackupData(true)
                //.setAsyncBackupCount(1)
                .addAttributeConfig(attributeConfig).addIndexConfig(
                new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));
        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);

        ReplicatedMapConfig replicatedMapConfig = config.getReplicatedMapConfig(CustomSecurityMetadataSource.SECURITY_METADATA_SOURCE_MAP);
        replicatedMapConfig.setInMemoryFormat(InMemoryFormat.OBJECT);
        replicatedMapConfig.setStatisticsEnabled(true);

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

//    @Bean
//    HttpSessionIdResolver defaultHttpSessionIdResolver(@Autowired CookieSerializer cookieSerializer) {
//
//        HeaderHttpSessionIdResolver headerHttpSessionIdResolver = HeaderHttpSessionIdResolver.xAuthToken();
//
//        CookieHttpSessionIdResolver cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
//        cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
//
//        return new HttpSessionIdResolver() {
//            @Override
//            public List<String> resolveSessionIds(HttpServletRequest httpServletRequest) {
//                List<String> strings = headerHttpSessionIdResolver.resolveSessionIds(httpServletRequest);
//                if (strings == null || strings.size() == 0) {
//                    return cookieHttpSessionIdResolver.resolveSessionIds(httpServletRequest);
//                }
//                return strings;
//            }
//
//            @Override
//            public void setSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) {
//                headerHttpSessionIdResolver.setSessionId(httpServletRequest, httpServletResponse, s);
//            }
//
//            @Override
//            public void expireSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//
//            }
//        };
//    }
}

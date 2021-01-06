package company.project.app.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author huang
 */
@Configuration
public class HttpSessionConfig {

    @Value("${hazelcast.ips:}")
    private String[] hazelcastIps;

    @Value("${spring.application.name}")
    String appName;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();

        config.setClusterName(appName + "-session");

        config.getNetworkConfig().setPort(0);
        if (hazelcastIps == null || hazelcastIps.length == 0) {
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
        } else {
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            TcpIpConfig tcpIpConfig = config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
            for (String hazelcastIp : hazelcastIps) {
                tcpIpConfig.addMember(hazelcastIp);
            }
        }

        AttributeConfig attributeConfig = new AttributeConfig()
                .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());
        config.getMapConfig(Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addAttributeConfig(attributeConfig).addIndexConfig(
                new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));
        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    HttpSessionIdResolver httpSessionIdResolver(){
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

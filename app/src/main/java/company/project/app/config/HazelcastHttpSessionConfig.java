package company.project.app.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSession;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.HazelcastSessionSerializer;
import org.springframework.session.hazelcast.PrincipalNameExtractor;

/**
 *
 * @author huang
 */
@Configuration
public class HazelcastHttpSessionConfig {

    @Value("${hazelcast.ips:}")
    private String[] hazelcastIps;

    @Value("${spring.application.name}")
    String appName;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();

        config.setClusterName(appName+"-session");

        if (hazelcastIps == null || hazelcastIps.length == 0) {
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
        } else {
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            TcpIpConfig tcpIpConfig = config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
            for (String hazelcastIp : hazelcastIps) {
                tcpIpConfig.addMember(hazelcastIp);
            }
        }

        MapConfig sessionMapConfig = config.getMapConfig(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME);
//        AttributeConfig attributeConfig = new AttributeConfig()
//                .setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
//                .setExtractorClassName(PrincipalNameExtractor.class.getName());
//        sessionMapConfig.addAttributeConfig(attributeConfig);

        SerializerConfig serializerConfig = new SerializerConfig();
        serializerConfig.setImplementation(new HazelcastSessionSerializer()).setTypeClass(MapSession.class);
        config.getSerializationConfig().addSerializerConfig(serializerConfig);
        return Hazelcast.newHazelcastInstance(config);
    }
}

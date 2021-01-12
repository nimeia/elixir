package company.project.core.config.springsecurity;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.util.StringUtil;
import com.hazelcast.replicatedmap.ReplicatedMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * 自定义权限加载
 *
 * @author huang
 */
@Component
@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    public static final String SECURITY_METADATA_SOURCE_MAP = "SecurityMetaDataSourceMap";

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private PathMatcher pathMatcher;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        log.info("===========getAttributes: " + o);
        ReplicatedMap<String, Collection<ConfigAttribute>> replicatedMap =
                hazelcastInstance.getReplicatedMap(SECURITY_METADATA_SOURCE_MAP);

        // Object中包含用户请求request
        String url = ((FilterInvocation) o).getRequestUrl();
        Iterator<String> iterator = replicatedMap.keySet().iterator();
        while (iterator.hasNext()) {
            String resURL = iterator.next();
            if (!StringUtil.isNullOrEmpty(resURL) && pathMatcher.match(resURL, url)) {
                return replicatedMap.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        log.info("=============getAllConfigAttributes");
        Collection<ConfigAttribute> values = new ArrayList<>();
        ReplicatedMap<Object, Collection<ConfigAttribute>> replicatedMap = hazelcastInstance.getReplicatedMap(SECURITY_METADATA_SOURCE_MAP);
        for (Collection<ConfigAttribute> value : replicatedMap.values()) {
            values.addAll(value);
        }
        return values;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("===============" + clazz);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("load system permission...");
        ReplicatedMap<Object, Object> replicatedMap = hazelcastInstance.getReplicatedMap(SECURITY_METADATA_SOURCE_MAP);
        if (replicatedMap == null || replicatedMap.size() == 0) {
            //todo : add more logic control
            List<CustomMetaDataSource> metadataSources = jdbcTemplate.query("select * from permission ", BeanPropertyRowMapper.newInstance(CustomMetaDataSource.class));
            Map hashMap = new HashMap();
            if (metadataSources != null && metadataSources.size() > 0) {
                for (CustomMetaDataSource metadataSource : metadataSources) {
                    Collection<ConfigAttribute> configAttributes = new ArrayList<>();
                    SecurityConfig securityConfig = new SecurityConfig(metadataSource.getName());
                    configAttributes.add(securityConfig);
                    hashMap.put(metadataSource.getPath(), configAttributes);
                }
                replicatedMap.putAll(hashMap);
            }
        }
    }
}

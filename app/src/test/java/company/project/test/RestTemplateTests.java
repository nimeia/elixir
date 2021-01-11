package company.project.test;

import company.project.app.ProjectApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = {ProjectApp.class})
public class RestTemplateTests {

    @Test
    public void getTest(@Autowired RestTemplate template) {
        ResponseEntity<String> forEntity = template.getForEntity("https://www.baidu.com", String.class);
        System.out.println(forEntity.getBody());
        Assert.notNull(forEntity.getBody());
    }
}

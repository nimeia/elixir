package company.project.core.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * json 工具类
 *
 * @author huang
 */
public abstract class JsonUtils {

    static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    /**
     * object to json
     * @param value
     * @return
     */
    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json to object
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json,Class<T> tClass){
        try {
            return objectMapper.readValue(json,tClass);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException(e);
        }
    }
}

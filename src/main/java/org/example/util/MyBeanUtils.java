package org.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @date : 2021/8/9
 */
@Slf4j
public class MyBeanUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * set prop value of an object
     *
     * @param o
     * @param key
     * @param value
     */
    public static void setPropValue(Object o, String key, String value) {
        try {
            PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(o.getClass(), key);
            if (pd != null) {
                Method writeMethod = pd.getWriteMethod();
                Type[] types = writeMethod.getGenericParameterTypes();
                Type type = types[0];
                JavaType javaType = TypeFactory.defaultInstance().constructType(type);
                Object propertyValue = OBJECT_MAPPER.readValue(value, javaType);

                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                writeMethod.invoke(o, propertyValue);
            }
        } catch (Exception e) {
            log.error("set prop value failed, ignored key : {}", key, e);
        }
    }

    /**
     * Convert all the non-null properties of this object to String map
     *
     * @param o
     * @return
     */
    public static Map<String, String> toStringMap(Object o) {
        Map<String, String> result = new HashMap<>();
        try {
            PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(o.getClass());
            for (PropertyDescriptor pd : pds) {
                if (pd != null && !pd.getName().equals("class")) {
                    Method readMethod = pd.getReadMethod();
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value = readMethod.invoke(o);
                    if (value != null) {
                        result.put(pd.getName(), OBJECT_MAPPER.writeValueAsString(value));
                    }
                }
            }
        } catch (Exception e) {
            log.error("read prop value exception", e);
        }
        return result;
    }

}

package com.li.honedu.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = null;

    public JsonUtil() {
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return mapper.writeValueAsString(obj);
            } catch (IOException var2) {
                logger.error(String.format("obj=[%s]", obj.toString()), var2);
                return null;
            }
        }
    }

    /** @deprecated */
    @Deprecated
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        } else {
            try {
                return mapper.readValue(json, clazz);
            } catch (IOException var3) {
                logger.error(String.format("json=[%s]", json), var3);
                return null;
            }
        }
    }

    public static <T> T parseJson(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        } else {
            try {
                return mapper.readValue(json, clazz);
            } catch (IOException var3) {
                logger.error(String.format("json=[%s]", json), var3);
                return null;
            }
        }
    }

    /** @deprecated */
    @Deprecated
    public static <T> Collection<T> fromJson(String json, Class<? extends Collection> collectionClazz, Class<T> clazz) {
        if (json == null) {
            return null;
        } else {
            try {
                Collection<T> collection = (Collection)mapper.readValue(json, getCollectionType(collectionClazz, clazz));
                return collection;
            } catch (IOException var4) {
                logger.error(String.format("json=[%s]", json), var4);
                return null;
            }
        }
    }

    public static <T> Collection<T> parseJson(String json, Class<? extends Collection> collectionClazz, Class<T> clazz) {
        if (json == null) {
            return null;
        } else {
            try {
                Collection<T> collection = (Collection)mapper.readValue(json, getCollectionType(collectionClazz, clazz));
                return collection;
            } catch (IOException var4) {
                logger.error(String.format("json=[%s]", json), var4);
                return null;
            }
        }
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static void main(String[] args) {
    }

    static {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        DeserializationConfig dc = mapper.getDeserializationConfig();
        dc.with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);
        mapper.setConfig(dc);
    }
}

package com.golemon.blogbackend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A utility class for Redis serialization and deserialization using FastJson.
 * <p>
 * This class performs serialization and deserialization of objects using FastJson,
 * ensuring type information support during the process.
 * </p>
 *
 * @param <T> The type of object to be serialized and deserialized
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * Default character set (UTF-8)
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * The target class to be serialized and deserialized
     */
    private final Class<T> clazz;

    // Static block to enable auto type support
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    /**
     * Constructor that takes the target class type.
     *
     * @param clazz The target class type to be serialized/deserialized
     */
    public FastJsonRedisSerializer(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Target class cannot be null.");
        }
        this.clazz = clazz;
    }

    /**
     * Serializes an object into a byte array.
     *
     * @param t The object to be serialized
     * @return The serialized byte array
     * @throws SerializationException Exception that occurs during serialization
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];  // Return empty byte array for null object
        }
        try {
            // Convert object to JSON string using FastJson and then to byte array
            String jsonStr = JSON.toJSONString(t, SerializerFeature.WriteClassName);
            return jsonStr.getBytes(DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new SerializationException("Failed to serialize object of class " + clazz.getName(), e);
        }
    }

    /**
     * Deserializes a byte array into an object.
     *
     * @param bytes The byte array
     * @return The deserialized object
     * @throws SerializationException Exception that occurs during deserialization
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;  // Return null for empty byte array
        }
        try {
            // Convert byte array to string and deserialize to object using FastJson
            String jsonStr = new String(bytes, DEFAULT_CHARSET);
            return JSON.parseObject(jsonStr, clazz);
        } catch (Exception e) {
            throw new SerializationException("Failed to deserialize byte array to object of class " + clazz.getName(), e);
        }
    }
}
package com.francesca.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.francesca.common.CommException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Json Util
 *
 * @author francesca
 *
 *
 *
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    static {
        //设置java.util.Date时间类的序列化以及反序列化的格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_PATTERN));

        // 初始化JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        //处理LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        //处理LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        //处理LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        //注册时间模块, 支持支持jsr310, 即新的时间类(java.time包下的时间类)
        OBJECT_MAPPER.registerModule(javaTimeModule);

        // 包含所有字段
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        // 在序列化一个空对象时时不抛出异常
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 忽略反序列化时在json字符串中存在, 但在java对象中不存在的属性
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private static final ObjectWriter PRETTY_PRINTER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();


    /**
     * 对象转换成json字符串
     *
     * @param value
     * @return
     */
    public static String toJsonString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("对象 转换成 json字符串 出现异常错误: ", e);
            throw new CommException( "对象 转换成 json字符串 出现异常错误");
        }
    }
    /**
     * 对象转换成json(格式化)字符串
     *
     * @param value
     * @return
     */
    public static String toPrettyJsonString(Object value) {
        try {
            return PRETTY_PRINTER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("对象 转换成 json(格式化)字符串 出现异常错误: ", e);
            throw new CommException( "对象 转换成 json(格式化)字符串 出现异常错误");
        }
    }

    /**
     * json字符串转换成对象
     *
     * @param content
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(String content, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            log.error("json字符串 转换成 {} 出现异常错误:  ", clazz.getName(), e);
            throw new CommException(String.format("json字符串 转换成 %s 出现异常错误", clazz.getName()));
        }
    }

    /**
     * json字符串转换成复杂对象（例如，集合）
     *
     * @param content
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T toObject(String content,  TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(content, typeReference);
        } catch (JsonProcessingException e) {
            log.error("json字符串 转换成 {} 出现异常错误:  ", typeReference.getType().getTypeName(), e);
            throw new CommException(String.format("json字符串 转换成 %s 出现异常错误", typeReference.getType().getTypeName()));
        }
    }


    /**
     * json字符串转换成JsonNode
     *
     * @param content
     * @return
     */
    public static JsonNode toJsonNode(String content){
        try {
            return OBJECT_MAPPER.readTree(content);
        } catch (JsonProcessingException e) {
            log.error("json字符串 转换成 JsonNode 出现异常错误:  ", e);
            throw new CommException("json字符串 转换成 JsonNode 出现异常错误");
        }
    }

}


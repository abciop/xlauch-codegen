package com.xlauch.generator.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类描述 : json转换器配置
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2022/8/13 14:12
 */
@Configuration("defaultFastjsonConfig")
@ConditionalOnClass(com.alibaba.fastjson.JSON.class)
@ConditionalOnMissingBean(FastJsonHttpMessageConverter.class)
@ConditionalOnWebApplication
public class JsonConfig {

    /**
     * fastjson的配置
     *
     * @return
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(fastjsonConfig());
        converter.setSupportedMediaTypes(getSupportedMediaType());
        return converter;
    }

    /**
     * fastjson的配置
     */
    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                // 格式化输出
                SerializerFeature.PrettyFormat,
                // 消除循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                // 返回结果保留null值
                SerializerFeature.WriteMapNullValue,
                // 将返回值为null的字符串转变成"",在这里可以自己设置
                SerializerFeature.WriteNullStringAsEmpty,
                // List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty
        );
        // 解决 SerializerFeature.WriteNullStringAsEmpty 不生效问题
        ValueFilter valueFilter = (o, s, o1) -> {
            if (null == o1 && o1 instanceof String) {
                o1 = "";
            }
            return o1;
        };
        // 设置全局日期格式
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 注入过滤器
        fastJsonConfig.setSerializeFilters(valueFilter);

        // Long、BigDecimal 序列化时转 String
//        在创建SerializeConfig时，使用了globalInstance并设置了相应的策略，这个是全局实例，会导致其他地方序列化时，也会采用全局的配置，这样就会出现Seata 序列化时，也采用个这个策略。
//        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        //解决Long转json精度丢失的问题
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        serializeConfig.put(BigDecimal.class, ToStringSerializer.instance);

        // 在转换器中添加配置信息
        fastJsonConfig.setSerializeConfig(serializeConfig);
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        return fastJsonConfig;
    }

    /**
     * 支持的mediaType类型 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
     */
    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        return mediaTypes;
    }

}

package com.july.configcenter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xingxing.zhong on 2020/7/22
 */
@Configuration
@PropertySource(value = "a.properties")
@Data
public class CustomProperties {
    @Value(value = "${card.a}")
    private int aval;

}

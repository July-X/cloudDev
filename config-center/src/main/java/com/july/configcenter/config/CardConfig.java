package com.july.configcenter.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2020/7/8
 *
 * @author xingxing.zhong
 */
@Data
@Configuration
@NacosPropertySource(dataId = "nacos-config-card.properties", autoRefreshed = true)
public class CardConfig {
    private final Logger logger = LoggerFactory.getLogger(CardConfig.class);
    @NacosValue(value = "${card.number:25}", autoRefreshed = true)
    private int number;
    @NacosValue(value = "${card.suit}", autoRefreshed = true)
    private String suit;

    @NacosConfigListener(dataId = "nacos-config-card.properties")
    public void defMessage(String config) {
        logger.info("default" + config);
    }
}

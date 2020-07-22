package com.july.configcenter.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * https://github.com/alibaba/nacos/issues/3303
 * <p>
 * Created on 2020/7/8
 *
 * @author xingxing.zhong
 */
@Data
@Configuration
@NacosPropertySource(dataId = "nacos-config-card-gp.properties", groupId = "DEV_GROUP", autoRefreshed = true)
public class GpCardConfig {
    private final Logger logger = LoggerFactory.getLogger(GpCardConfig.class);
    @NacosValue(value = "${card.number:25}", autoRefreshed = true)
    private int number;
    @NacosValue(value = "${card.suit}", autoRefreshed = true)
    private String suit;

    @NacosConfigListener(dataId = "nacos-config-card-gp.properties", groupId = "DEV_GROUP")
    public void message(String config) {
        logger.info(config);
    }
}

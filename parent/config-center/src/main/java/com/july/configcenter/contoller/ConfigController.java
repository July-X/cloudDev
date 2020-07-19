package com.july.configcenter.contoller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.july.configcenter.config.CardConfig;
import com.july.configcenter.config.GpCardConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2020/7/7
 *
 * @author xingxing.zhong
 */
@Controller
@RequestMapping("/config")
@NacosPropertySource(dataId = "nacos-config-example.properties", autoRefreshed = true)
public class ConfigController {

    @NacosValue(value = "${user.age:25}", autoRefreshed = true)
    private int userAge;
    @NacosValue(value = "${user.nam2e:fuck}", autoRefreshed = true)
    private String userName;

    @Autowired
    private CardConfig cardConfig;

    @Autowired
    private GpCardConfig gpCardConfig;


    @RequestMapping(value = "/get")
    @ResponseBody
    public String get() {
        final String user = System.getProperty("user.name");
        return userName + "-" + userAge + "-System:" + user;
    }

    @GetMapping(value = "/card")
    @ResponseBody
    public String card() {
        return cardConfig.getNumber() + "--" + cardConfig.getSuit();
    }

    @GetMapping(value = "/ns")
    @ResponseBody
    public String ns() {
        return gpCardConfig.getNumber() + "-SS-" + gpCardConfig.getSuit();
    }
}
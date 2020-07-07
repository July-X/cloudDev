package com.july.configcenter.contoller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get() {
        final String user = System.getProperty("user.name");
        return userName + "-" + userAge + "-System:" + user;
    }
}
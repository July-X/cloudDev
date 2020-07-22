package com.july.baseserver.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author xingxing.zhong on 2020/7/22
 */
@RestController
@RequestMapping("discovery")
public class DiscoveryController {
    @NacosInjected
    private NamingService namingService;
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Integer serverPort;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            namingService.registerInstance(applicationName, "127.0.0.1", serverPort);
            // 根据服务名从注册中心获取一个健康的服务实例
            Instance instance = namingService.selectOneHealthyInstance("base-server");
            // 这里只是为了方便才新建RestTemplate实例
            String url = String.format("http://%s:%d/discovery/get?serviceName=base-server", instance.getIp(), instance.getPort());
            String result = restTemplate.getForObject(url, String.class);
            System.out.println(String.format("请求URL:%s,响应结果:%s", url, result));
        };
    }
}

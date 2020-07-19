# Nacos config server 使用

## SpringBoot 集成nacos配置中心

* maven依赖配置
```
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>nacos-config-spring-boot-starter</artifactId>
            <version>${nacos-config-spring-boot.version}</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba.boot</groupId>
            <artifactId>nacos-config-spring-boot-actuator</artifactId>
            <version>${nacos-config-spring-boot.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies><!-- 版本控制 -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

* 核心配置项`application.properties` 添加以下内容
``` 
nacos.config.server-addr=127.0.0.1:8848
management.endpoints.jmx.exposure.include=*
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

* 代码使用方式
1. 在需要读取配置得地方使用注解` @NacosPropertySource(dataId = "nacos中发布的配置数据id", autoRefreshed = true)`
2. 独立的配置bean方式 

``` 
@Data
@Configuration
@NacosPropertySource(dataId = "nacos-config-card.properties", autoRefreshed = true)
public class CardConfig {
    @NacosValue(value = "${card.number:25}", autoRefreshed = true)
    private int number;
    @NacosValue(value = "${card.suit}", autoRefreshed = true)
    private String suit;
}
```
3. 非单独bean的时候可以在配置了`@NacosPropertySource `的类中直接用`@NacosValue` 来获取配置属性

``` 
@NacosValue(value = "${user.age:25}", autoRefreshed = true)
    private int userAge;
    @NacosValue(value = "${user.nam2e:fuck}", autoRefreshed = true)
    private String userName;
```
## 不同profile如何隔离配置


## 读取配置得底层机制分析


## 自动刷新配置机制分析


* nacos 接受配置变动事件，自动解析复制逻辑`com.alibaba.nacos.spring.context.annotation.config.NacosValueAnnotationBeanPostProcessor.onApplicationEvent`

``` 
public void onApplicationEvent(NacosConfigReceivedEvent event) {
		// In to this event receiver, the environment has been updated the
		// latest configuration information, pull directly from the environment
		// fix issue #142
		for (Map.Entry<String, List<NacosValueTarget>> entry : placeholderNacosValueTargetMap.entrySet()) {
			String key = environment.resolvePlaceholders(entry.getKey());
			String newValue = environment.getProperty(key);
			if (newValue == null) {
				continue;
			}
			List<NacosValueTarget> beanPropertyList = entry.getValue();
			for (NacosValueTarget target : beanPropertyList) {
				String md5String = MD5.getInstance().getMD5String(newValue);
				boolean isUpdate = !target.lastMD5.equals(md5String);
				if (isUpdate) {
					target.updateLastMD5(md5String);
					if (target.method == null) {
						setField(target, newValue);
					} else {
						setMethod(target, newValue);
					}
				}
			}
		}
	}
```



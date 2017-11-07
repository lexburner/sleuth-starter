package moe.cnkirito.sleuth.order.config;

import com.weibo.api.motan.config.springsupport.*;
import com.weibo.api.motan.filter.sleuth.SleuthTracerFactory;
import com.weibo.api.motan.filter.sleuth.SleuthTracingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by xiaoqian on 2016/9/27.
 */
@Configuration
public class MotanConfig {

    @Bean
    public AnnotationBean motanAnnotationBean(@Value("${motan.annotaiong-package}") String annotaiongPackage) {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage(annotaiongPackage);
        if(motanAnnotationBean.getPackage()==null){
            throw new RuntimeException("请配置maton api包");
        }
        return motanAnnotationBean;
    }

    @Bean(name = "motanServer")
    public ProtocolConfigBean protocolConfig() {
        return getProtocolConfigBean();
    }

    @Bean(name = "motanClient")
    public ProtocolConfigBean protocolConfigClient() {
        return getProtocolConfigBean();
    }

    private ProtocolConfigBean getProtocolConfigBean() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setSerialization("fastjson");
        config.setName("motan");
        config.setMaxContentLength(1048576);
//        config.setSerialization("fastjson");
        return config;
    }

    @Bean(name = "registry")
    public RegistryConfigBean registryConfigSit(@Value("${motan.zookeeper-host}") String zookeeperHost) {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setDefault(true);
        config.setRegProtocol("zookeeper");
        config.setAddress(zookeeperHost);
        return config;
    }

    /**
     * 基础服务端配置
     * @return
     */
    @Bean(name = "motanServerBasicConfig")
    public BasicServiceConfigBean baseServiceConfig(@Value("${motan.export-port}") Integer exportPort,
                                                    @Value("${motan.server-group}") String serverGroup,
                                                    @Value("${motan.server-access-log}") Boolean serverAccessLog,
                                                    @Value("${spring.sleuth.enabled:false}") Boolean tracing
    ) {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setDefault(true);
        config.setExport("motanServer:"+exportPort);
        config.setGroup(serverGroup);
        config.setAccessLog(serverAccessLog);
        config.setShareChannel(true);
        config.setRequestTimeout(60*1000);
        if(tracing){
            config.setFilter("sleuth-tracing");
        }
        config.setRegistry("registry");
        return config;
    }


    @Bean(name = "motanClientBasicConfig")
    public BasicRefererConfigBean baseRefererConfig(@Value("${motan.client-group}") String clientGroup,
                                                    @Value("${motan.client-access-log}") Boolean clientAccessLog) {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("motanClient");
        config.setGroup(clientGroup);
        config.setAccessLog(clientAccessLog);
        config.setRegistry("registry");
        config.setCheck(false);
        config.setRetries(2);
        config.setRequestTimeout(60*1000);
        config.setThrowException(true);
        config.setDefault(true);
        return config;
    }


    @Bean
    SleuthTracingContext sleuthTracingContext(@Autowired(required = false)  org.springframework.cloud.sleuth.Tracer tracer){
        SleuthTracingContext context = new SleuthTracingContext();
        context.setTracerFactory(new SleuthTracerFactory() {
            @Override
            public org.springframework.cloud.sleuth.Tracer getTracer() {
                return tracer;
            }
        });

        return context;
    }

}

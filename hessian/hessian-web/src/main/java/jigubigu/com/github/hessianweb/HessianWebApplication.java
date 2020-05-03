package jigubigu.com.github.hessianweb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import service.UserService;

@SpringBootApplication
public class HessianWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HessianWebApplication.class, args);
    }

    @Value("${hessian.service.url.user}")
    private String userServiceUrl;

    @Bean
    public HessianProxyFactoryBean helloClient(){
        HessianProxyFactoryBean factoryBean = new HessianProxyFactoryBean();
        factoryBean.setServiceUrl(userServiceUrl);
        factoryBean.setServiceInterface(UserService.class);
        return factoryBean;
    }
}

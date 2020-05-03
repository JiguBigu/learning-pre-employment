package jigubigu.com.github.hessianservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import service.UserService;

@SpringBootApplication
public class HessianServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HessianServiceApplication.class, args);
    }

    @Autowired
    private UserService userService;

    @Bean(name = "/user/getOne")
    public HessianServiceExporter accountService(){
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(userService);
        exporter.setServiceInterface(UserService.class);
        return exporter;
    }
}

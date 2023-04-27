package jpabook.jpashop.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfigure {

    @Bean
    public Tracer logTracer(){
        return new ThreadLocalTracer();
    }
}

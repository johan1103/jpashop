package jpabook.jpashop.swagger;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "JPA SHOP API 명세서",
                description = "jpashop API 명세서",
                version = "v1"
        )
)
@Configuration
public class config {

}

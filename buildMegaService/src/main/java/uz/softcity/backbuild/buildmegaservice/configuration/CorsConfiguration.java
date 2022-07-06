package uz.softcity.backbuild.buildmegaservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * for cors configuration class
 */
@Configuration
public class CorsConfiguration {
    /*
     * @Value("${allowed.origin}")
     * private String allowedOrigin;
     */

    /**
     * @return permission of configuration
     */
    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
//                        .allowedOrigins(allowedOrigin)
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}

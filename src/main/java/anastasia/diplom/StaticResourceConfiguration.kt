package anastasia.diplom

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
open class StaticResourceConfiguration : WebMvcConfigurerAdapter() {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!
                .addResourceHandler("/public/**")
                .addResourceLocations("file:public/")
    }
}

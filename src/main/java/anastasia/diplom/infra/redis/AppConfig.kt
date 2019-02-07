package anastasia.diplom.infra.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory


@Configuration
open class AppConfig {

    @Bean
    open fun redisConnectionFactory(): JedisConnectionFactory {
        return JedisConnectionFactory()
    }
}
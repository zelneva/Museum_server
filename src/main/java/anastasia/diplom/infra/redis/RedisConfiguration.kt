package anastasia.diplom.infra.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.RedisSentinelConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory





@Configuration
open class RedisConfiguration {

    @Bean
    open fun redisConnectionFactory(): JedisConnectionFactory {

        val config = RedisStandaloneConfiguration("localhost", 6379)
        return JedisConnectionFactory(config)
    }

    @Bean
    open fun jedisConnectionFactory(): RedisConnectionFactory {
        val sentinelConfig = RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("127.0.0.1", 26379)
                .sentinel("127.0.0.1", 26380)
        return JedisConnectionFactory(sentinelConfig)
    }
}
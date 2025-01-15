package practice.global.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import practice.global.property.RedisProperty

@Configuration
@EnableConfigurationProperties(RedisProperty::class)
class AppConfig {
}

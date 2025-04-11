package co.payment.global.config

import co.payment.global.property.RedisProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(RedisProperty::class)
class AppConfig

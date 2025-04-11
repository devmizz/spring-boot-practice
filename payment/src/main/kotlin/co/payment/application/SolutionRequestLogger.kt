package co.payment.application

import co.payment.domain.ExternalSolutionRequestLog
import co.payment.infrastructure.repository.ExternalSolutionRequestLogRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val logger = KotlinLogging.logger { }

@Component
class SolutionRequestLogger(
    private val externalSolutionRequestLogRepository: ExternalSolutionRequestLogRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    @Transactional
    fun getMessageNumber(): Int {
        val recentRequestLog = externalSolutionRequestLogRepository.findByRequestAtOrderByIdDesc()
        val thisRequestMessageNumber = (recentRequestLog?.requestMessageNumber ?: 0) + 1
        return thisRequestMessageNumber.also {
            externalSolutionRequestLogRepository.save(ExternalSolutionRequestLog(it))
        }
    }

    @Transactional
    fun getMessageNumberThroughIncr(date: LocalDate): Int {
        val thisRequestMessageNumber = redisTemplate.opsForValue().increment(date.toString())?.toInt() ?: 0
        return thisRequestMessageNumber.also {
            externalSolutionRequestLogRepository.save(ExternalSolutionRequestLog(it))
        }
    }
}
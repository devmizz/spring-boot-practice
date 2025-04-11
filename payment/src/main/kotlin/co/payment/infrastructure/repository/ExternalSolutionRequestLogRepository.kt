package co.payment.infrastructure.repository

import co.payment.domain.ExternalSolutionRequestLog
import org.springframework.data.domain.Limit
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ExternalSolutionRequestLogRepository : JpaRepository<ExternalSolutionRequestLog, Long> {
    fun findByRequestAtOrderByIdDesc(
        requestAt: LocalDate = LocalDate.now(),
        limit: Limit = Limit.of(1)
    ): ExternalSolutionRequestLog?
}

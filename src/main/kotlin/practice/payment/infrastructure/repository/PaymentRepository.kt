package practice.payment.infrastructure.repository

import org.springframework.stereotype.Repository
import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.RegularPaymentTarget
import java.time.LocalDate

@Repository
class PaymentRepository(
    private val map: MutableMap<Long, RegularPaymentTarget> = mutableMapOf()
) {

    fun save(targets: List<RegularPaymentTarget>) {
        targets.forEach { target ->
            map.put(map.size + 1L, target)
        }
    }

    fun findTodayRegularPaymentTargets(): List<RegularPaymentTarget> {
        return map.values
            .filter { it.regularDate == LocalDate.now().dayOfMonth }
            .toList()
    }

    fun findTodayRegularPaymentTargets(
        solutionType: PaymentSolutionType
    ): List<RegularPaymentTarget> {
        return map.values
            .filter {
                it.regularDate == LocalDate.now().dayOfMonth
                        && it.withdrawalSolutionType == solutionType
            }
            .toList()
    }
}

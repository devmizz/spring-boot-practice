package co.payment.infrastructure.repository

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import org.springframework.stereotype.Repository
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

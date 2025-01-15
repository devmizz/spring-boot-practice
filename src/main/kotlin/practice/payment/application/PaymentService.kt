package practice.payment.application

import org.springframework.stereotype.Service
import practice.payment.infrastructure.repository.PaymentRepository
import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.PaymentSolutionType.KARINA
import practice.payment.domain.PaymentSolutionType.WINTER
import practice.payment.domain.RegularPaymentTarget
import practice.payment.infrastructure.solution.KarinaPaymentSolutionProvider
import practice.payment.infrastructure.solution.PaymentSolutionProvider

@Service
class PaymentService(
    private val withdrawalSolutionProcessor: List<PaymentSolutionProvider>,
    private val transferSolutionProcessor: KarinaPaymentSolutionProvider,
    private val paymentRepository: PaymentRepository,
) {

    fun payRegularly(solutionType: PaymentSolutionType) {
        val targets = paymentRepository.findTodayRegularPaymentTargets(solutionType)

        if (solutionType == KARINA) {
            filterKarinaSolutionTargets(targets)
        }

        val withdrawalResult =
            withdrawalSolutionProcessor.single { it.solutionType == solutionType }
                .withdraw(targets)
                .also { paymentRepository.save(it.successTargets) }

        if (solutionType == WINTER) {
            updateTransferAmount(targets)
        }

        transferSolutionProcessor.transfer(withdrawalResult.successTargets)
            .also { paymentRepository.save(it.successTargets) }
    }

    private fun filterKarinaSolutionTargets(
        targets: List<RegularPaymentTarget>
    ): List<RegularPaymentTarget> {
        return targets.filter { it.accountNumber.endsWith("01") }
    }

    private fun updateTransferAmount(targets: List<RegularPaymentTarget>) {
        targets.forEach { it.updateTransferAmount(0.9) }
    }
}

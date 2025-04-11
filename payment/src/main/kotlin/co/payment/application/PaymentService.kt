package co.payment.application

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import co.payment.infrastructure.repository.PaymentRepository
import co.payment.infrastructure.solution.KarinaPaymentSolutionProvider
import co.payment.infrastructure.solution.PaymentSolutionProvider
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val withdrawalSolutionProcessor: List<PaymentSolutionProvider>,
    private val transferSolutionProcessor: KarinaPaymentSolutionProvider,
    private val paymentRepository: PaymentRepository,
) {

    fun payRegularly(solutionType: PaymentSolutionType) {
        val targets = paymentRepository.findTodayRegularPaymentTargets(solutionType)

        if (solutionType == PaymentSolutionType.KARINA) {
            filterKarinaSolutionTargets(targets)
        }

        val withdrawalResult =
            withdrawalSolutionProcessor.single { it.solutionType == solutionType }
                .withdraw(targets)
                .also { paymentRepository.save(it.successTargets) }

        if (solutionType == PaymentSolutionType.WINTER) {
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

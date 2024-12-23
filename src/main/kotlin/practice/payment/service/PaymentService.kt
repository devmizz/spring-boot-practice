package practice.payment.service

import org.springframework.stereotype.Service
import practice.payment.PaymentRepository
import practice.payment.PaymentSolutionType
import practice.payment.PaymentSolutionType.KARINA
import practice.payment.PaymentSolutionType.WINTER
import practice.payment.RegularPaymentTarget
import practice.payment.solution.KarinaPaymentSolutionProcessor
import practice.payment.solution.PaymentSolutionProcessor

@Service
class PaymentService(
    private val withdrawalSolutionProcessor: List<PaymentSolutionProcessor>,
    private val transferSolutionProcessor: KarinaPaymentSolutionProcessor,
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

package practice.payment.service

import practice.payment.PaymentRepository
import practice.payment.PaymentSolutionType
import practice.payment.PaymentSolutionType.KARINA
import practice.payment.RegularPaymentTarget
import practice.payment.TransferResult
import practice.payment.WithdrawalResult
import practice.payment.solution.KarinaPaymentSolutionProcessor

class KarinaRegularPaymentService(
    private val karinaPaymentProcessor: KarinaPaymentSolutionProcessor,
    private val paymentRepository: PaymentRepository,
) : RegularPaymentService() {

    override val solutionType: PaymentSolutionType = KARINA

    override fun getTodayRegularPaymentTargets(): List<RegularPaymentTarget> {
        return paymentRepository.findTodayRegularPaymentTargets(solutionType)
            .filter { it.accountNumber.endsWith("01") }
    }

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        return karinaPaymentProcessor.withdraw(targets)
    }

    override fun transfer(successTargets: List<RegularPaymentTarget>): TransferResult {
        return karinaPaymentProcessor.transfer(successTargets)
    }
}

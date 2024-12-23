package practice.payment.service

import practice.payment.PaymentRepository
import practice.payment.PaymentSolutionType
import practice.payment.RegularPaymentTarget
import practice.payment.TransferResult
import practice.payment.WithdrawalResult
import practice.payment.solution.KarinaPaymentSolutionProcessor
import practice.payment.solution.WinterPaymentSolutionProcessor

class WinterRegularPaymentService(
    private val paymentRepository: PaymentRepository,
    private val winterPaymentProcessor: WinterPaymentSolutionProcessor,
    private val transferPaymentProcessor: KarinaPaymentSolutionProcessor,
) : RegularPaymentService() {

    override val solutionType: PaymentSolutionType = PaymentSolutionType.WINTER

    override fun getTodayRegularPaymentTargets(): List<RegularPaymentTarget> {
        return paymentRepository.findTodayRegularPaymentTargets(solutionType)
    }

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        return winterPaymentProcessor.withdraw(targets)
    }

    override fun transfer(successTargets: List<RegularPaymentTarget>): TransferResult {
        successTargets.map { it.updateTransferAmount(0.9) }
        return transferPaymentProcessor.transfer(successTargets)
    }
}
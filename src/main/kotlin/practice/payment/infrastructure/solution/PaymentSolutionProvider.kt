package practice.payment.infrastructure.solution

import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.RegularPaymentTarget
import practice.payment.domain.TransferResult
import practice.payment.domain.WithdrawalResult

interface PaymentSolutionProvider {
    val solutionType: PaymentSolutionType
    fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult
    fun transfer(targets: List<RegularPaymentTarget>): TransferResult
}
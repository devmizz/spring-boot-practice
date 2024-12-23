package practice.payment.solution

import practice.payment.PaymentSolutionType
import practice.payment.RegularPaymentTarget
import practice.payment.TransferResult
import practice.payment.WithdrawalResult

interface PaymentSolutionProcessor {
    val solutionType: PaymentSolutionType
    fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult
    fun transfer(targets: List<RegularPaymentTarget>): TransferResult
}
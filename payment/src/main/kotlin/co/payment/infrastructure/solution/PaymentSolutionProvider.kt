package co.payment.infrastructure.solution

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import co.payment.domain.TransferResult
import co.payment.domain.WithdrawalResult

interface PaymentSolutionProvider {
    val solutionType: PaymentSolutionType
    fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult
    fun transfer(targets: List<RegularPaymentTarget>): TransferResult
}
package practice.payment.service

import org.springframework.stereotype.Service
import practice.payment.PaymentSolutionType
import practice.payment.RegularPaymentTarget
import practice.payment.TransferResult
import practice.payment.WithdrawalResult

@Service
abstract class RegularPaymentService {

    abstract val solutionType: PaymentSolutionType

    fun pay() {
        val targets = getTodayRegularPaymentTargets()
        val withdrawalResult = withdraw(targets)
        transfer(withdrawalResult.successTargets)
    }
    abstract fun getTodayRegularPaymentTargets(): List<RegularPaymentTarget>
    abstract fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult
    abstract fun transfer(successTargets: List<RegularPaymentTarget>): TransferResult
}
package practice.payment.application

import org.springframework.stereotype.Service
import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.RegularPaymentTarget
import practice.payment.domain.TransferResult
import practice.payment.domain.WithdrawalResult

@Service
abstract class RegularPaymentService {

    abstract val solutionType: PaymentSolutionType

    fun pay() {
        val targets = getTodayRegularPaymentTargets()
        val withdrawalResult = withdraw(targets)
        transfer(withdrawalResult.successTargets)
    }

    fun payWithRedisIncr() {
        val targets = getTodayRegularPaymentTargets()
        val withdrawalResult = withdrawWithIncr(targets)
        transferWithIncr(withdrawalResult.successTargets)
    }

    abstract fun getTodayRegularPaymentTargets(): List<RegularPaymentTarget>
    abstract fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult
    abstract fun transfer(successTargets: List<RegularPaymentTarget>): TransferResult
    abstract fun withdrawWithIncr(targets: List<RegularPaymentTarget>): WithdrawalResult
    abstract fun transferWithIncr(successTargets: List<RegularPaymentTarget>): TransferResult
}
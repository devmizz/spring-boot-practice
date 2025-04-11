package co.payment.application

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import co.payment.domain.TransferResult
import co.payment.domain.WithdrawalResult
import org.springframework.stereotype.Service

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
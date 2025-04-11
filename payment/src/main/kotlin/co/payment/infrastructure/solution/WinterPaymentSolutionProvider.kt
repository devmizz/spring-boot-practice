package co.payment.infrastructure.solution

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import co.payment.domain.TransferResult
import co.payment.domain.WithdrawalResult
import org.springframework.stereotype.Component

@Component
class WinterPaymentSolutionProvider(
    override val solutionType: PaymentSolutionType = PaymentSolutionType.WINTER
) : PaymentSolutionProvider {

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        // 윈터 출금 호출
        return WithdrawalResult(targets)
    }

    override fun transfer(targets: List<RegularPaymentTarget>): TransferResult {
        // 윈터 송금 호출
        return TransferResult(targets)
    }
}
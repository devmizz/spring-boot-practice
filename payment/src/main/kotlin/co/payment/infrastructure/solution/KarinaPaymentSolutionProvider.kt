package co.payment.infrastructure.solution

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import co.payment.domain.TransferResult
import co.payment.domain.WithdrawalResult
import org.springframework.stereotype.Component

@Component
class KarinaPaymentSolutionProvider(
    override val solutionType: PaymentSolutionType = PaymentSolutionType.KARINA
) : PaymentSolutionProvider {

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        // 카리나 출금 호출
        return WithdrawalResult(targets)
    }

    override fun transfer(targets: List<RegularPaymentTarget>): TransferResult {
        // 카리나 송금 호출
        return TransferResult(targets)
    }
}

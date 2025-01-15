package practice.payment.infrastructure.solution

import org.springframework.stereotype.Component
import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.RegularPaymentTarget
import practice.payment.domain.TransferResult
import practice.payment.domain.WithdrawalResult

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
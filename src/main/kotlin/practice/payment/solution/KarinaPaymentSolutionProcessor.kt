package practice.payment.solution

import org.springframework.stereotype.Component
import practice.payment.PaymentSolutionType
import practice.payment.RegularPaymentTarget
import practice.payment.TransferResult
import practice.payment.WithdrawalResult

@Component
class KarinaPaymentSolutionProcessor(
    override val solutionType: PaymentSolutionType = PaymentSolutionType.KARINA
) : PaymentSolutionProcessor {

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        // 카리나 출금 호출
        println("카리나 출금을 호출하셨습니다!")
        return WithdrawalResult(targets)
    }

    override fun transfer(targets: List<RegularPaymentTarget>): TransferResult {
        // 카리나 송금 호출
        println("카리나 송금을 호출하셨습니다!")
        return TransferResult(targets)
    }
}

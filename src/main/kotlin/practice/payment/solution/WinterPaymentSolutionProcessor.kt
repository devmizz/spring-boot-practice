package practice.payment.solution

import org.springframework.stereotype.Component
import practice.payment.PaymentSolutionType
import practice.payment.RegularPaymentTarget
import practice.payment.TransferResult
import practice.payment.WithdrawalResult

@Component
class WinterPaymentSolutionProcessor(
    override val solutionType: PaymentSolutionType = PaymentSolutionType.WINTER
) : PaymentSolutionProcessor {

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        // 윈터 출금 호출
        println("윈터 출금을 호출하셨습니다!")
        return WithdrawalResult(targets)
    }

    override fun transfer(targets: List<RegularPaymentTarget>): TransferResult {
        // 윈터 송금 호출
        println("윈터 송금을 호출하셨습니다!")
        return TransferResult(targets)
    }
}
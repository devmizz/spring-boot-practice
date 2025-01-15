package practice.payment.application

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.PaymentSolutionType.KARINA
import practice.payment.domain.RegularPaymentTarget
import practice.payment.domain.TransferResult
import practice.payment.domain.WithdrawalResult
import practice.payment.infrastructure.repository.PaymentRepository
import practice.payment.infrastructure.solution.KarinaPaymentSolutionProvider
import java.time.LocalDate

private val logger = KotlinLogging.logger {  }

@Service
class KarinaRegularPaymentService(
    private val karinaPaymentProcessor: KarinaPaymentSolutionProvider,
    private val paymentRepository: PaymentRepository,
    private val solutionRequestLogger: SolutionRequestLogger
) : RegularPaymentService() {

    override val solutionType: PaymentSolutionType = KARINA

    override fun getTodayRegularPaymentTargets(): List<RegularPaymentTarget> {
        return paymentRepository.findTodayRegularPaymentTargets(solutionType)
            .filter { it.accountNumber.endsWith("01") }
    }

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        val messageNumber = solutionRequestLogger.getMessageNumber()
        logger.info { "전문번호: $messageNumber 메서드: 카리나 - 출금" }
        return karinaPaymentProcessor.withdraw(targets)
    }

    override fun transfer(successTargets: List<RegularPaymentTarget>): TransferResult {
        val messageNumber = solutionRequestLogger.getMessageNumber()
        logger.info { "전문번호: $messageNumber 메서드: 카리나 - 송금" }
        return karinaPaymentProcessor.transfer(successTargets)
    }

    override fun withdrawWithIncr(targets: List<RegularPaymentTarget>): WithdrawalResult {
        val now = LocalDate.now()
        val messageNumber = solutionRequestLogger.getMessageNumberThroughIncr(now)
        logger.info { "전문번호: $messageNumber 메서드: 카리나 - 출금" }
        return karinaPaymentProcessor.withdraw(targets)
    }

    override fun transferWithIncr(successTargets: List<RegularPaymentTarget>): TransferResult {
        val now = LocalDate.now()
        val messageNumber = solutionRequestLogger.getMessageNumberThroughIncr(now)
        logger.info { "전문번호: $messageNumber 메서드: 카리나 - 송금" }
        return karinaPaymentProcessor.transfer(successTargets)
    }
}

package practice.payment.application

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import practice.payment.domain.PaymentSolutionType
import practice.payment.domain.RegularPaymentTarget
import practice.payment.domain.TransferResult
import practice.payment.domain.WithdrawalResult
import practice.payment.infrastructure.repository.PaymentRepository
import practice.payment.infrastructure.solution.KarinaPaymentSolutionProvider
import practice.payment.infrastructure.solution.WinterPaymentSolutionProvider
import java.time.LocalDate

private val logger = KotlinLogging.logger {  }

@Service
class WinterRegularPaymentService(
    private val paymentRepository: PaymentRepository,
    private val winterPaymentProcessor: WinterPaymentSolutionProvider,
    private val transferPaymentProcessor: KarinaPaymentSolutionProvider,
    private val solutionRequestLogger: SolutionRequestLogger
) : RegularPaymentService() {

    override val solutionType: PaymentSolutionType = PaymentSolutionType.WINTER

    override fun getTodayRegularPaymentTargets(): List<RegularPaymentTarget> {
        return paymentRepository.findTodayRegularPaymentTargets(solutionType)
    }

    override fun withdraw(targets: List<RegularPaymentTarget>): WithdrawalResult {
        val messageNumber = solutionRequestLogger.getMessageNumber()
        logger.info { "전문번호: $messageNumber 메서드: 윈터 - 출금" }
        return winterPaymentProcessor.withdraw(targets)
    }

    override fun transfer(successTargets: List<RegularPaymentTarget>): TransferResult {
        val messageNumber = solutionRequestLogger.getMessageNumber()
        logger.info { "전문번호: $messageNumber 메서드: 윈터 - 송금" }
        successTargets.map { it.updateTransferAmount(0.9) }
        return transferPaymentProcessor.transfer(successTargets)
    }

    override fun withdrawWithIncr(targets: List<RegularPaymentTarget>): WithdrawalResult {
        val now = LocalDate.now()
        val messageNumber = solutionRequestLogger.getMessageNumberThroughIncr(now)
        logger.info { "전문번호: $messageNumber 메서드: 윈터 - 출금" }
        return winterPaymentProcessor.withdraw(targets)
    }

    override fun transferWithIncr(successTargets: List<RegularPaymentTarget>): TransferResult {
        val now = LocalDate.now()
        val messageNumber = solutionRequestLogger.getMessageNumberThroughIncr(now)
        logger.info { "전문번호: $messageNumber 메서드: 윈터 - 송금" }
        return winterPaymentProcessor.transfer(successTargets)
    }
}
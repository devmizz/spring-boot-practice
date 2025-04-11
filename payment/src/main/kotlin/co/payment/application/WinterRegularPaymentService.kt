package co.payment.application

import co.payment.domain.PaymentSolutionType
import co.payment.domain.RegularPaymentTarget
import co.payment.domain.TransferResult
import co.payment.domain.WithdrawalResult
import co.payment.infrastructure.repository.PaymentRepository
import co.payment.infrastructure.solution.KarinaPaymentSolutionProvider
import co.payment.infrastructure.solution.WinterPaymentSolutionProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDate

private val logger = KotlinLogging.logger { }

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
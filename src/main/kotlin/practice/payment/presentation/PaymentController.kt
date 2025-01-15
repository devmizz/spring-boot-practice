package practice.payment.presentation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import practice.payment.domain.PaymentSolutionType.KARINA
import practice.payment.domain.PaymentSolutionType.WINTER
import practice.payment.application.PaymentService
import practice.payment.application.RegularPaymentService

@RestController
class PaymentController(
    private val paymentService: PaymentService,
    private val regularPaymentServices: List<RegularPaymentService>,
) {

    @PostMapping("/v1/recurring-payment/karina")
    fun payRegularly() {
        regularPaymentServices.single { it.solutionType == KARINA }.pay()
    }

    @PostMapping("/v1/recurring-payment/winter")
    fun payRegularlyWinter() {
        regularPaymentServices.single { it.solutionType == WINTER }.pay()
    }
}
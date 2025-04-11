package co.payment.presentation

import co.payment.application.PaymentService
import co.payment.application.RegularPaymentService
import co.payment.domain.PaymentSolutionType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val paymentService: PaymentService,
    private val regularPaymentServices: List<RegularPaymentService>,
) {

    @PostMapping("/v1/recurring-payment/karina")
    fun payRegularly() {
        regularPaymentServices.single { it.solutionType == PaymentSolutionType.KARINA }.pay()
    }

    @PostMapping("/v1/recurring-payment/winter")
    fun payRegularlyWinter() {
        regularPaymentServices.single { it.solutionType == PaymentSolutionType.WINTER }.pay()
    }
}
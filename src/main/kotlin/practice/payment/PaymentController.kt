package practice.payment

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import practice.payment.PaymentSolutionType.KARINA
import practice.payment.PaymentSolutionType.WINTER
import practice.payment.service.PaymentService
import practice.payment.service.RegularPaymentService

@RestController
class PaymentController(
    private val paymentService: PaymentService,
    private val regularPaymentServices: List<RegularPaymentService>,
) {

    @PostMapping("/v1/recurring-payment")
    fun payRegularly() {
        regularPaymentServices.single { it.solutionType == KARINA }.pay()
    }

    @PostMapping("/v1/recurring-payment/winter")
    fun payRegularlyWinter() {
        regularPaymentServices.single { it.solutionType == WINTER }.pay()
    }
}
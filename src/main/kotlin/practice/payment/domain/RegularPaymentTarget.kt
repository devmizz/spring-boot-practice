package practice.payment.domain

import java.math.BigDecimal

class RegularPaymentTarget(
    val targetId: Long,
    val accountNumber: String,
    val amount: BigDecimal,
    transferAmount: BigDecimal,
    val withdrawalSolutionType: PaymentSolutionType,
    val regularDate: Int,
) {

    var transferAmount: BigDecimal = transferAmount
        private set

    fun updateTransferAmount(discountRate: Double) {
        transferAmount *= BigDecimal.valueOf(discountRate)
    }
}

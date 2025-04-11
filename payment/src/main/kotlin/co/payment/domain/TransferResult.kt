package co.payment.domain

data class TransferResult(
    val successTargets: List<RegularPaymentTarget>
)

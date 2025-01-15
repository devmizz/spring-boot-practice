package practice.payment.domain

data class TransferResult(
    val successTargets: List<RegularPaymentTarget>
)

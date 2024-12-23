package practice.payment

data class TransferResult(
    val successTargets: List<RegularPaymentTarget>
)

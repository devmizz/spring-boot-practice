package practice.payment

data class WithdrawalResult(
    val successTargets: List<RegularPaymentTarget>
)

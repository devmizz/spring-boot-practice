package practice.payment.domain

data class WithdrawalResult(
    val successTargets: List<RegularPaymentTarget>
)

package co.payment.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity(name = "external_solution_log")
class ExternalSolutionRequestLog(
    @Column(name = "request_message_number")
    val requestMessageNumber: Int,
    @Column(name = "request_at")
    val requestAt: LocalDate = LocalDate.now()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
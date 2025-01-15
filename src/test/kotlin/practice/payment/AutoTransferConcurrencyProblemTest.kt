package practice.payment

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import practice.payment.application.RegularPaymentService
import practice.payment.domain.PaymentSolutionType
import practice.payment.infrastructure.repository.ExternalSolutionRequestLogRepository
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
//@Transactional
class AutoTransferConcurrencyProblemTest {

    @Autowired
    lateinit var regularPaymentServices: List<RegularPaymentService>

    @Autowired
    lateinit var externalSolutionRequestLogRepository: ExternalSolutionRequestLogRepository

    @AfterEach
    fun clearData() {
        externalSolutionRequestLogRepository.deleteAll()
    }

    @Test
    fun `INCR을 사용하지 않는 경우 동시성 이슈가 발생한다`() {
        runConcurrentTask(2, 50) {
            regularPaymentServices.single { it.solutionType == PaymentSolutionType.WINTER }.pay()
            regularPaymentServices.single { it.solutionType == PaymentSolutionType.KARINA }.pay()
        }

        val logs = externalSolutionRequestLogRepository.findAll()
        val distinctSize = logs.map { it.requestMessageNumber }.distinct().size

        assertThat(logs.size).isGreaterThan(distinctSize)
        println("전체 개수: ${logs.size} / 중복되지 않은 전문번호 개수: $distinctSize")
    }

    @Test
    fun `INCR을 사용하는 경우 동시성 이슈가 발생하지 않는다`() {
        runConcurrentTask(2, 50) {
            regularPaymentServices.single { it.solutionType == PaymentSolutionType.WINTER }.payWithRedisIncr()
            regularPaymentServices.single { it.solutionType == PaymentSolutionType.KARINA }.payWithRedisIncr()
        }

        val logs = externalSolutionRequestLogRepository.findAll()
        val distinctSize = logs.map { it.requestMessageNumber }.distinct().size
        assertThat(logs.size).isEqualTo(distinctSize)
        println("전체 개수: ${logs.size} / 중복되지 않은 전문번호 개수: $distinctSize")
    }

    private fun runConcurrentTask(numberOfThread: Int, repeatCountPerThread: Int, task: () -> Unit) {
        val startLatch = CountDownLatch(1)
        val doneLatch = CountDownLatch(numberOfThread);
        val executor = Executors.newFixedThreadPool(numberOfThread)

        repeat(numberOfThread) {
            executor.submit {
                try {
                    startLatch.await()
                    repeat(repeatCountPerThread) {
                        task()
                    }
                } finally {
                    doneLatch.countDown()
                }
            }
        }

        startLatch.countDown()
        doneLatch.await()
        executor.shutdown()
        executor.awaitTermination(1L, TimeUnit.SECONDS)
    }
}
package co.app

import co.app.service.MockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class DockerComposeTest @Autowired constructor(
    private val mockService: MockService
) {

    @Test
    fun test() {
        mockService.mockMethod()
    }
}
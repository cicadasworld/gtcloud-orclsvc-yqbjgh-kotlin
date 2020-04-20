package gtcloud.yqbjgh

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class OracleServiceApplication

fun main(args: Array<String>) {
    runApplication<OracleServiceApplication>(*args)
}

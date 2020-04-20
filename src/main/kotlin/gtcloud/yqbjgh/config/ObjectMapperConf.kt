package gtcloud.yqbjgh.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class ObjectMapperConf {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }
}
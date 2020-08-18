package gtcloud.yqbjgh.config

import com.fasterxml.jackson.core.JsonGenerator.Feature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConf {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().configure(Feature.ESCAPE_NON_ASCII, true)
    }
}
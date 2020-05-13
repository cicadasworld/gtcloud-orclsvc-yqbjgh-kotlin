package gtcloud.yqbjgh.config

import org.locationtech.jts.geom.GeometryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeometryFactoryConf {

    @Bean
    fun geometryFactory(): GeometryFactory {
        return GeometryFactory()
    }
}
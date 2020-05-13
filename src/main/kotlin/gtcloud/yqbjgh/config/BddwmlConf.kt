package gtcloud.yqbjgh.config

import gtcloud.yqbjgh.domain.TxzhTsBddwml
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BddwmlConf {

    @Autowired
    lateinit var bddwmlRepo: TxzhTsBddwmlRepository

    @Bean
    fun xhToBigUnit(): Map<String, TxzhTsBddwml> {
        val entities = bddwmlRepo.findByParentnmIsNull()
        val rootId = entities.map { it.nm }.first()
        return bddwmlRepo.findByParentnm(rootId).associateBy({it.xh}, {it})
    }
}
package gtcloud.yqbjgh.bootstrap

import gtcloud.yqbjgh.services.TxzhTsBddwmlService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ContextRefreshedListener {

    @Autowired
    lateinit var rootIdsBean: RootIdsBean

    @Autowired
    lateinit var xhToBigUnitBean: XhToBigUnitBean

    @Autowired
    lateinit var service: TxzhTsBddwmlService

    @EventListener(ContextRefreshedEvent::class)
//    @Scheduled(fixedRate = 600_000)
    fun contextRefreshedEvent() {
        rootIdsBean.rootIds = service.getRootIds()
        xhToBigUnitBean.xhToBigUnit = service.getXhToBigUnitMap()
    }
}
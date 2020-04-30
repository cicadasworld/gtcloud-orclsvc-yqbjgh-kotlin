package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.UnitNode
import gtcloud.yqbjgh.services.UnitTreeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UnitTreeController {

    @Autowired
    lateinit var service: UnitTreeService

    @GetMapping("/bddwml-resident/catalogue")
    fun getUnitTree(): List<UnitNode> = service.getUnitTree()

    @GetMapping("/bddwml-zbgc/catalogue")
    fun getMnrUnitTree(): List<UnitNode> = service.getMnrUnitTree()

    @GetMapping("/bddwml-resident-unit/catalogue")
    fun getUseUnitTree(): List<UnitNode> = service.getUseUnitTree()

    @GetMapping("/bddwml-resident-unitkind/catalogue")
    fun getKindUnitTree(): List<UnitNode> = service.getKindUnitTree()
}
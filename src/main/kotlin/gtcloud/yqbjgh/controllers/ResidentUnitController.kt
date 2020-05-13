package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.Node
import gtcloud.yqbjgh.domain.ResidentUnit
import gtcloud.yqbjgh.services.ResidentUnitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ResidentUnitController {

    @Autowired
    lateinit var service: ResidentUnitService

    @GetMapping("/resident-unit/bdnm/{bdnm}")
    fun getResidentUnit(@PathVariable bdnm: String): List<ResidentUnit> =
        service.getResidentUnit(bdnm)

    @GetMapping("/resident-unit/campid/{campId}")
    fun getResidentUnitByUsingCampId(@PathVariable campId: String): List<Node> =
        service.getResidentUnitByUsingCampId(campId)

    @GetMapping("/resident-unit/apartid/{apartId}")
    fun getResidentUnitByUsingApartId(@PathVariable apartId: String): List<Node> =
        service.getResidentUnitByUsingApartId(apartId)

}
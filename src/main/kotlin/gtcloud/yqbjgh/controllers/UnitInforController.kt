package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.StaffStatistics
import gtcloud.yqbjgh.domain.UnitNode
import gtcloud.yqbjgh.domain.VUnitInfor
import gtcloud.yqbjgh.services.VUnitInforService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UnitInforController {

    @Autowired
    lateinit var service: VUnitInforService

    @GetMapping("/v-unit-infor/bdnm/{bdnm}")
    fun getVUnitInforBybdnm(@PathVariable bdnm: String,
                            @RequestParam("unitkind") unitKind: String? = null): VUnitInfor =
        service.getVUnitInforBybdnm(bdnm, unitKind)

    @GetMapping("/v-unit-infor/campid/{campId}")
    fun getVUnitInforByCampId(@PathVariable campId: String): StaffStatistics =
        service.getVUnitInforByCampId(campId)

    @GetMapping("/v-unit-infor/apartnum/{apartNum}")
    fun getVUnitInforByApartNum(@PathVariable apartNum: String): StaffStatistics =
        service.getVUnitInforByApartNum(apartNum)

    @GetMapping("/v-unit-infor/unitkind/{unitKind}")
    fun getVUnitInforByUnitKind(@PathVariable unitKind: String): StaffStatistics =
        service.getVUnitInforByUnitKind(unitKind)

    @GetMapping("/v-unit-infor/rootnm/{unitKindNm}")
    fun getRootBdnmByUnitKindNm(@PathVariable unitKindNm: String): List<UnitNode> =
        service.getRootBdnmByUnitKindNm(unitKindNm)
}
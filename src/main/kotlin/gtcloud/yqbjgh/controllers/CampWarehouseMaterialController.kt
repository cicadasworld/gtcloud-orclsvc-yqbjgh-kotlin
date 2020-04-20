package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampWarehouseMaterial
import gtcloud.yqbjgh.domain.MaterialStatistics
import gtcloud.yqbjgh.services.CampWarehouseMaterialService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampWarehouseMaterialController {

    @Autowired
    lateinit var service: CampWarehouseMaterialService

    @GetMapping("/camp-warehouse-material/{warehouseId}")
    fun getCampWarehouseMaterial(@PathVariable warehouseId: String): List<CampWarehouseMaterial> =
        service.getCampWarehouseMaterial(warehouseId)

    @GetMapping("/camp-warehouse-material/material-kind/{campId}")
    fun getStatisticsByMaterialKind(@PathVariable campId: String): Map<String?, MaterialStatistics> =
        service.getStatisticsByMaterialKind(campId)

    @GetMapping("/camp-warehouse-material/dangerous-attr/{campId}")
    fun getStatisticsByDangerousAttr(@PathVariable campId: String): Map<String?, MaterialStatistics> =
            service.getStatisticsByDangerousAttr(campId)
}
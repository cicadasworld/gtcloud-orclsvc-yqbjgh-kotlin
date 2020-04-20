package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampWarehouse
import gtcloud.yqbjgh.services.CampWarehouseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampWarehouseController {

    @Autowired
    lateinit var service: CampWarehouseService

    @GetMapping("/camp-warehouse/{campId}")
    fun getCampWarehouse(@PathVariable campId: String): List<CampWarehouse> =
        service.getCampWarehouse(campId)
}
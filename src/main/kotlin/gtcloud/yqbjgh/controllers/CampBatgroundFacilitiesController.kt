package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampBatgroundFacilities
import gtcloud.yqbjgh.services.CampBatgroundFacilitiesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampBatgroundFacilitiesController {

    @Autowired
    lateinit var service: CampBatgroundFacilitiesService

    @GetMapping("/camp-batground-facilities/{campId}")
    fun getCampBatgroundFacilities(@PathVariable campId: String): List<CampBatgroundFacilities> =
        service.getCampBatgroundFacilities(campId)
}
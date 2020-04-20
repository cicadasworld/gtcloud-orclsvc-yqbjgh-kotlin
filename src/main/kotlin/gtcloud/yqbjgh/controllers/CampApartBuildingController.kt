package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampApartBuilding
import gtcloud.yqbjgh.domain.CampApartBuildingInfo
import gtcloud.yqbjgh.services.CampApartBuildingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampApartBuildingController {

    @Autowired
    lateinit var service: CampApartBuildingService

    @GetMapping("/camp-apart-building/{jlbm}")
    fun getCampApartBuilding(@PathVariable jlbm: String): CampApartBuilding = service.getById(jlbm)

    @GetMapping("/camp-apart-building/campid/{campId}")
    fun getCampApartBuildingByCampId(@PathVariable campId: String): List<CampApartBuilding> =
        service.getCampApartBuildingByCampId(campId)

    @GetMapping("/v-apart-coordinate-json")
    fun getAllVApartCoordinates(): List<CampApartBuildingInfo> = service.getAllVApartCoordinateJsons()
}
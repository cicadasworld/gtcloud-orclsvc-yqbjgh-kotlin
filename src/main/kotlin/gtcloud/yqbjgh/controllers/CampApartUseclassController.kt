package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampApartUseclass
import gtcloud.yqbjgh.domain.HousingStatistics
import gtcloud.yqbjgh.services.CampApartUseclassService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampApartUseclassController {

    @Autowired
    lateinit var service: CampApartUseclassService

    @GetMapping("/camp-apart-useclass/{apartId}")
    fun getCampApartUseclass(@PathVariable apartId: String): List<CampApartUseclass> =
        service.getCampApartUseClass(apartId)

    @GetMapping("/camp-apart-useclass/area-statistics/{campId}")
    fun getHousingStatistics(@PathVariable campId: String): Map<String?, HousingStatistics> =
        service.getAreaStatistics(campId)
}
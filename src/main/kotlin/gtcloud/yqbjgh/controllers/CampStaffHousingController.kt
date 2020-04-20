package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampStaffHousing
import gtcloud.yqbjgh.services.CampStaffHousingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampStaffHousingController {

    @Autowired
    lateinit var service: CampStaffHousingService

    @GetMapping("/camp-staff-housing/{apartId}")
    fun getCampStaffHousing(@PathVariable apartId: String): List<CampStaffHousing> =
        service.getCampStaffHousing(apartId)
}
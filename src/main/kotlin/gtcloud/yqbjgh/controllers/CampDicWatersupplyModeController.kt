package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicWatersupplyMode
import gtcloud.yqbjgh.services.CampDicWatersupplyModeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicWatersupplyModeController {

    @Autowired
    lateinit var service: CampDicWatersupplyModeService

    @GetMapping("/camp-dic-watersupply-mode")
    fun getCampDicWatersupplyModeList(): List<CampDicWatersupplyMode> = service.listAll()

}
package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicGassupplyMode
import gtcloud.yqbjgh.services.CampDicGassupplyModeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicGassupplyModeController {

    @Autowired
    lateinit var service: CampDicGassupplyModeService

    @GetMapping("/camp-dic-gassupply-mode")
    fun getCampDicGassupplyModeList(): List<CampDicGassupplyMode> = service.listAll()

}
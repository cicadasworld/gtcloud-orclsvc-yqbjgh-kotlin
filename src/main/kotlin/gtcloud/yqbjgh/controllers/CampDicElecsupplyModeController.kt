package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicElecsupplyMode
import gtcloud.yqbjgh.services.CampDicElecsupplyModeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicElecsupplyModeController {

    @Autowired
    lateinit var service: CampDicElecsupplyModeService

    @GetMapping("/camp-dic-elecsupply-mode")
    fun getCampDicElecsupplyModeList(): List<CampDicElecsupplyMode> = service.listAll()

}
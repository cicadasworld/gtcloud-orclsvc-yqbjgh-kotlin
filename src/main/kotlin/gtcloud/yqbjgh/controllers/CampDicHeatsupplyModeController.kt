package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicHeatsupplyMode
import gtcloud.yqbjgh.services.CampDicHeatsupplyModeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicHeatsupplyModeController {

    @Autowired
    lateinit var service: CampDicHeatsupplyModeService

    @GetMapping("/camp-dic-heatsupply-mode")
    fun getCampDicHeatsupplyModeList(): List<CampDicHeatsupplyMode> = service.listAll()

}
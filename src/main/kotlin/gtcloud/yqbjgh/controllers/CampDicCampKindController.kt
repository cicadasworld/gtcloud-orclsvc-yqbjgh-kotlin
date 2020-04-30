package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicCampKind
import gtcloud.yqbjgh.services.CampDicCampKindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicCampKindController {

    @Autowired
    lateinit var service: CampDicCampKindService

    @GetMapping("/camp-dic-camp-kind")
    fun getCampDicCampKindList(): List<List<CampDicCampKind>> = service.listAll()

}
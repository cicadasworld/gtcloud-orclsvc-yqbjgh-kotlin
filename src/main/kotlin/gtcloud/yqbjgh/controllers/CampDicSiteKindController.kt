package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicSiteKind
import gtcloud.yqbjgh.services.CampDicSiteKindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicSiteKindController {

    @Autowired
    lateinit var service: CampDicSiteKindService

    @GetMapping("/camp-dic-site-kind")
    fun getCampDicSiteKindList(): List<CampDicSiteKind> = service.listAll()

}
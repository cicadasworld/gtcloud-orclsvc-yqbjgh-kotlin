package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampLocationKind
import gtcloud.yqbjgh.services.CampLocationKindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampLocationKindController {

    @Autowired
    lateinit var service: CampLocationKindService

    @GetMapping("/camp-location-kind")
    fun getCampLocationKindList(): List<CampLocationKind> = service.listAll()

}
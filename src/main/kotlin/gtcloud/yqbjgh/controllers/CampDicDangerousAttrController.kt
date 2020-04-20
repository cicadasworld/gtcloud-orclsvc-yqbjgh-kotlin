package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicDangerousAttr
import gtcloud.yqbjgh.services.CampDicDangerousAttrService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicDangerousAttrController {

    @Autowired
    lateinit var service: CampDicDangerousAttrService

    @GetMapping("/camp-dic-dangerous-attr")
    fun getCampDicDangerousAttrList(): List<CampDicDangerousAttr> = service.listAll()

}
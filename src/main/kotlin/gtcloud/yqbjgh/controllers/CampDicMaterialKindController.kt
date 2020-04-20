package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicMaterialKind
import gtcloud.yqbjgh.services.CampDicMaterialKindService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicMaterialKindController {

    @Autowired
    lateinit var service: CampDicMaterialKindService

    @GetMapping("/camp-dic-material-kind")
    fun getCampDicMaterialKindList(): List<CampDicMaterialKind> = service.listAll()

}
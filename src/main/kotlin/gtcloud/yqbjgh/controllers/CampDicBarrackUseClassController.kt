package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampDicBarrackUseClass
import gtcloud.yqbjgh.services.CampDicBarrackUseClassService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CampDicBarrackUseClassController {

    @Autowired
    lateinit var service: CampDicBarrackUseClassService

    @GetMapping("/camp-dic-barrack-use-class")
    fun getCampDicBarrackUseClassList(): List<CampDicBarrackUseClass> = service.listAll()

}
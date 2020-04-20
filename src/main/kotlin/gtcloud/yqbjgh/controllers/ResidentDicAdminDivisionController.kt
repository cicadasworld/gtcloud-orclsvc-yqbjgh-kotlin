package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.ResidentDicAdminDivision
import gtcloud.yqbjgh.services.ResidentDicAdminDivisionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ResidentDicAdminDivisionController {

    @Autowired
    lateinit var service: ResidentDicAdminDivisionService

    @GetMapping("/resident-dic-admin-division")
    fun getResidentDicAdminDivisionList(): List<ResidentDicAdminDivision> = service.listAll()

}
package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampTrainingField
import gtcloud.yqbjgh.services.CampTrainingFieldService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CampTrainingFieldController {

    @Autowired
    lateinit var service: CampTrainingFieldService

    @GetMapping("/camp-training-field/{campId}")
    fun getCampTrainingField(@PathVariable campId: String): List<CampTrainingField> =
        service.getCampTrainingField(campId)
}
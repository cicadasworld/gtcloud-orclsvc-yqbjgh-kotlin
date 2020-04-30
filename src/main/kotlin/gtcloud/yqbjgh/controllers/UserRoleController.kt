package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.services.UserRoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRoleController {

    @Autowired
    lateinit var service: UserRoleService

    @GetMapping("/UserRole")
    fun login(@RequestParam username: String): Boolean =
        service.isPlanAdmin(username)

}
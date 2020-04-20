package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.services.UserLoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserLoginController {

    @Autowired
    lateinit var service: UserLoginService

    @GetMapping("/UserLogin")
    fun login(@RequestParam("id") username: String,
              @RequestParam("pwd") password: String): Array<String> =
        service.login(username, password)

}
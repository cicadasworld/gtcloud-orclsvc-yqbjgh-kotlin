package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.repositories.Bdf2UserRepository
import gtcloud.yqbjgh.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserLoginService {

    @Autowired
    lateinit var repository: Bdf2UserRepository

    fun login(username: String, password: String): Array<String> {
        val user = repository.findByUsername(username)
        val encPass = user.password
        val salt = user.salt
        val passwordEncoder = ShaPasswordEncoder()
        val valid = passwordEncoder.isPasswordValid(encPass, password, salt)
        return if (valid) {
            arrayOf("true", "\u767b\u5f55\u6210\u529f!")
        } else {
            arrayOf("false", "\u7528\u6237\u540d\u6216\u5bc6\u7801\u9519\u8bef")
        }
    }
}
package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.repositories.Bdf2RoleMemberRepository
import gtcloud.yqbjgh.repositories.Bdf2RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserRoleService {

    @Autowired
    lateinit var roleRepository: Bdf2RoleRepository

    @Autowired
    lateinit var roleMemberRepository: Bdf2RoleMemberRepository

    fun isPlanAdmin(username: String): Boolean {
        val roleMembers = roleMemberRepository.findByUsername(username)
        for (roleMember in roleMembers) {
            val roleId = roleMember.roleId
            val role = roleRepository.findByIdOrNull(roleId)
            if (role != null && role.roleNm == "plan_admin") {
                return true
            }
        }
        return false
    }
}
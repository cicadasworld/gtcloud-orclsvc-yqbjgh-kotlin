package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.ResidentDicAdminDivision
import gtcloud.yqbjgh.repositories.ResidentDicAdminDivisionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResidentDicAdminDivisionService {

    @Autowired
    lateinit var repository: ResidentDicAdminDivisionRepository

    fun listAll(): List<ResidentDicAdminDivision> = repository.findAll()
}
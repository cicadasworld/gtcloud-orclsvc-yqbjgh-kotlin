package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicElecsupplyMode
import gtcloud.yqbjgh.repositories.CampDicElecsupplyModeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicElecsupplyModeService {

    @Autowired
    lateinit var repository: CampDicElecsupplyModeRepository

    fun listAll(): List<CampDicElecsupplyMode> = repository.findAll()
}
package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicGassupplyMode
import gtcloud.yqbjgh.repositories.CampDicGassupplyModeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicGassupplyModeService {

    @Autowired
    lateinit var repository: CampDicGassupplyModeRepository

    fun listAll(): List<CampDicGassupplyMode> = repository.findAll()
}
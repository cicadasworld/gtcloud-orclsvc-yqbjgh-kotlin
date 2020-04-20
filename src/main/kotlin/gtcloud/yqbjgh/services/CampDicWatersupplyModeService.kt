package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicWatersupplyMode
import gtcloud.yqbjgh.repositories.CampDicWatersupplyModeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicWatersupplyModeService {

    @Autowired
    lateinit var repository: CampDicWatersupplyModeRepository

    fun listAll(): List<CampDicWatersupplyMode> = repository.findAll()
}
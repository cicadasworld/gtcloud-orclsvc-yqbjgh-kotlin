package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicHeatsupplyMode
import gtcloud.yqbjgh.repositories.CampDicHeatsupplyModeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicHeatsupplyModeService {

    @Autowired
    lateinit var repository: CampDicHeatsupplyModeRepository

    fun listAll(): List<CampDicHeatsupplyMode> = repository.findAll()
}
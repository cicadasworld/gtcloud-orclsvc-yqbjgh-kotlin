package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicCampKind
import gtcloud.yqbjgh.repositories.CampDicCampKindRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicCampKindService {

    @Autowired
    lateinit var repository: CampDicCampKindRepository

    fun listAll(): List<CampDicCampKind> = repository.findAll()
}
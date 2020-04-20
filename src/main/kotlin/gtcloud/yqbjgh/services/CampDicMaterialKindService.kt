package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicMaterialKind
import gtcloud.yqbjgh.repositories.CampDicMaterialKindRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicMaterialKindService {

    @Autowired
    lateinit var repository: CampDicMaterialKindRepository

    fun listAll(): List<CampDicMaterialKind> = repository.findAll()
}
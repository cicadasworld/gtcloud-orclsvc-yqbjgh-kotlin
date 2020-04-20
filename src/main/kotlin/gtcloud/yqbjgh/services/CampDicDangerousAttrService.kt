package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicDangerousAttr
import gtcloud.yqbjgh.repositories.CampDicDangerousAttrRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicDangerousAttrService {

    @Autowired
    lateinit var repository: CampDicDangerousAttrRepository

    fun listAll(): List<CampDicDangerousAttr> = repository.findAll()
}
package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicSiteKind
import gtcloud.yqbjgh.repositories.CampDicSiteKindRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicSiteKindService {

    @Autowired
    lateinit var repository: CampDicSiteKindRepository

    fun listAll(): List<CampDicSiteKind> = repository.findAll()
}
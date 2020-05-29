package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampLocationKind
import gtcloud.yqbjgh.repositories.CampLocationKindRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampLocationKindService {

    @Autowired
    lateinit var repository: CampLocationKindRepository

    fun listAll(): List<CampLocationKind> {
        return repository.findAll()
    }
}
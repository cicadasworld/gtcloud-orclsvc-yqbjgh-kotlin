package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicBarrackUseClass
import gtcloud.yqbjgh.repositories.CampDicBarrackUseClassRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicBarrackUseClassService {

    @Autowired
    lateinit var repository: CampDicBarrackUseClassRepository

    fun listAll(): List<CampDicBarrackUseClass> = repository.findAll()
}
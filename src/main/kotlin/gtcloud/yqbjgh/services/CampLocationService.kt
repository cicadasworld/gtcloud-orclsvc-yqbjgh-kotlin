package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampLocation
import gtcloud.yqbjgh.repositories.CampLocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampLocationService {

    @Autowired
    lateinit var campLocationRepository: CampLocationRepository

    fun getByRelatedMainCampId(nm: String): List<CampLocation> {
        return campLocationRepository.findByRelatedMainCampid(nm)
                .filter { it.nm != nm && it.campArea == 0f }
    }

    fun getByNm(nm: String): List<CampLocation> {
        return campLocationRepository.findByNm(nm)
    }
}
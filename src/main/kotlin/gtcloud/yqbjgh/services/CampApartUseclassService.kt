package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampApartUseclass
import gtcloud.yqbjgh.domain.HousingStatistics
import gtcloud.yqbjgh.repositories.CampApartBuildingRepository
import gtcloud.yqbjgh.repositories.CampApartUseclassRepository
import gtcloud.yqbjgh.repositories.CampDicBarrackUseClassRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CampApartUseclassService {

    @Autowired
    lateinit var campApartUseclassRepository: CampApartUseclassRepository

    @Autowired
    lateinit var campApartBuildingRepository: CampApartBuildingRepository

    @Autowired
    lateinit var campDicBarrackUseClassRepository: CampDicBarrackUseClassRepository

    fun getCampApartUseClass(apartId: String): List<CampApartUseclass> {
        val campApartUseclasses = campApartUseclassRepository.findByApartId(apartId)
        return campApartUseclasses.
                map { convert(it) }
    }

    fun convert(campApartUseclass: CampApartUseclass): CampApartUseclass {
        val campApartBuilding = campApartBuildingRepository
                .findByIdOrNull(campApartUseclass.apartId ?: "")
        val campDicBarrackUseClass = campDicBarrackUseClassRepository
                .findByIdOrNull(campApartUseclass.barrackUseClass?: "")
        return campApartUseclass.copy(
                apartName = campApartBuilding?.apartName, // apartId -> apartName
                barrackUseClass = campDicBarrackUseClass?.mc // barrackUseClass -> barrackUseClassÃû³Æ
        )
    }

    fun getAreaStatistics(campId: String): Map<String?, HousingStatistics> {
        val apartBuildings = campApartBuildingRepository.findByCampId(campId)
        val apartUseclasses = apartBuildings.
                flatMap { campApartUseclassRepository.findByApartId(it.jlbm) }
        val map = apartUseclasses.groupBy { it.barrackUseClass }
        return map.mapValues { (_, apartUseclasses) ->
            val set = apartUseclasses.size
            val area = apartUseclasses.sumByDouble { it.barrackUseArea?.toDouble()?:0.0 }
            HousingStatistics(set = set, area = area)
        }
    }
}
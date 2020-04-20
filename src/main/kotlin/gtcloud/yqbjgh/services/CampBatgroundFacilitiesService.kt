package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampBatgroundFacilities
import gtcloud.yqbjgh.repositories.CampBatgroundFacilitiesRepository
import gtcloud.yqbjgh.repositories.CampDicBatKindRepository
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampBatgroundFacilitiesService {

    @Autowired
    lateinit var campBatgroundFacilitiesRepository: CampBatgroundFacilitiesRepository

    @Autowired
    lateinit var campDicBatKindRepository: CampDicBatKindRepository

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    fun getCampBatgroundFacilities(campId: String): List<CampBatgroundFacilities> {
        val campBatgroundFacilities = campBatgroundFacilitiesRepository.findByCampId(campId)
        return campBatgroundFacilities.
                map { convert(it) }
    }

    fun convert(campBatgroundFacilities: CampBatgroundFacilities): CampBatgroundFacilities {
        val batKind = campBatgroundFacilities.batKind
        val campDicBatKind = campDicBatKindRepository.findById(batKind ?: "").get()
        val managementUnit = campBatgroundFacilities.managementUnit
        val txzhTsBddwml = txzhTsBddwmlRepository.findById(managementUnit ?: "").get()

        return campBatgroundFacilities.copy(
                batKind = campDicBatKind.mc, // batKind -> batKindÃû³Æ
                managementUnit = txzhTsBddwml.mc //  managementUnit -> bdÃû³Æ
        )
    }
}
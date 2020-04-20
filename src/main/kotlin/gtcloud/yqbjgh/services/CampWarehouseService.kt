package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampWarehouse
import gtcloud.yqbjgh.repositories.CampDicWarKindRepository
import gtcloud.yqbjgh.repositories.CampWarehouseRepository
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampWarehouseService {

    @Autowired
    lateinit var campWarehouseRepository: CampWarehouseRepository

    @Autowired
    lateinit var campDicWarKindRepository: CampDicWarKindRepository

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    fun getCampWarehouse(campId: String): List<CampWarehouse> {
        return campWarehouseRepository.findByCampId(campId)
                .map { convert(it) }
    }

    fun convert(campWarehouse: CampWarehouse): CampWarehouse {
        val campDicWarKind = campDicWarKindRepository
                .findById(campWarehouse.warKind ?: "").orElse(null)

        val txzhTsBddwml = txzhTsBddwmlRepository
                .findById(campWarehouse.managementUnit ?: "").orElse(null)

        return campWarehouse.copy(
                warKind = campDicWarKind?.mc,  // warKind -> warKindÃû³Æ
                managementUnit = txzhTsBddwml?.mc  // managementUnit -> bdÃû³Æ
        )
    }
}
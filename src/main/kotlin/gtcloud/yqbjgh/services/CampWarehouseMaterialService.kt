package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampWarehouseMaterial
import gtcloud.yqbjgh.domain.MaterialStatistics
import gtcloud.yqbjgh.repositories.CampDicDangerousAttrRepository
import gtcloud.yqbjgh.repositories.CampDicMaterialKindRepository
import gtcloud.yqbjgh.repositories.CampWarehouseMaterialRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CampWarehouseMaterialService {

    @Autowired
    lateinit var campWarehouseMaterialRepository: CampWarehouseMaterialRepository

    @Autowired
    lateinit var campDicMaterialKindRepository: CampDicMaterialKindRepository

    @Autowired
    lateinit var campDicDangerousAttrRepository: CampDicDangerousAttrRepository

    fun getCampWarehouseMaterial(warehouseId: String): List<CampWarehouseMaterial> {
        return campWarehouseMaterialRepository.findByWarehouseId(warehouseId)
                .map { convert(it) }
    }

    fun convert(campWarehouseMaterial: CampWarehouseMaterial): CampWarehouseMaterial {
        val campDicMaterialKind = campDicMaterialKindRepository
                .findByIdOrNull(campWarehouseMaterial.materialKind ?: "")

        val campDicDangerousAttr = campDicDangerousAttrRepository
                .findByIdOrNull(campWarehouseMaterial.dangerousAttr ?: "")

        return campWarehouseMaterial.copy(
                materialKind = campDicMaterialKind?.mc,  // materialKind -> materialKind����
                dangerousAttr = campDicDangerousAttr?.mc  // dangerousAttr -> dangerousAttr����
        )
    }

    fun getStatisticsByMaterialKind(campId: String): Map<String?, MaterialStatistics> {
        val materials = campWarehouseMaterialRepository.findByCampId(campId)
        val materialsGroupByMaterialKind = materials.groupBy { it.materialKind }
        return materialsGroupByMaterialKind.mapValues { (_, materials) ->
            val quantity = materials.size
            val area = materials.sumByDouble { it.warArea?.toDouble()?:0.0 }
            MaterialStatistics(quantity = quantity, area = area) }
    }

    fun getStatisticsByDangerousAttr(campId: String): Map<String?, MaterialStatistics> {
        val materials = campWarehouseMaterialRepository.findByCampId(campId)
        val materialGroupByDangerousAttr = materials.groupBy { it.dangerousAttr }
        return materialGroupByDangerousAttr.mapValues { (_, materials) ->
            val quantity = materials.size
            val area = materials.sumByDouble { it.warArea?.toDouble()?:0.0 }
            MaterialStatistics(quantity = quantity, area = area) }
    }
}
package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampTrainingField
import gtcloud.yqbjgh.repositories.CampDicTraUsingConditionRepository
import gtcloud.yqbjgh.repositories.CampDicTrafieldKindRepository
import gtcloud.yqbjgh.repositories.CampTrainingFieldRepository
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CampTrainingFieldService {

    @Autowired
    lateinit var campTrainingFieldRepository: CampTrainingFieldRepository

    @Autowired
    lateinit var campDicTrafieldKindRepository: CampDicTrafieldKindRepository

    @Autowired
    lateinit var campDicTraUsingConditionRepository: CampDicTraUsingConditionRepository

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    fun getCampTrainingField(campId: String): List<CampTrainingField> {
        return campTrainingFieldRepository.findByCampId(campId)
                .map { convert(it) }
    }

    fun convert(campTrainingField: CampTrainingField): CampTrainingField {
        val txzhTsBddwml = txzhTsBddwmlRepository
                .findByIdOrNull(campTrainingField.managementUnit ?: "")

        val campDicTrafieldKind = campDicTrafieldKindRepository
                .findByIdOrNull(campTrainingField.traKind ?: "")

        val campDicTraUsingCondition = campDicTraUsingConditionRepository
                .findByIdOrNull(campTrainingField.traUsingCondition ?: "")

        return campTrainingField.copy(
                managementUnit = txzhTsBddwml?.mc,  // managementUnit -> bdÃû³Æ
                traKind = campDicTrafieldKind?.mc,  // traKind -> traKindÃû³Æ
                traUsingCondition = campDicTraUsingCondition?.mc  // traUsingCondition -> traUsingConditionÃû³Æ
        )
    }
}
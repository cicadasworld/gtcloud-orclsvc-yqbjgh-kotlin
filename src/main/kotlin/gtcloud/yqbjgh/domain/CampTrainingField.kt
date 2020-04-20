package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampTrainingField(

    @Id
    val jlbm: String,

    val campId: String?,

    val traName: String?,

    val traKind: String?,

    val traArea: Float?,

    val traUsingCondition: String?,

    val managementUnit: String?
)

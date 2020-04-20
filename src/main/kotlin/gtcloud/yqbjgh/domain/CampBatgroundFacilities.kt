package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampBatgroundFacilities(

    @Id
    val jlbm: String,

    val campId: String?,

    val batName: String?,

    val batKind: String?,

    val batArea: Float?,

    val batFunction: String?,

    val managementUnit: String?
)

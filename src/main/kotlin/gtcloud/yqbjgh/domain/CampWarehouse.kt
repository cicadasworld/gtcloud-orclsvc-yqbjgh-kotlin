package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampWarehouse(

    @Id
    val jlbm: String,

    val apartId: String?,

    val warKind: String?,

    val warName: String?,

    val campId: String?,

    val managementUnit: String?
)

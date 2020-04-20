package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampWarehouseMaterial(

    @Id
    val jlbm: String,

    val warArea: Float?,

    val materialKind: String?,

    val dangerousAttr: String?,

    val warehouseId: String?,

    val ccwzsl: Float?,

    val campId: String?
)

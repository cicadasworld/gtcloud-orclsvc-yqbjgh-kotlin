package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampLocation(

    @Id
    val jlbm: String,

    val nm: String?,

    val campWord: String?,

    val campCode: String?,

    val campKind: String?,

    val adminDivision: String?,

    val detailAddress: String?,

    val campArea: Float?,

    val siteKind: String?,

    val watersupplyMode: String?,

    val elecsupplyMode: String?,

    val gassupplyMode: String?,

    val heatsupplyMode: String?,

    val realorvirtual: String?
)

package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "V_CAMP_LOCATION")
data class VCampLocation(

    @Transient
    val bigUnit: String?,

    val bdxh: String,

    val bdnm: String,

    val bdmc: String?,

    val jlbm: String?,

    @Id
    val dknm: String?,

    val dkmc: String?,

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

    val lineColor: String?,

    val fillColor: String?,

    val fillOpacity: String?,

    val realorvirtual: String?
)

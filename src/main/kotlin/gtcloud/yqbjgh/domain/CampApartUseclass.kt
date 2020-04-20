package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampApartUseclass(

    @Id
    val jlbm: String,

    val apartId: String?,

    val barrackUseClass: String?,

    val barrackUseArea: Float?,

    val barrackUseUnit: String?,

    @Transient
    val apartName: String?
)

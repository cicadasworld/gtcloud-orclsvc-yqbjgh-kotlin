package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TxzhTsBddwml(

    @Id
    val nm: String,

    val mc: String?,

    val xh: String,

    val bdhfnm: String?,

    val bdhf: String?,

    val bdjc: String?,

    val bzxh: String?,

    val bzfh: String?,

    val bzjc: String?,

    val zzbd: String?,

    val parentnm: String?,

    val resident: String?
)

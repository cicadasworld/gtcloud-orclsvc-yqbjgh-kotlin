package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TxzyTsZbgc(

    @Id
    val nm: String,

    val mc: String?,

    val xh: String?,

    val dh: String?,

    val sfjd: String?,

    val sfysj: String?,

    val sjcjdwnm: String?,

    val parentnm: String?,

    val sjcjdwxh: String?
)

package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampLocationKind(

    @Id
    val jlbm: String,

    val campId: String,

    val campKindNm: String?
)

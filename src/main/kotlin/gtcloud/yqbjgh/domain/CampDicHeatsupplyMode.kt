package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class CampDicHeatsupplyMode(

    @Id
    val nm: String,

    val xh: String,

    val mc: String

)
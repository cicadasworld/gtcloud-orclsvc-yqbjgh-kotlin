package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "V_APART_COORDINATE_JSON")
data class VApartCoordinateJson(

    @Id
    val apartId: String,

    val campId: String,

    val apartInfo: String
)

package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "V_APART_COORDINATE")
data class VApartCoordinate(

    @Id
    val apartId: String,

    val campId: String?,

    val campWord: String?,

    val campCode: String?,

    val apartName: String?,

    val jlbm: String?,

    @Column(name = "COOR_X")
    val coorX: String?,

    @Column(name = "COOR_Y")
    val coorY: String?
)

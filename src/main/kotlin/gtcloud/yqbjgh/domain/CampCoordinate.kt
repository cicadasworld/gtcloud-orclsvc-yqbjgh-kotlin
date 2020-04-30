package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampCoordinate(

    @Id
    val jlbm: String,

    val fid: String?,

    val coordinateNum: Int?,

    @Column(name = "COOR_X")
    val coorX: String?,

    @Column(name = "COOR_Y")
    val coorY: String?,

    @Column(name = "CENTER_X")
    val centerX: String?,

    @Column(name = "CENTER_Y")
    val centerY: String?,

    val sjcjry: String? = ""
)

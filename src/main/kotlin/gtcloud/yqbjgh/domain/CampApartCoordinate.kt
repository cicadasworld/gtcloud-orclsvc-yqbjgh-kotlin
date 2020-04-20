package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampApartCoordinate(

    @Id
    val jlbm: String,

    val apartId: String?,

    val coordinateNum: Int?,

    @Column(name = "COOR_X")
    val coorX: String?,

    @Column(name = "COOR_Y")
    val coorY: String?,

    val coorLength: String?,

    val coorHeigh: String?
)

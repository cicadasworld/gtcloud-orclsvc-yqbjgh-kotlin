package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampApartBuilding(

    @Id
    val jlbm: String,

    val apartName: String?,

    val floorArea: Float?,

    val usingStatus: String?,

    val floorlevelUp: Int?,

    val floorlevelDown: Int?,

    val buildingStructure: String?,

    val apartNum: String?,

    val qualityGrade: String?,

    val elevatorNum: Int?,

    val houseAmount: Int?,

    val apartCellAmount: Int?,

    val floorYear: String?,

    val campId: String?,

    val usecalss1: String?,

    val useclassArea2: Float?,

    val useclassArea1: Float?,

    val usecalss2: String?,

    @Transient
    val coorX: String?,

    @Transient
    val coorY: String?,

    @Transient
    val coordinateId: String?
)

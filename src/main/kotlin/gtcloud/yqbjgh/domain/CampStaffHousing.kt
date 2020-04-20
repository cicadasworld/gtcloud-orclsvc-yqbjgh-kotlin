package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class CampStaffHousing(

    @Id
    val jlbm: String,

    val apartId: String?,

    val houseKind: String?,

    val staffKind: String?,

    val houseStandardGrade: String?,

    val houseStandardArea: Float?,

    @Column(name = "STUFF_NAME")
    val staffName: String?,

    val sex: String?,

    val idNum: String?,

    val armyNum: String?,

    val employeeDbNum: String?,

    val workingUnit: String?,

    val mobilePhone: String?,

    val isHousingreform: String?,

    val usualResidentNum: String?,

    val houseNum: String?,

    val cellNum: String?,

    val campId: String?
)

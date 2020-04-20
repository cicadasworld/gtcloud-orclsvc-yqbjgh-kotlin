package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ResidentUnit(

    @Id
    val jlbm: String,

    val bdnm: String?,

    val mission: String?,

    val adminDivision: String?,

    val detailAddress: String?,

    val unitKind: String?,

    val officerRealityNum: Int?,

    val soldierRealityNum: Int?,

    val soldierAuthorizedNum: Int?,

    val civilAuthorizedNum: Int?,

    val employeeAuthorizedNum: Int?,

    val employeeRealityNum: Int?,

    val civilRealityNum: Int?,

    val officerAuthorizedNum: Int?,

    val officerStudentAuthorizedNum: Int?,

    val officerStudentRealityNum: Int?,

    val soldierStudentAuthorizedNum: Int?,

    val soldierStudentRealityNum: Int?,

    val usingCampId: String?,

    val missionEx: String?,

    val remark: String?,

    val unitGrade: String?,

    val usingApartNum: String?
)

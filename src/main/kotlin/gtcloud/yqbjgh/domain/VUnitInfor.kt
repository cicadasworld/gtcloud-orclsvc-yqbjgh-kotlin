package gtcloud.yqbjgh.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "V_UNIT_INFOR")
data class VUnitInfor(

    @Id
    val bdnm: String,

    val xh: String,

    val mc: String?,

    val bdjc: String?,

    val parentnm: String?,

    val jlbm: String?,

    val adminDivision: String?,

    val detailAddress: String?,

    val unitKind: String?,

    val unitKindName: String?,

    val unitGrade: String?,

    val usingCampId: String?,

    val soldierAuthorizedNum: Int?,

    val soldierRealityNum: Int?,

    val employeeAuthorizedNum: Int?,

    val employeeRealityNum: Int?,

    val officerAuthorizedNum: Int?,

    val officerRealityNum: Int?,

    val civilAuthorizedNum: Int?,

    val civilRealityNum: Int?,

    val officerStudentAuthorizedNum: Int?,

    val officerStudentRealityNum: Int?,

    val soldierStudentAuthorizedNum: Int?,

    val soldierStudentRealityNum: Int?,

    val mission: String?,

    val missionEx: String?,

    val remark: String?,

    val usingApartNum: String?,

    @Transient
    val totalSoldierAuthorizedNum: Int?,

    @Transient
    val totalSoldierRealityNum: Int?,

    @Transient
    val totalEmployeeAuthorizedNum: Int?,

    @Transient
    val totalEmployeeRealityNum: Int?,

    @Transient
    val totalOfficerAuthorizedNum: Int?,

    @Transient
    val totalOfficerRealityNum: Int?,

    @Transient
    val totalCivilAuthorizedNum: Int?,

    @Transient
    val totalCivilRealityNum: Int?,

    @Transient
    val totalOfficerStudentAuthorizedNum: Int?,

    @Transient
    val totalOfficerStudentRealityNum: Int?,

    @Transient
    val totalSoldierStudentAuthorizedNum: Int?,

    @Transient
    val totalSoldierStudentRealityNum: Int?
)

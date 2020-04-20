package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.StaffStatistics
import gtcloud.yqbjgh.domain.VUnitInfor
import gtcloud.yqbjgh.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VUnitInforService {

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    @Autowired
    lateinit var vUnitInforRepository: VUnitInforRepository

    @Autowired
    lateinit var residentDicAdminDivisionRepository: ResidentDicAdminDivisionRepository

    @Autowired
    lateinit var residentDicUnitKindRepository: ResidentDicUnitKindRepository

    @Autowired
    lateinit var residentDicUnitGradeRepository: ResidentDicUnitGradeRepository

    fun getVUnitInforBybdnm(bdnm: String, unitKind: String?): VUnitInfor {
        val vUnitInfor = vUnitInforRepository.findById(bdnm).get()
        return convert(vUnitInfor, unitKind)
    }

    fun convert(vUnitInfor: VUnitInfor, unitKind: String?): VUnitInfor {
        val residentDicAdminDivision = residentDicAdminDivisionRepository
                .findById(vUnitInfor.adminDivision ?: "").orElse(null)

        val residentDicUnitKind = residentDicUnitKindRepository
                .findById(vUnitInfor.unitKind ?: "").orElse(null)

        val residentDicUnitGrade = residentDicUnitGradeRepository
                .findById(vUnitInfor.unitGrade ?: "").orElse(null)

        val vUnitInfors = vUnitInforRepository.findByXh(vUnitInfor.xh)

        val matchedUnits = if (unitKind != null && unitKind.isNotEmpty()) {
            vUnitInfors.filter { it.unitKind == unitKind }
        } else {
            vUnitInfors
        }

        val totalSoldierAuthorizedNum = matchedUnits.sumBy { it.soldierAuthorizedNum ?: 0 }
        val totalSoldierRealityNum = matchedUnits.sumBy { it.soldierRealityNum ?: 0 }
        val totalEmployeeAuthorizedNum = matchedUnits.sumBy { it.employeeAuthorizedNum ?: 0 }
        val totalEmployeeRealityNum = matchedUnits.sumBy { it.employeeRealityNum ?: 0 }
        val totalOfficerAuthorizedNum = matchedUnits.sumBy { it.officerAuthorizedNum ?: 0 }
        val totalOfficerRealityNum = matchedUnits.sumBy { it.officerRealityNum ?: 0 }
        val totalCivilAuthorizedNum = matchedUnits.sumBy { it.civilAuthorizedNum ?: 0 }
        val totalCivilRealityNum = matchedUnits.sumBy { it.civilRealityNum ?: 0 }
        val totalOfficerStudentAuthorizedNum = matchedUnits.sumBy { it.officerStudentAuthorizedNum
                ?: 0 }
        val totalOfficerStudentRealityNum = matchedUnits.sumBy { it.officerStudentRealityNum ?: 0 }
        val totalSoldierStudentAuthorizedNum = matchedUnits.sumBy { it.soldierStudentAuthorizedNum
                ?: 0 }
        val totalSoldierStudentRealityNum = matchedUnits.sumBy { it.soldierStudentRealityNum ?: 0 }

        return vUnitInfor.copy(
                adminDivision = residentDicAdminDivision?.mc,  // adminDivision -> adminDivisionÃû³Æ
                unitKind = residentDicUnitKind?.mc,  // unitKind -> unitKindÃû³Æ
                unitGrade = residentDicUnitGrade?.mc, // unitGrade -> unitGradeÃû³Æ
                totalSoldierAuthorizedNum = totalSoldierAuthorizedNum,
                totalSoldierRealityNum = totalSoldierRealityNum,
                totalEmployeeAuthorizedNum = totalEmployeeAuthorizedNum,
                totalEmployeeRealityNum = totalEmployeeRealityNum,
                totalOfficerAuthorizedNum = totalOfficerAuthorizedNum,
                totalOfficerRealityNum = totalOfficerRealityNum,
                totalCivilAuthorizedNum = totalCivilAuthorizedNum,
                totalCivilRealityNum = totalCivilRealityNum,
                totalOfficerStudentAuthorizedNum = totalOfficerStudentAuthorizedNum,
                totalOfficerStudentRealityNum = totalOfficerStudentRealityNum,
                totalSoldierStudentAuthorizedNum = totalSoldierStudentAuthorizedNum,
                totalSoldierStudentRealityNum = totalSoldierStudentRealityNum
        )
    }

    fun getVUnitInforByCampId(campId: String): StaffStatistics {
        val vUnitInfors = vUnitInforRepository.findByUsingCampId(campId)
        val bdnms = vUnitInfors.flatMap { txzhTsBddwmlRepository.findBdnmFamily(it.bdnm) }
        val matchedUnits = bdnms.map { vUnitInforRepository.findById(it).get() }.filter { it.usingCampId == campId }
        return staffStatistics(matchedUnits)
    }

    fun getVUnitInforByApartNum(apartNum: String): StaffStatistics {
        val vUnitInfors = vUnitInforRepository.findByUsingApartNum(apartNum)
        val bdnms = vUnitInfors.flatMap { txzhTsBddwmlRepository.findBdnmFamily(it.bdnm) }
        val matchedUnits = bdnms.map { vUnitInforRepository.findById(it).get() }.filter { it.usingApartNum == apartNum }
        return staffStatistics(matchedUnits)
    }

    private fun staffStatistics(matchedUnits: List<VUnitInfor>): StaffStatistics {
        val totalSoldierAuthorizedNum = matchedUnits.sumBy { it.soldierAuthorizedNum ?: 0 }
        val totalSoldierRealityNum = matchedUnits.sumBy { it.soldierRealityNum ?: 0 }
        val totalEmployeeAuthorizedNum = matchedUnits.sumBy { it.employeeAuthorizedNum ?: 0 }
        val totalEmployeeRealityNum = matchedUnits.sumBy { it.employeeRealityNum ?: 0 }
        val totalOfficerAuthorizedNum = matchedUnits.sumBy { it.officerAuthorizedNum ?: 0 }
        val totalOfficerRealityNum = matchedUnits.sumBy { it.officerRealityNum ?: 0 }
        val totalCivilAuthorizedNum = matchedUnits.sumBy { it.civilAuthorizedNum ?: 0 }
        val totalCivilRealityNum = matchedUnits.sumBy { it.civilRealityNum ?: 0 }
        val totalOfficerStudentAuthorizedNum = matchedUnits.sumBy { it.officerStudentAuthorizedNum ?: 0 }
        val totalOfficerStudentRealityNum = matchedUnits.sumBy { it.officerStudentRealityNum ?: 0 }
        val totalSoldierStudentAuthorizedNum = matchedUnits.sumBy { it.soldierStudentAuthorizedNum ?: 0 }
        val totalSoldierStudentRealityNum = matchedUnits.sumBy { it.soldierStudentRealityNum ?: 0 }

        return StaffStatistics(
                totalSoldierAuthorizedNum = totalSoldierAuthorizedNum,
                totalSoldierRealityNum = totalSoldierRealityNum,
                totalEmployeeAuthorizedNum = totalEmployeeAuthorizedNum,
                totalEmployeeRealityNum = totalEmployeeRealityNum,
                totalOfficerAuthorizedNum = totalOfficerAuthorizedNum,
                totalOfficerRealityNum = totalOfficerRealityNum,
                totalCivilAuthorizedNum = totalCivilAuthorizedNum,
                totalCivilRealityNum = totalCivilRealityNum,
                totalOfficerStudentAuthorizedNum = totalOfficerStudentAuthorizedNum,
                totalOfficerStudentRealityNum = totalOfficerStudentRealityNum,
                totalSoldierStudentAuthorizedNum = totalSoldierStudentAuthorizedNum,
                totalSoldierStudentRealityNum = totalSoldierStudentRealityNum
        )
    }

    fun getVUnitInforByUnitKind(unitKind: String): StaffStatistics {
        val vUnitInfors = vUnitInforRepository.findAll()
        val matchedUnits = vUnitInfors.filter { it.unitKind!=null && it.unitKind.startsWith(unitKind)}
        return staffStatistics(matchedUnits)
    }
}
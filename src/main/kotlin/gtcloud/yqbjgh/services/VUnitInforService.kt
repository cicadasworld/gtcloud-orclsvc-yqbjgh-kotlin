package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.StaffStatistics
import gtcloud.yqbjgh.domain.UnitNode
import gtcloud.yqbjgh.domain.VUnitInfor
import gtcloud.yqbjgh.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
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

    @Autowired
    lateinit var campApartBuildingRepository: CampApartBuildingRepository

    fun getVUnitInforBybdnm(bdnm: String, unitKind: String?): VUnitInfor {
        val vUnitInfor = vUnitInforRepository.findById(bdnm).get()
        return convert(vUnitInfor, unitKind)
    }

    fun convert(vUnitInfor: VUnitInfor, unitKind: String?): VUnitInfor {
        val residentDicAdminDivision = residentDicAdminDivisionRepository
                .findByIdOrNull(vUnitInfor.adminDivision ?: "")

        val residentDicUnitKind = residentDicUnitKindRepository
                .findByIdOrNull(vUnitInfor.unitKind ?: "")

        val residentDicUnitGrade = residentDicUnitGradeRepository
                .findByIdOrNull(vUnitInfor.unitGrade ?: "")

        val vUnitInfors = vUnitInforRepository.findByXhFamily(vUnitInfor.xh)

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

    fun getVUnitInforByApartId(apartId: String): StaffStatistics {
        val apartBuilding = campApartBuildingRepository.findByIdOrNull(apartId)
        val matchedUnits = vUnitInforRepository.findAll().filter {
            it.usingCampId == apartBuilding?.campId && it.usingApartNum == apartBuilding?.apartNum
        }
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
        val matchedUnits = vUnitInforRepository.findByUnitKindFamily(unitKind)
        return staffStatistics(matchedUnits)
    }

    fun getRootBdnmByUnitKindNm(unitKindNm: String): List<UnitNode> {
        val vUnitInfors = vUnitInforRepository.findByUnitKindFamily(unitKindNm)
        val xhs = vUnitInfors.map { getRootXh(it.xh) }.distinct()
        return xhs.map {
            val unitInfo = vUnitInforRepository.findByXh(it)
            UnitNode(
                nm = unitInfo.bdnm,
                xh = it
            )
        }
    }

    fun getRootXh(text: String): String {
        if (text.length >= 4) {
            return text.substring(0, 4)
        }
        return text
    }
}
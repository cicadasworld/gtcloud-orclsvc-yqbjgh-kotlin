package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.Node
import gtcloud.yqbjgh.domain.ResidentUnit
import gtcloud.yqbjgh.domain.VUnitInfor
import gtcloud.yqbjgh.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ResidentUnitService {

    @Autowired
    lateinit var residentUnitRepository: ResidentUnitRepository

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    @Autowired
    lateinit var residentDicUnitGradeRepository: ResidentDicUnitGradeRepository

    @Autowired
    lateinit var residentDicAdminDivisionRepository: ResidentDicAdminDivisionRepository

    @Autowired
    lateinit var residentDicUnitKindRepository: ResidentDicUnitKindRepository

    @Autowired
    lateinit var vUnitInforRepository: VUnitInforRepository

    @Autowired
    lateinit var campApartBuildingRepository: CampApartBuildingRepository

    fun getResidentUnit(bdnm: String): List<ResidentUnit> {
        return residentUnitRepository.findByBdnm(bdnm)
                .map { convert(it) }
    }

    fun convert(residentUnit: ResidentUnit): ResidentUnit {
        val txzhTsBddwml = txzhTsBddwmlRepository
                .findById(residentUnit.bdnm ?: "").orElse(null)

        val residentDicUnitGrade = residentDicUnitGradeRepository
                .findById(residentUnit.unitGrade ?: "").orElse(null)

        val residentDicAdminDivision = residentDicAdminDivisionRepository
                .findById(residentUnit.adminDivision ?: "").orElse(null)

        val residentDicUnitKind = residentDicUnitKindRepository
                .findById(residentUnit.unitKind ?: "").orElse(null)

        return residentUnit.copy(
                bdnm = txzhTsBddwml?.mc,  // bdnm -> bd名称
                unitGrade = residentDicUnitGrade?.mc,  // unitGrade -> unitGrade名称
                adminDivision = residentDicAdminDivision?.mc, // adminDivision -> adminDivision名称
                unitKind = residentDicUnitKind?.mc // unitKind -> unitKind名称
        )
    }

    fun getResidentUnitByUsingCampId(campId: String): List<Node> {
        val vUnitInfors = vUnitInforRepository.findByUsingCampId(campId)
        val xhs = vUnitInfors.flatMap { splitTextByStep(it.xh) }.distinct()
        val nodes = xhs.map { vUnitInforRepository.findByXh(it) }
                .map { convertToNode(it) }
                .sortedBy { it.xh }
        val associateNodes = associate(nodes)
        return associateNodes.filter { it.parent == null }
    }

    fun convertToNode(unit: VUnitInfor): Node {
        return Node(bdjc = unit.bdjc, mc = unit.mc, xh = unit.xh, unitKindName = unit.unitKindName)
    }

    fun associate(nodes: List<Node>): List<Node> {
        for (i in nodes.indices) {
            val n = nodes[i]
            for (j in i + 1 until nodes.size) {
                val m = nodes[j]
                if (m.xh.startsWith(n.xh) && m.xh.length == n.xh.length + 2) {
                    n.children.add(m)
                    m.parent = n
                } else if (n.xh.startsWith(m.xh) && n.xh.length == m.xh.length + 2) {
                    m.children.add(n)
                    n.parent = m
                }
            }
        }
        return nodes
    }

    fun splitTextByStep(text: String): List<String> {
        val split = mutableListOf<String>()
        val size = (text.length - 4) / 2
        for (i in 0..size) {
            split.add(text.substring(0, 4 + 2 * i))
        }
        return split
    }

    fun getResidentUnitByUsingApartId(apartId: String): List<Node> {
        val apartBuilding = campApartBuildingRepository.findByIdOrNull(apartId)
        val matchedUnits = vUnitInforRepository.findAll().filter {
            it.usingCampId == apartBuilding?.campId && it.usingApartNum == apartBuilding?.apartNum
        }
        val xhs = matchedUnits.flatMap { splitTextByStep(it.xh) }.distinct()
        val nodes = xhs.map { vUnitInforRepository.findByXh(it) }
                .map { convertToNode(it) }
                .sortedBy { it.xh }
        val associateNodes = associate(nodes)
        return associateNodes.filter { it.parent == null }
    }
}
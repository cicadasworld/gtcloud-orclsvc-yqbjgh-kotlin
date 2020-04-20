package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.Node
import gtcloud.yqbjgh.domain.ResidentUnit
import gtcloud.yqbjgh.domain.VUnitInfor
import gtcloud.yqbjgh.repositories.*
import org.springframework.beans.factory.annotation.Autowired
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

    fun getResidentUnitByCampId(campId: String): List<Node> {
        val nodes = vUnitInforRepository.findByUsingCampId(campId)
                .map { convertToNode(it) }
                .sortedBy { it.xh }
        val associateNodes = associate(nodes)
        return associateNodes.filter { it.parent == null }
    }

    fun convertToNode(unit: VUnitInfor): Node {
        return Node(bdjc = unit.bdjc, mc = unit.mc, xh = unit.xh)
    }

    fun associate(nodes: List<Node>): List<Node> {
        for (i in nodes.indices) {
            val n = nodes[i]
            for (j in i + 1 until nodes.size) {
                val m = nodes[j]
                if (m.xh.startsWith(n.xh)) {
                    n.children.add(m)
                    m.parent = n
                } else if (n.xh.startsWith(m.xh)) {
                    m.children.add(n)
                    n.parent = m
                }
            }
        }
        return nodes
    }
}
package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.UnitNode
import gtcloud.yqbjgh.domain.VUnitInfor
import gtcloud.yqbjgh.repositories.ResidentDicUnitKindRepository
import gtcloud.yqbjgh.repositories.VCampLocationRepository
import gtcloud.yqbjgh.repositories.VUnitInforRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UnitTreeService {

    @Autowired
    lateinit var vUnitInforRepository: VUnitInforRepository

    @Autowired
    lateinit var vCampLocationRepository: VCampLocationRepository

    @Autowired
    lateinit var residentDicUnitKindRepository: ResidentDicUnitKindRepository

    fun getUnitTree(): List<UnitNode> {
        val xhStepLen = 2
        val tree = ArrayList<UnitNode>()
        val levelToUnitNodes = HashMap<Int, UnitNode>()
        val unitEntities = vUnitInforRepository.findAllOderByXh()
        var nLastLevel = 0
        var strLastXh = ""
        var currentNode: UnitNode
        for (unitEntity in unitEntities) {
            val xh = unitEntity.xh
            val unitNode = UnitNode(
                    xh = xh,
                    nm = unitEntity.bdnm,
                    mc = unitEntity.mc,
                    bdjc = unitEntity.bdjc,
                    parentnm = unitEntity.parentnm,
                    unitKind = unitEntity.unitKind,
                    unitKindName = unitEntity.unitKindName
            )
            val level = xh.length / xhStepLen - 1
            if (strLastXh.isEmpty()) {
                tree.add(unitNode)
                currentNode = tree[tree.size - 1]
                levelToUnitNodes[level] = currentNode
            } else {
                var parentLevel: Int
                var parent: UnitNode?
                if (xh.length == strLastXh.length) {
                    parentLevel = nLastLevel - 1
                    parent = levelToUnitNodes[parentLevel]
                    if (parent != null) {
                        val children = parent.children
                        children.add(unitNode)
                        levelToUnitNodes[level] = children[children.size - 1]
                    }
                } else if (xh.length > strLastXh.length) {
                    parentLevel = nLastLevel
                    parent = levelToUnitNodes[parentLevel]
                    if (parent != null) {
                        val children = parent.children
                        children.add(unitNode)
                        levelToUnitNodes[level] = children[children.size - 1]
                    }
                } else {
                    parentLevel = level - 1
                    if (level == 0) {
                        tree.add(unitNode)
                        currentNode = tree[tree.size - 1]
                        levelToUnitNodes[level] = currentNode
                    } else {
                        parent = levelToUnitNodes[parentLevel]
                        if (parent != null) {
                            val children = parent.children
                            children.add(unitNode)
                            levelToUnitNodes[level] = children[children.size - 1]
                        }
                    }
                }
            }
            nLastLevel = xh.length / xhStepLen - 1
            strLastXh = xh
        }
        return tree
    }

    fun getMnrUnitTree(): List<UnitNode> {
        val unitTree = getUnitTree()
        val unitMap = getMnrUnitMap()
        return getMatchedUnitTree(unitTree, unitMap)
    }

    fun getUseUnitTree(): List<UnitNode> {
        val unitTree = getUnitTree()
        val unitMap = getUseUnitMap()
        return getMatchedUnitTree(unitTree, unitMap)
    }

    private fun getMnrUnitMap(): Map<String, String> {
        val vCampLocations = vCampLocationRepository.findAllOrderByBdxh()
        return vCampLocations.associate { it.bdxh to it.bdnm }
    }

    private fun getUseUnitMap(): Map<String, String> {
        val unitInfors = vUnitInforRepository.findByUsingCampIdOrderByXh()
        return unitInfors.associate { it.xh to it.bdnm }
    }

    private fun filterUnitNodes(unitNodes: List<UnitNode>, unitMap: Map<String, String>): MutableList<UnitNode> {
        val newUnitNodes = ArrayList<UnitNode>()
        for (unitNode in unitNodes) {
            val children = unitNode.children
            if (children.size > 0) {
                val newChildren = filterUnitNodes(children, unitMap)
                if (newChildren.size > 0) {
                    val newUnitNode = UnitNode(
                            xh = unitNode.xh,
                            nm = unitNode.nm,
                            mc = unitNode.mc,
                            bdjc = unitNode.bdjc,
                            parentnm = unitNode.parentnm,
                            unitKind = unitNode.unitKind,
                            unitKindName = unitNode.unitKindName,
                            children = newChildren
                    )
                    newUnitNodes.add(newUnitNode)
                } else {
                    val nm = unitMap[unitNode.xh]
                    if (nm != null && nm.isNotEmpty()) {
                        val newUnitNode = UnitNode(
                                xh = unitNode.xh,
                                nm = unitNode.nm,
                                mc = unitNode.mc,
                                bdjc = unitNode.bdjc,
                                parentnm = unitNode.parentnm,
                                unitKind = unitNode.unitKind,
                                unitKindName = unitNode.unitKindName,
                                children = ArrayList()
                        )
                        newUnitNodes.add(newUnitNode)
                    }
                }
            } else {
                val nm = unitMap[unitNode.xh]
                if (nm != null && nm.isNotEmpty()) {
                    val newUnitNode = UnitNode(
                            xh = unitNode.xh,
                            nm = unitNode.nm,
                            mc = unitNode.mc,
                            bdjc = unitNode.bdjc,
                            parentnm = unitNode.parentnm,
                            unitKind = unitNode.unitKind,
                            unitKindName = unitNode.unitKindName,
                            children = ArrayList()
                    )
                    newUnitNodes.add(newUnitNode)
                }
            }
        }
        return newUnitNodes
    }

    private fun getMatchedUnitTree(unitTree: List<UnitNode>, unitMap: Map<String, String>): List<UnitNode> {
        val matchedUnitTree = ArrayList<UnitNode>()
        for (unitNode in unitTree) {
            val children = unitNode.children
            if (children.size > 0) {
                val newChildren = filterUnitNodes(children, unitMap)
                if (newChildren.size > 0) {
                    val newUnitNode = UnitNode(
                            xh = unitNode.xh,
                            nm = unitNode.nm,
                            mc = unitNode.mc,
                            bdjc = unitNode.bdjc,
                            parentnm = unitNode.parentnm,
                            unitKind = unitNode.unitKind,
                            unitKindName = unitNode.unitKindName,
                            children = newChildren
                    )
                    matchedUnitTree.add(newUnitNode)
                } else {
                    val nm = unitMap[unitNode.xh]
                    if (nm != null && nm.isNotEmpty()) {
                        val newUnitNode = UnitNode(
                                xh = unitNode.xh,
                                nm = unitNode.nm,
                                mc = unitNode.mc,
                                bdjc = unitNode.bdjc,
                                parentnm = unitNode.parentnm,
                                unitKind = unitNode.unitKind,
                                unitKindName = unitNode.unitKindName,
                                children = ArrayList()
                        )
                        matchedUnitTree.add(newUnitNode)
                    }
                }
            } else {
                val nm = unitMap[unitNode.xh]
                if (nm != null && nm.isNotEmpty()) {
                    val newUnitNode = UnitNode(
                            xh = unitNode.xh,
                            nm = unitNode.nm,
                            mc = unitNode.mc,
                            bdjc = unitNode.bdjc,
                            parentnm = unitNode.parentnm,
                            unitKind = unitNode.unitKind,
                            unitKindName = unitNode.unitKindName,
                            children = ArrayList()
                    )
                    matchedUnitTree.add(newUnitNode)
                }
            }
        }
        return matchedUnitTree
    }

    private fun getUnitKindDicTree(): List<UnitNode> {
        val nmStepLen = 2
        val tree = ArrayList<UnitNode>()
        val levelToUnitNodes = HashMap<Int, UnitNode>()
        val dicUnitKinds = residentDicUnitKindRepository.findAllOrderByNm()
        var nLastLevel = 0
        var strLastXh = ""
        var currentNode: UnitNode
        for (dicUnitKind in dicUnitKinds) {
            val nm = dicUnitKind.nm
            val unitNode = UnitNode(
                    xh = dicUnitKind.xh,
                    nm = nm,
                    mc = dicUnitKind.mc,
                    bdjc = dicUnitKind.mc,
                    dicUnitKind = true
            )
            val level = nm.length / nmStepLen - 1
            if (strLastXh.isEmpty()) {
                tree.add(unitNode)
                currentNode = tree[tree.size - 1]
                levelToUnitNodes[level] = currentNode
            } else {
                var parentLevel: Int
                var parent: UnitNode?
                if (nm.length == strLastXh.length) {
                    parentLevel = nLastLevel - 1
                    parent = levelToUnitNodes[parentLevel]
                    if (parent != null) {
                        val children = parent.children
                        children.add(unitNode)
                        levelToUnitNodes[level] = children[children.size - 1]
                    }
                } else if (nm.length > strLastXh.length) {
                    parentLevel = nLastLevel
                    parent = levelToUnitNodes[parentLevel]
                    if (parent != null) {
                        val children = parent.children
                        children.add(unitNode)
                        levelToUnitNodes[level] = children[children.size - 1]
                    }
                } else {
                    parentLevel = level - 1
                    if (level == 0) {
                        tree.add(unitNode)
                        currentNode = tree[tree.size - 1]
                        levelToUnitNodes[level] = currentNode
                    } else {
                        parent = levelToUnitNodes[parentLevel]
                        if (parent != null) {
                            val children = parent.children
                            children.add(unitNode)
                            levelToUnitNodes[level] = children[children.size - 1]
                        }
                    }
                }
            }
            nLastLevel = nm.length / nmStepLen - 1
            strLastXh = nm
        }
        return tree
    }

    fun getLeafNodes(tree: List<UnitNode>): List<UnitNode> {
        val leafNodes = ArrayList<UnitNode>()
        for (kindNode in tree) {
            searchLeafNodes(kindNode.children, leafNodes)
        }
        return leafNodes
    }

    fun searchLeafNodes(nodes: List<UnitNode>, leafs: MutableList<UnitNode>) {
        for (node in nodes) {
            val children = node.children
            if (children.isEmpty()) {
                leafs.add(node)
            } else {
                searchLeafNodes(children, leafs)
            }
        }
    }

    fun getKindUnitTree(): List<UnitNode> {
        val allUnitInfors = vUnitInforRepository.findAllOderByXh()
        val xhToUnitInfor = allUnitInfors.associateBy({it.xh}, {it})
        val unitKindTree = getUnitKindDicTree()
        val leafs = getLeafNodes(unitKindTree)
        val deferred = leafs.map {
            GlobalScope.async {
                val unitKind = it.nm
                it.children = getTreeByUnitKind(unitKind, xhToUnitInfor)
                it
            }
        }
        runBlocking {
            deferred.map { it.await() }
        }
        return unitKindTree
    }

    private fun getTreeByUnitKind(unitKind: String, xhToUnitInfor: Map<String, VUnitInfor>): MutableList<UnitNode> {
        val vUnitInfors = vUnitInforRepository.findByUnitKind(unitKind)
        val xhs = vUnitInfors.flatMap { splitTextByStep(it.xh) }.distinct()
        val nodes = xhs.mapNotNull { it ->
            xhToUnitInfor[it]?.let { convertToNode(it) }
        }
        val associateNodes = associate(nodes)
        return associateNodes.filter { it.parent == null }.toMutableList()
    }

    private fun convertToNode(unit: VUnitInfor): UnitNode {
        return UnitNode(
                xh = unit.xh,
                nm = unit.bdnm,
                mc = unit.mc,
                bdjc = unit.bdjc,
                parentnm = unit.parentnm,
                unitKind = unit.unitKind,
                unitKindName = unit.unitKindName
        )
    }

    private fun associate(nodes: List<UnitNode>): List<UnitNode> {
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
        val split = ArrayList<String>()
        val size = (text.length - 4) / 2
        for (i in 0..size) {
            split.add(text.substring(0, 4 + 2 * i))
        }
        return split
    }
}
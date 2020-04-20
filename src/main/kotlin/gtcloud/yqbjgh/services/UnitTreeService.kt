package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.UnitNode
import gtcloud.yqbjgh.repositories.ResidentDicUnitKindRepository
import gtcloud.yqbjgh.repositories.VCampLocationRepository
import gtcloud.yqbjgh.repositories.VUnitInforRepository
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

    fun getUnitKindDicTree(): List<UnitNode> {
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
                    bdjc = dicUnitKind.mc
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

    private fun filterNotesByUnitKind(unitNodes: MutableList<UnitNode>, unitKind: String): MutableList<UnitNode> {
        val result = ArrayList<UnitNode>()
        for (node in unitNodes) {
            val children = node.children
            if (children.isEmpty()) {
                if (node.unitKind == unitKind) {
                    result.add(node)
                }
            } else {
                val newChildren = filterNotesByUnitKind(children, unitKind)
                if (newChildren.isEmpty()) {
                    if (node.unitKind == unitKind) {
                        val newNode = UnitNode(
                                xh = node.xh,
                                bdjc = node.bdjc,
                                mc = node.mc,
                                nm = node.nm,
                                unitKind = node.unitKind,
                                unitKindName = node.unitKindName
                        )
                        newNode.children = newChildren
                        result.add(newNode)
                    }
                } else {
                    val newNode = UnitNode(
                            xh = node.xh,
                            bdjc = node.bdjc,
                            mc = node.mc,
                            nm = node.nm,
                            unitKind = node.unitKind,
                            unitKindName = node.unitKindName
                    )
                    newNode.children = newChildren
                    result.add(newNode)
                }
            }
        }
        return result
    }

    private fun getTreeByUnitKind(tree: MutableList<UnitNode>, unitKind: String): MutableList<UnitNode> {
        val result = ArrayList<UnitNode>()
        for (node in tree) {
            val children = node.children
            if (children.isEmpty()) {
                if (node.unitKind == unitKind) {
                    result.add(node)
                }
            } else {
                val newChildren = filterNotesByUnitKind(children, unitKind)
                if (newChildren.isEmpty()) {
                    if (node.unitKind == unitKind) {
                        val newNode = UnitNode(
                                xh = node.xh,
                                bdjc = node.bdjc,
                                mc = node.mc,
                                nm = node.nm,
                                unitKind = node.unitKind,
                                unitKindName = node.unitKindName
                        )
                        newNode.children = newChildren
                        result.add(newNode)
                    }
                } else {
                    val newNode = UnitNode(
                            xh = node.xh,
                            bdjc = node.bdjc,
                            mc = node.mc,
                            nm = node.nm,
                            unitKind = node.unitKind,
                            unitKindName = node.unitKindName
                    )
                    newNode.children = newChildren
                    result.add(newNode)
                }
            }
        }
        return result
    }

    private fun filterUnitByKind(kindNodes: List<UnitNode>, unitTree: List<UnitNode>) {
        for (kindNode in kindNodes) {
            val children = kindNode.children
            if (children.isEmpty()) {
                val unitNodes = getTreeByUnitKind(unitTree.toMutableList(), kindNode.nm)
                kindNode.children = unitNodes
            } else {
                filterUnitByKind(children, unitTree)
            }
        }
    }

    fun getKindUnitTree(): List<UnitNode> {
        val unitKindTree = getUnitKindDicTree()
        val unitTree = getUnitTree()
        for (kindNode in unitKindTree) {
            val children = kindNode.children
            filterUnitByKind(children, unitTree)
        }
        return unitKindTree
    }
}
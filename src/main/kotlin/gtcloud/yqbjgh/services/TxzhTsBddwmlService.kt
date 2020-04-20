package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.bootstrap.RootIdsBean
import gtcloud.yqbjgh.bootstrap.XhToBigUnitBean
import gtcloud.yqbjgh.domain.TxzhTsBddwml
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TxzhTsBddwmlService {

    @Autowired
    lateinit var bddwmlRepo: TxzhTsBddwmlRepository

    @Autowired
    lateinit var rootIdsBean: RootIdsBean

    @Autowired
    lateinit var xhToBigUnitBean: XhToBigUnitBean

    fun getRootIds(): List<String> {
        val entities = bddwmlRepo.findByParentnmIsNull()
        return entities.map { it.nm }
    }

    fun getBigUnits(xh: String): TxzhTsBddwml? {
        val xhToBigUnit = xhToBigUnitBean.xhToBigUnit
        val values = xhToBigUnit.filterKeys { xh.startsWith(it) }.values
        return if (values.isEmpty()) null else values.first()
    }

    fun getXhToBigUnitMap(): Map<String, TxzhTsBddwml> {
        val rootIds = rootIdsBean.rootIds
        val unitsGroupByXh = rootIds.flatMap { bddwmlRepo.findByParentnm(it) }.groupBy { it.xh }
        return unitsGroupByXh.mapValues { (_, units) -> units[0] }
    }
}
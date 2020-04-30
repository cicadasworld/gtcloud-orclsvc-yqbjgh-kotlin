package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampDicCampKind
import gtcloud.yqbjgh.repositories.CampDicCampKindRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampDicCampKindService {

    @Autowired
    lateinit var repository: CampDicCampKindRepository

    fun listAll(): List<List<CampDicCampKind>> {

        val campDicCampKinds = repository.findAll()
        val nms = campDicCampKinds.filter { it.nm.length == 2 }.map { it.nm }
        val dicList = ArrayList<ArrayList<CampDicCampKind>>()

        for (nm in nms) {
            val newCampDicCampKinds = ArrayList<CampDicCampKind>()
            for (campDicCampKind in campDicCampKinds) {
                if (campDicCampKind.nm.startsWith(nm)) {
                    val newCampDicCampKind = CampDicCampKind(
                            nm = campDicCampKind.nm,
                            xh = campDicCampKind.xh,
                            mc = campDicCampKind.mc
                    )
                    newCampDicCampKinds.add(newCampDicCampKind)
                }
            }
            dicList.add(newCampDicCampKinds)
        }
        return dicList
    }
}
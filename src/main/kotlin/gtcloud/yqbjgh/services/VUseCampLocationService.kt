package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.TxzhTsBddwml
import gtcloud.yqbjgh.domain.VUseCampLocation
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import gtcloud.yqbjgh.repositories.VUseCampLocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VUseCampLocationService {

    @Autowired
    lateinit var xhToBigUnit: Map<String, TxzhTsBddwml>

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    @Autowired
    lateinit var vUseCampLocationRepository: VUseCampLocationRepository

    fun getVUsingCampCampLocationByUseBdnm(id: String): List<VUseCampLocation> {
        val bddwml = txzhTsBddwmlRepository.findByIdOrNull(id)
        return if (bddwml == null) {
            vUseCampLocationRepository.findAll()
                    .filter { it.realorvirtual == "1" }
                    .map { addBigUnit(it) }
        } else {
            vUseCampLocationRepository.findByBdxh(bddwml.xh)
                    .filter { it.realorvirtual == "1" }
                    .map { addBigUnit(it) }
        }
    }

    private fun addBigUnit(vUseCampLocation: VUseCampLocation): VUseCampLocation {
        val bigUnits = getBigUnits(vUseCampLocation.bdxh?:"")
        return vUseCampLocation.copy(bigUnit = bigUnits?.bdjc)
    }

    private fun getBigUnits(xh: String): TxzhTsBddwml? {
        val values = xhToBigUnit.filterKeys { xh.startsWith(it) }.values
        return if (values.isEmpty()) null else values.first()
    }

}
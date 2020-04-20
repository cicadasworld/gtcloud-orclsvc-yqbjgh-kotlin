package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.VUseCampLocation
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import gtcloud.yqbjgh.repositories.VUseCampLocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VUseCampLocationService {

    @Autowired
    lateinit var bddwmlService: TxzhTsBddwmlService

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    @Autowired
    lateinit var vUseCampLocationRepository: VUseCampLocationRepository

    fun getVUsingCampCampLocationByBdnm(id: String): List<VUseCampLocation> {
        val bddwml = txzhTsBddwmlRepository.findByIdOrNull(id)
        return if (bddwml == null) {
            vUseCampLocationRepository.findAll().map { addBigUnit(it) }
        } else {
            vUseCampLocationRepository.findByBdxh(bddwml.xh).map { addBigUnit(it) }
        }
    }

    private fun addBigUnit(vUseCampLocation: VUseCampLocation): VUseCampLocation {
        val bigUnits = bddwmlService.getBigUnits(vUseCampLocation.bdxh?:"")
        return vUseCampLocation.copy(bigUnit = bigUnits?.bdjc)
    }
}
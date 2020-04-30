package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampApartCoordinate
import gtcloud.yqbjgh.domain.CampApartCoordinateDTO
import gtcloud.yqbjgh.domain.VApartCoordinate
import gtcloud.yqbjgh.repositories.CampApartBuildingRepository
import gtcloud.yqbjgh.repositories.CampApartCoordinateRepository
import gtcloud.yqbjgh.repositories.VApartCoordinateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CampApartCoordinateService {

    @Autowired
    lateinit var campApartCoordinateRepository: CampApartCoordinateRepository

    @Autowired
    lateinit var campApartBuildingRepository: CampApartBuildingRepository

    @Autowired
    lateinit var vApartCoordinateRepository: VApartCoordinateRepository

    fun getCampApartCoordinate(apartId: String): List<CampApartCoordinateDTO> {
        val campApartCoordinates = campApartCoordinateRepository.findByApartId(apartId)
        return campApartCoordinates.
                map { convert(it) }
    }

    fun convert(coordinate: CampApartCoordinate): CampApartCoordinateDTO {
        val apartId = coordinate.apartId
        val campApartBuilding = campApartBuildingRepository.findByIdOrNull(apartId ?: "")
        return CampApartCoordinateDTO(
                jlbm = coordinate.jlbm,
                apartId = coordinate.apartId,
                apartName = campApartBuilding?.apartName,
                coordinateNum = coordinate.coordinateNum?.toString(),
                coorX = coordinate.coorX,
                coorY = coordinate.coorY,
                coorLength = coordinate.coorLength,
                coorHeigh = coordinate.coorHeigh
        )
    }

    fun createCampApartCoordinate(dto: CampApartCoordinateDTO): CampApartCoordinateDTO {
        var jlbm = dto.jlbm
        if (jlbm == null || jlbm.isEmpty()) {
            val time = System.currentTimeMillis()
            val randomNum = UUID.randomUUID().toString().replace("-", "")
            jlbm = "t${time}x$randomNum"
        }
        dto.jlbm = jlbm
        val campApartCoordinate = CampApartCoordinate(
                jlbm= jlbm,
                apartId = dto.apartId,
                coordinateNum = dto.coordinateNum?.toInt(),
                coorX = dto.coorX,
                coorY = dto.coorY,
                coorLength = dto.coorLength,
                coorHeigh = dto.coorHeigh
        )
        campApartCoordinateRepository.save(campApartCoordinate)
        return dto
    }

    fun updateCampApartCoordinate(jlbm: String, dto: CampApartCoordinateDTO): CampApartCoordinateDTO {
        val campApartCoordinate = CampApartCoordinate(
                jlbm = jlbm,
                apartId = dto.apartId,
                coordinateNum = dto.coordinateNum?.toInt(),
                coorX = dto.coorX,
                coorY = dto.coorY,
                coorLength = dto.coorLength,
                coorHeigh = dto.coorHeigh
        )
        campApartCoordinateRepository.save(campApartCoordinate)
        return dto
    }

    fun deleteCampApartCoordinate(apartId: String): List<CampApartCoordinateDTO> {
        val campApartCoordinates = campApartCoordinateRepository.findByApartId(apartId)
        return campApartCoordinates.
                map { deleteAndReturn(campApartCoordinateRepository, it) }
    }

    fun deleteAndReturn(campApartCoordinateRepository: CampApartCoordinateRepository,
                        campApartCoordinate: CampApartCoordinate): CampApartCoordinateDTO {
        campApartCoordinateRepository.delete(campApartCoordinate)
        return convert(campApartCoordinate)
    }

    fun getVApartCoordinateByRect(left: Double, bottom: Double, right: Double, top: Double): List<VApartCoordinate> {
        return vApartCoordinateRepository.findByRect(left, bottom, right, top)
    }
}
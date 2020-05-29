package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.*
import gtcloud.yqbjgh.repositories.CampCoordinateRepository
import gtcloud.yqbjgh.repositories.CampLocationRepository
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class CampCoordinateService {

    @Autowired
    lateinit var campCoordinateRepository: CampCoordinateRepository

    @Autowired
    lateinit var campLocationRepository: CampLocationRepository

    @Autowired
    lateinit var geometryFactory: GeometryFactory

    fun deleteByFid(fid: String): List<CampCoordinate> {
        val campCoordinates = campCoordinateRepository.findByFid(fid, Sort.by("coordinateNum"))
        campCoordinates.forEach { campCoordinateRepository.deleteById(it.jlbm) }
        return campCoordinates
    }

    fun getCampCoordinateByFid(fid: String): List<CampCoordinateDTO> {
        val campCoordinates = campCoordinateRepository.findByFid(fid, Sort.by("coordinateNum"))
        return campCoordinates.map { convert(it) }
    }

    fun convert(coordinate: CampCoordinate): CampCoordinateDTO {
        return CampCoordinateDTO(
                jlbm = coordinate.jlbm,
                fid = coordinate.fid,
                coordinateNum = coordinate.coordinateNum?.toString(),
                coorX = coordinate.coorX?:"",
                coorY = coordinate.coorY?:"",
                centerX = coordinate.centerX,
                centerY = coordinate.centerY
        )
    }

    fun saveCampCoordinate(dto: CampCoordinateDTO): CampCoordinateDTO {
        val coordinate = CampCoordinate(
                jlbm = dto.jlbm,
                fid = dto.fid,
                coordinateNum = dto.coordinateNum?.toInt(),
                coorX = dto.coorX,
                coorY = dto.coorY,
                centerX = dto.centerX,
                centerY = dto.centerY
        )
        campCoordinateRepository.save(coordinate)
        return dto
    }

    fun updateCampCoordinate(jlbm: String, dto: CampCoordinateDTO): CampCoordinateDTO {
        val coordinate = CampCoordinate(
                jlbm = jlbm,
                fid = dto.fid,
                coordinateNum = dto.coordinateNum?.toInt(),
                coorX = dto.coorX,
                coorY = dto.coorY,
                centerX = dto.centerX,
                centerY = dto.centerY
        )
        campCoordinateRepository.save(coordinate)
        return dto
    }

    fun updateCampCoordinates(fid: String, dtos: List<CampCoordinateDTO>): List<CampCoordinateDTO> {
        // remove old coordiantes
        this.deleteByFid(fid)

        // calculate centroid
        val coords = mutableListOf<Coordinate>()
        for (dto in dtos) {
            coords.add(Coordinate(dto.coorX.toDouble(), dto.coorY.toDouble()))
        }
        coords.add(Coordinate(dtos[0].coorX.toDouble(), dtos[0].coorY.toDouble()))
        val polygon = geometryFactory.createPolygon(coords.toTypedArray())
        val centroid = polygon.centroid

        for (dto in dtos) {
            dto.fid = fid
            dto.centerX = centroid.x.toString()
            dto.centerY = centroid.y.toString()
            val time = System.currentTimeMillis()
            val randomNum = UUID.randomUUID().toString().replace("-", "")
            val jlbm = "t${time}fid${fid}x$randomNum"
            val coordinate = CampCoordinate(
                jlbm = jlbm,
                fid = fid,
                coordinateNum =dto.coordinateNum?.toInt(),
                coorX = dto.coorX,
                coorY = dto.coorY,
                centerX = centroid.x.toString(),
                centerY = centroid.y.toString(),
                sjcjry = dto.sjcjry
            )
            campCoordinateRepository.save(coordinate)
        }

        return dtos
    }

    fun deleteCampCoordinates(fid: String): List<CampCoordinateDTO> {
        val campCoordinates = this.deleteByFid(fid)
        return campCoordinates.map { convert(it) }
    }

    fun getAllCampLocationInfoList(virtual: Boolean): List<CampLocationInfo> {
        val campLocations = if (virtual) {
            campLocationRepository.findAllVirtualCampLocation()
        } else {
            campLocationRepository.findAll()
        }
        return campLocations.map { getCampLocationInfo(it) }
    }

    fun getCampLocationInfo(campLocation: CampLocation): CampLocationInfo {
        val result = CampLocationInfo()
        val fid = campLocation.nm
        result.dknm = fid
        val campCoordinates = campCoordinateRepository.findByFid(fid, Sort.by("coordinateNum"))
        if (!campCoordinates.isEmpty()) {
            val aRow = campCoordinates.first()
            result.centerX = aRow.centerX?.toDouble()
            result.centerY = aRow.centerY?.toDouble()
            val points = campCoordinates.map { getCampPoint(it) }
            result.points = points
        }
        result.relatedMainCampid = campLocation.relatedMainCampid
        return result
    }

    fun getCampPoint(coordinate: CampCoordinate): CampPoint {
        return CampPoint(
            num = coordinate.coordinateNum,
            x = coordinate.coorX?.toDouble(),
            y = coordinate.coorY?.toDouble()
        )
    }
}
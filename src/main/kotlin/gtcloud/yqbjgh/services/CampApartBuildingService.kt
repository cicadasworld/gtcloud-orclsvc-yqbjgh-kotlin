package gtcloud.yqbjgh.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import gtcloud.yqbjgh.domain.ApartInfo
import gtcloud.yqbjgh.domain.CampApartBuilding
import gtcloud.yqbjgh.domain.CampApartBuildingInfo
import gtcloud.yqbjgh.domain.VApartCoordinateJson
import gtcloud.yqbjgh.repositories.*
import org.apache.commons.text.StringEscapeUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CampApartBuildingService {

    @Autowired
    lateinit var campApartBuildingRepository: CampApartBuildingRepository

    @Autowired
    lateinit var dicBuildingStructureRepository: CampDicBuildingStructureRepository

    @Autowired
    lateinit var dicQualityGradeRepository: CampDicQualityGradeRepository

    @Autowired
    lateinit var dicUsingStatusRepository: CampDicUsingStatusRepository

    @Autowired
    lateinit var campApartCoordinateRepository: CampApartCoordinateRepository

    @Autowired
    lateinit var vApartCoordinateJsonRepository: VApartCoordinateJsonRepository

    @Autowired
    lateinit var mapper: ObjectMapper

    fun getCampApartBuildingByCampId(campId: String): List<CampApartBuilding> {
        return campApartBuildingRepository.findByCampId(campId)
                .map { convert(it) }
    }

    fun convert(campApartBuilding: CampApartBuilding): CampApartBuilding {
        val dicBuildingStructure = dicBuildingStructureRepository
                .findById(campApartBuilding.buildingStructure?: "").orElse(null)

        val dicQualityGrade = dicQualityGradeRepository
                .findById(campApartBuilding.qualityGrade?:"").orElse(null)

        val dicUsingStatus = dicUsingStatusRepository
                .findById(campApartBuilding.usingStatus?:"").orElse(null)

        val campApartCoordinates = campApartCoordinateRepository
                .findByApartId(campApartBuilding.jlbm)

        val campApartCoordinate = if (!campApartCoordinates.isEmpty()) {
             campApartCoordinates.first()
        } else null

        return campApartBuilding.copy(
                buildingStructure = dicBuildingStructure?.mc,  // buildingStructure -> buildingStructureÃû³Æ
                qualityGrade = dicQualityGrade?.mc,  // qualityGrade -> qualityGradeÃû³Æ
                usingStatus = dicUsingStatus?.mc,  // usingStatus -> usingStatusÃû³Æ
                coordinateId = campApartCoordinate?.jlbm,
                coorX = campApartCoordinate?.coorX,
                coorY = campApartCoordinate?.coorY
        )
    }

    fun getById(jlbm: String): CampApartBuilding {
        return campApartBuildingRepository.findById(jlbm).get()
    }

    fun getAllVApartCoordinateJsons(): List<CampApartBuildingInfo> {
        val vApartCoordinateJsons = vApartCoordinateJsonRepository.findAll()
        return vApartCoordinateJsons.map { getCampApartBuildingInfo(it) }
    }

    fun getCampApartBuildingInfo(vApartCoordinateJson: VApartCoordinateJson): CampApartBuildingInfo {
        var apartInfoStr = vApartCoordinateJson.apartInfo
        apartInfoStr = StringEscapeUtils.unescapeJava(apartInfoStr)
        apartInfoStr = apartInfoStr.replace(Regex("\\s*"), "")
        val apartInfo =  mapper.readValue<ApartInfo>(apartInfoStr)
        return CampApartBuildingInfo(
                campId = vApartCoordinateJson.campId,
                apartId = vApartCoordinateJson.apartId,
                apartInfo = apartInfo
        )
    }
}
package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampStaffHousing
import gtcloud.yqbjgh.repositories.CampDicHouseStandardGradeRepository
import gtcloud.yqbjgh.repositories.CampDicStaffKindRepository
import gtcloud.yqbjgh.repositories.CampStaffHousingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CampStaffHousingService {

    @Autowired
    lateinit var campStaffHousingRepository: CampStaffHousingRepository

    @Autowired
    lateinit var campDicHouseStandardGradeRepository: CampDicHouseStandardGradeRepository

    @Autowired
    lateinit var campDicStaffKindRepository: CampDicStaffKindRepository

    fun getCampStaffHousing(apartId: String): List<CampStaffHousing> {
        return campStaffHousingRepository.findByApartId(apartId)
                .map { convert(it) }
    }

    fun convert(campStaffHousing: CampStaffHousing): CampStaffHousing {
        val campDicHouseStandardGrade = campDicHouseStandardGradeRepository
                .findByIdOrNull(campStaffHousing.houseStandardGrade ?: "")

        val campDicStaffKind = campDicStaffKindRepository
                .findByIdOrNull(campStaffHousing.staffKind?:"")

        return campStaffHousing.copy(
                houseStandardGrade = campDicHouseStandardGrade?.mc,  // houseStandardGrade -> houseStandardGrade����
                staffKind = campDicStaffKind?.mc  // staffKind -> staffKind����
        )
    }
}
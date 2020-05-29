package gtcloud.yqbjgh.config

import gtcloud.yqbjgh.domain.CampLocation
import gtcloud.yqbjgh.repositories.CampLocationKindRepository
import gtcloud.yqbjgh.repositories.CampLocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CampLocationKindConf {

    @Autowired
    lateinit var campLocationKindRepository: CampLocationKindRepository

    @Autowired
    lateinit var campLocationRepository: CampLocationRepository

    @Bean
    @Scheduled(fixedRateString = "\${fixed-rate.in.millis}")
    fun updateCampKind() {
        val campLocations = campLocationRepository.findAll()
        val newCampLocations = mutableListOf<CampLocation>()
        for (campLocation in campLocations) {
            val campId = campLocation.nm
            val campKind = getCampKindByCampId(campId)
            val newCampLocation = campLocation.copy(
                campKind = campKind
            )
            newCampLocations.add(newCampLocation)
        }
        campLocationRepository.saveAll(newCampLocations)
    }

    private fun getCampKindByCampId(campId: String): String {
        val campLocationKinds = campLocationKindRepository.findByCampId(campId)

        val allStartsWith01 = campLocationKinds.all { it.campKindNm.startsWith("01") }
        val allStartsWith02 = campLocationKinds.all { it.campKindNm.startsWith("02") }
        val allStartsWith03 = campLocationKinds.all { it.campKindNm.startsWith("03") }
        val allStartsWith04 = campLocationKinds.all { it.campKindNm.startsWith("04") }
        val anyStartsWith01 = campLocationKinds.any { it.campKindNm.startsWith("01") }
        val anyStartsWith02 = campLocationKinds.any { it.campKindNm.startsWith("02") }
        val anyStartsWith03 = campLocationKinds.any { it.campKindNm.startsWith("03") }
        val filterWith03Sub = campLocationKinds.filter { it.campKindNm.startsWith("03") && it.campKindNm.length > 2 }
        val all010203 = filterWith03Sub.all { it.campKindNm.startsWith("0301")
                || it.campKindNm.startsWith("0302")
                || it.campKindNm.startsWith("0303")
        }

        when {
            allStartsWith01 -> return "01" // 当某个营区在 CAMP_LOCATION_KIND 表中的所有属性均以'01'开头时，CAMP_LOCATION.CAMP_KIND取值为'01'
            allStartsWith02 -> return "02" // 当某个营区在 CAMP_LOCATION_KIND 表中的所有属性均以'02'开头时，CAMP_LOCATION.CAMP_KIND取值为'02'
            allStartsWith03 -> return "03" // 当某个营区在 CAMP_LOCATION_KIND 表中的所有属性均以'03'开头时，CAMP_LOCATION.CAMP_KIND取值为'03'
            allStartsWith04 -> return "04" // 当某个营区在 CAMP_LOCATION_KIND 表中的所有属性均以'04'开头时，CAMP_LOCATION.CAMP_KIND取值为'04'
            anyStartsWith01 && anyStartsWith02 -> return "01" // 当某个营区在 CAMP_LOCATION_KIND 表中的属性既有以'01'开头的，也有以'02'开头的时，CAMP_LOCATION.CAMP_KIND取值为'01'
            anyStartsWith01 && anyStartsWith03 -> when { // 当某个营区在 CAMP_LOCATION_KIND 表中的属性既有以'01'开头的，也有以'03'开头的
                all010203 -> return "05" // 且以'03'开头的属性取值为'0301','0302','0303'时，CAMP_LOCATION.CAMP_KIND取值为'05'
                else -> return "01" // 且以'03'开头的属性取值不为'0301','0302','0303'时，CAMP_LOCATION.CAMP_KIND取值为'01'
            }
            anyStartsWith02 && anyStartsWith03 -> when {
                all010203 -> return "05" // 且以'03'开头的属性取值为'0301','0302','0303'时，CAMP_LOCATION.CAMP_KIND取值为'05'
                else -> return "02" // 且以'03'开头的属性取值不为'0301','0302','0303'时，CAMP_LOCATION.CAMP_KIND取值为'02'
            }
        }
        return campLocationKinds.first().campKindNm.substring(0, 2)
    }
}
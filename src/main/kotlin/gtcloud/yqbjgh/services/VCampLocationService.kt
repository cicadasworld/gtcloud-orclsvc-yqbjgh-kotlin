package gtcloud.yqbjgh.services

import gtcloud.yqbjgh.domain.CampCoordinate
import gtcloud.yqbjgh.domain.CustomFields
import gtcloud.yqbjgh.domain.TxzhTsBddwml
import gtcloud.yqbjgh.domain.VCampLocation
import gtcloud.yqbjgh.repositories.CampCoordinateRepository
import gtcloud.yqbjgh.repositories.CampLocationKindRepository
import gtcloud.yqbjgh.repositories.TxzhTsBddwmlRepository
import gtcloud.yqbjgh.repositories.VCampLocationRepository
import gtcloud.yqbjgh.specification.Specifications
import org.apache.commons.lang3.StringUtils.isNotBlank
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Polygon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VCampLocationService {

    @Autowired
    lateinit var vCampLocationRepository: VCampLocationRepository

    @Autowired
    lateinit var campCoordinateRepository: CampCoordinateRepository

    @Autowired
    lateinit var txzhTsBddwmlRepository: TxzhTsBddwmlRepository

    @Autowired
    lateinit var xhToBigUnit: Map<String, TxzhTsBddwml>

    @Autowired
    lateinit var geometryFactory: GeometryFactory

    @Autowired
    lateinit var campLocationKindRepository: CampLocationKindRepository

    fun queryVCampLocationByCustomFields(customFields: CustomFields): List<VCampLocation> {
        val dknm = customFields.dknm
        val dkmc = customFields.dkmc
        val campWord = customFields.campWord
        val campCode = customFields.campCode
        val detailAddress = customFields.detailAddress
        val campKind = customFields.campKind
        val campAttr = customFields.campAttr
        val onlyThisKind = customFields.onlyThisKind
        val adminDivision = customFields.adminDivision
        val siteKind = customFields.siteKind
        val watersupplyMode = customFields.watersupplyMode
        val elecsupplyMode = customFields.elecsupplyMode
        val gassupplyMode = customFields.gassupplyMode
        val heatsupplyMode = customFields.heatsupplyMode
        val campAreaFrom = customFields.campAreaFrom
        val campAreaTo = if (customFields.campAreaTo != 0.0f) customFields.campAreaTo else vCampLocationRepository.findMaxCampArea()

        val spec = if (dknm.isEmpty() && dkmc.isEmpty() && campWord.isEmpty() && campCode.isEmpty() && detailAddress.isEmpty()) {
            Specifications.and<VCampLocation>()
                    .like(isNotBlank(campKind), "campKind", campKind)
                    .like(isNotBlank(adminDivision), "adminDivision", adminDivision)
                    .like(isNotBlank(siteKind), "siteKind", siteKind)
                    .like(isNotBlank(watersupplyMode), "watersupplyMode", watersupplyMode)
                    .like(isNotBlank(elecsupplyMode), "elecsupplyMode", elecsupplyMode)
                    .like(isNotBlank(gassupplyMode), "gassupplyMode", gassupplyMode)
                    .like(isNotBlank(heatsupplyMode), "heatsupplyMode", heatsupplyMode)
                    .between(true, "campArea", campAreaFrom, campAreaTo)
                    .build()
        } else {
            Specifications.and<VCampLocation>()
                    .like(isNotBlank(campKind), "campKind", campKind)
                    .like(isNotBlank(adminDivision), "adminDivision", adminDivision)
                    .like(isNotBlank(siteKind), "siteKind", siteKind)
                    .like(isNotBlank(watersupplyMode), "watersupplyMode", watersupplyMode)
                    .like(isNotBlank(elecsupplyMode), "elecsupplyMode", elecsupplyMode)
                    .like(isNotBlank(gassupplyMode), "gassupplyMode", gassupplyMode)
                    .like(isNotBlank(heatsupplyMode), "heatsupplyMode", heatsupplyMode)
                    .between(true, "campArea", campAreaFrom, campAreaTo)
                    .predicate(Specifications.or<VCampLocation>()
                            .like(isNotBlank(dknm), "dknm", "%$dknm%")
                            .like(isNotBlank(dkmc), "dkmc", "%$dkmc%")
                            .like(isNotBlank(campWord), "campWord", "%$campWord%")
                            .like(isNotBlank(campCode), "campCode", "%$campCode%")
                            .like(isNotBlank(detailAddress), "detailAddress", "%$detailAddress%")
                            .build())
                    .build()
        }

        val campLocations = vCampLocationRepository.findAll(spec)

        if (campAttr.isEmpty()) return campLocations.map { addBigUnit(it) }

        val campLocationKinds = campLocationKindRepository.findByCampKindNm(campAttr)
        val matchedCampLocations = campLocationKinds
                .mapNotNull { vCampLocationRepository.findByIdOrNull(it.campId) }

        if (onlyThisKind) {
            return matchedCampLocations
                    .filter { campLocationKindRepository.findByCampId(it.dknm).size == 1 }
                    .map { addBigUnit(it) }
        }
        return matchedCampLocations
                .map { addBigUnit(it) }
    }

    fun getByDknm(dknm: String): VCampLocation {
        return addBigUnit(vCampLocationRepository.findById(dknm).get())
    }

    fun getVManagedCampLocationsByBdnm(id: String): List<VCampLocation> {
        val bddwml = txzhTsBddwmlRepository.findByIdOrNull(id)
        return if (bddwml == null) {
            vCampLocationRepository.findAll()
                    .map { addBigUnit(it) }
        } else {
            vCampLocationRepository.findByBdxh(bddwml.xh)
                    .map { addBigUnit(it) }
        }
    }

    fun queryCampLocationByPolygon(polygon: Polygon): List<VCampLocation> {
        val campCoordinates = campCoordinateRepository.findAll()
        val fids = campCoordinates.filter { pointIsInPolygon(polygon, it) }.map { it.fid }

        val vCampLocations = vCampLocationRepository.findAll()
        return vCampLocations
                .filter { locationIsInFids(fids, it) }
                .map { addBigUnit(it) }
    }

    fun pointIsInPolygon(polygon: Polygon, coordinate: CampCoordinate): Boolean {
        val point = geometryFactory.createPoint(Coordinate(coordinate.centerX!!.toDouble(),
                coordinate.centerY!!.toDouble()))
        return polygon.contains(point)
    }

    fun locationIsInFids(fids: List<String?>, location: VCampLocation): Boolean {
        return fids.contains(location.dknm)
    }

    fun queryCampLocationByCircle(lat: Double, lng: Double, radius: Double): List<VCampLocation> {
        val campCoordinates = campCoordinateRepository.findAll()
        val fids = campCoordinates.filter { pointIsInCircle(lat, lng, radius, it) }.map { it.fid }
        val vCampLocations = vCampLocationRepository.findAll()
        return vCampLocations
                .filter { locationIsInFids(fids, it) }
                .map { addBigUnit(it) }
    }

    fun pointIsInCircle(lat: Double, lng: Double, radius: Double, coordinate: CampCoordinate): Boolean {
        val point = geometryFactory.createPoint(Coordinate(coordinate.centerX!!.toDouble(),
                coordinate.centerY!!.toDouble()))
        val circle = geometryFactory.createPoint(Coordinate(lat, lng))
        return point.isWithinDistance(circle, radius)
    }

    private fun addBigUnit(vCampLoaction: VCampLocation): VCampLocation {
        val bigUnits = getBigUnits(vCampLoaction.bdxh)
        return vCampLoaction.copy(bigUnit = bigUnits?.bdjc)
    }

    private fun getBigUnits(xh: String): TxzhTsBddwml? {
        val values = xhToBigUnit.filterKeys { xh.startsWith(it) }.values
        return if (values.isEmpty()) null else values.first()
    }

}
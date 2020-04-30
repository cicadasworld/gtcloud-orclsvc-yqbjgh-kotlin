package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampRestResult
import gtcloud.yqbjgh.domain.CustomFields
import gtcloud.yqbjgh.domain.VCampLocation
import gtcloud.yqbjgh.domain.VUseCampLocation
import gtcloud.yqbjgh.services.VCampLocationService
import gtcloud.yqbjgh.services.VUseCampLocationService
import org.geotools.geojson.geom.GeometryJSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.StringReader

@RestController
class CampLocationController {

    @Autowired
    lateinit var vCampLocationService: VCampLocationService

    @Autowired
    lateinit var vUseCampLocationService: VUseCampLocationService

    @PostMapping("/v-camp-location/customquery")
    fun queryVCampLocationByCustomFields(@RequestBody customFields: CustomFields): CampRestResult<VCampLocation> {
        val restResult = CampRestResult<VCampLocation>()
        val results = vCampLocationService.queryVCampLocationByCustomFields(customFields)
        restResult.endpoint = "/v-camp-location/customquery"
        restResult.campLocations = results
        return restResult
    }

    @GetMapping("/v-camp-location/managed-camp/{bdnm}")
    fun getVCampLocationForManagedCampByBdnm(@PathVariable bdnm: String): CampRestResult<VCampLocation> {
        val restResult = CampRestResult<VCampLocation>()
        val results = vCampLocationService.getVManagedCampLocationsByBdnm(bdnm)
        restResult.endpoint = "/v-camp-location/managed-camp/$bdnm"
        restResult.campLocations = results
        return restResult
    }

    @GetMapping("/v-camp-location/using-camp/{bdnms}")
    fun getVUsingCampCampLocationByUseBdnm(@PathVariable bdnms: Array<String>): CampRestResult<VUseCampLocation> {
        val restResult = CampRestResult<VUseCampLocation>()
        val results = bdnms.flatMap { vUseCampLocationService.getVUsingCampCampLocationByUseBdnm(it) }
        restResult.endpoint = "/v-camp-location/using-camp/${bdnms.joinToString(",")}"
        restResult.campLocations = results
        return restResult
    }

    @GetMapping("/v-camp-location/dknm/{dknm}")
    fun getVCampLocationByDknm(@PathVariable dknm: String): CampRestResult<VCampLocation> {
        val restResult = CampRestResult<VCampLocation>()
        val results =  vCampLocationService.getByDknm(dknm)
        val endpoint = "/v-camp-location/dknm/$dknm"
        restResult.endpoint = endpoint
        restResult.campLocations = listOf(results)
        return restResult

    }

    @PostMapping("/v-camp-location/polygonquery")
    fun queryCampLocationByPolygon(@RequestBody json: String): CampRestResult<VCampLocation> {
        val restResult = CampRestResult<VCampLocation>()
        val gtjson = GeometryJSON()
        val polygon = gtjson.readPolygon(StringReader(json))
        val results = vCampLocationService.queryCampLocationByPolygon(polygon)
        val endpoint = "/v-camp-location/polygonquery?$json"
        restResult.endpoint = endpoint
        restResult.campLocations = results
        return restResult
    }

    @GetMapping("/v-camp-location/circlequery")
    fun queryCampLocationByCircle(@RequestParam lat: Double,
                                  @RequestParam lng: Double,
                                  @RequestParam radius: Double): CampRestResult<VCampLocation> {
        val restResult = CampRestResult<VCampLocation>()
        val results = vCampLocationService.queryCampLocationByCircle(lat, lng, radius)
        val endpoint ="/v-camp-location/circlequery?lat=$lat&lng=$lng&radius=$radius"
        restResult.endpoint = endpoint
        restResult.campLocations = results
        return restResult
    }
}
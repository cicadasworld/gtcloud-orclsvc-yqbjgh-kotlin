package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampApartCoordinateDTO
import gtcloud.yqbjgh.domain.VApartCoordinate
import gtcloud.yqbjgh.services.CampApartCoordinateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CampApartCoordinateController {

    @Autowired
    lateinit var service: CampApartCoordinateService

    @GetMapping("/camp-apart-coordinate/{apartId}")
    fun getCampApartCoordinate(@PathVariable apartId: String): List<CampApartCoordinateDTO> =
        service.getCampApartCoordinate(apartId)

    @PostMapping("/camp-apart-coordinate")
    fun createCampApartCoordinate(@RequestBody dto: CampApartCoordinateDTO): CampApartCoordinateDTO =
        service.createCampApartCoordinate(dto)

    @PutMapping("/camp-apart-coordinate/{jlbm}")  // 为GTMap提供编辑分栋的接口
    fun updateCampApartCoordinate(@PathVariable jlbm: String,
                                  @RequestBody dto: CampApartCoordinateDTO): CampApartCoordinateDTO =
        service.updateCampApartCoordinate(jlbm, dto)

    @DeleteMapping("/camp-apart-coordinate/{apartId}")
    fun deleteCampApartCoordinate(@PathVariable apartId: String): List<CampApartCoordinateDTO> =
        service.deleteCampApartCoordinate(apartId)

    @GetMapping("/v-apart-coordinate")
    fun getVApartCoordinateByRect(@RequestParam left: Double,
                            @RequestParam bottom: Double,
                            @RequestParam right: Double,
                            @RequestParam top: Double): List<VApartCoordinate> =
        service.getVApartCoordinateByRect(left, bottom, right, top)
}
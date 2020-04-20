package gtcloud.yqbjgh.controllers

import gtcloud.yqbjgh.domain.CampCoordinateDTO
import gtcloud.yqbjgh.domain.CampLocationInfo
import gtcloud.yqbjgh.services.CampCoordinateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CampCoordinateController {

    @Autowired
    lateinit var service: CampCoordinateService

    @GetMapping("/camp-coordinate/{fid}")
    fun getCampCoordinateByFid(@PathVariable fid: String): List<CampCoordinateDTO> =
        service.getCampCoordinateByFid(fid)

    @PostMapping("/camp-coordinate")
    fun saveCampCoordinate(@RequestBody dto: CampCoordinateDTO): CampCoordinateDTO =// Ϊ���빤���ṩ�Ľӿ�
        service.saveCampCoordinate(dto)

    @PutMapping("/camp-coordinate/fid/{fid}")
    fun updateCampCoordinates(@PathVariable fid: String,
                              @RequestBody dtos: List<CampCoordinateDTO>): List<CampCoordinateDTO> =// ΪGTMap�ṩ�༭�ؿ�Ľӿ�
        service.updateCampCoordinates(fid, dtos)

    @DeleteMapping("/camp-coordinate/fid/{fid}")
    fun deleteCampCoordinates(@PathVariable fid: String): List<CampCoordinateDTO> =
        service.deleteCampCoordinates(fid)

    @PutMapping("/camp-coordinate/{jlbm}")
    fun updateCampCoordinate(@PathVariable jlbm: String,
                             @RequestBody dto: CampCoordinateDTO): CampCoordinateDTO =
        service.updateCampCoordinate(jlbm, dto)

    @GetMapping("/camp-coordinate-json")
    fun getAllCampLocationInfoList(): List<CampLocationInfo> =
        service.getAllCampLocationInfoList()
}
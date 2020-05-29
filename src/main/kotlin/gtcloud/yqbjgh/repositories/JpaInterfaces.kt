package gtcloud.yqbjgh.repositories

import gtcloud.yqbjgh.domain.*
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface Bdf2UserRepository : JpaRepository<Bdf2User, String> {

    fun findByUsername(username: String): Bdf2User

}

interface Bdf2RoleMemberRepository : JpaRepository<Bdf2RoleMember, String> {

    fun findByUsername(username: String): List<Bdf2RoleMember>

}

interface Bdf2RoleRepository : JpaRepository<Bdf2Role, String>

interface CampApartBuildingRepository : JpaRepository<CampApartBuilding, String> {

    fun findByCampId(campId: String): List<CampApartBuilding>

}

interface CampApartCoordinateRepository : JpaRepository<CampApartCoordinate, String> {

    fun findByApartId(apartId: String): List<CampApartCoordinate>

}

interface CampApartUseclassRepository : JpaRepository<CampApartUseclass, String> {

    fun findByApartId(apartId: String): List<CampApartUseclass>

}

interface CampBatgroundFacilitiesRepository : JpaRepository<CampBatgroundFacilities, String> {

    fun findByCampId(campId: String): List<CampBatgroundFacilities>

}

interface CampCoordinateRepository : JpaRepository<CampCoordinate, String> {

    fun findByFid(fid: String, sort: Sort): List<CampCoordinate>

}

interface CampDicBarrackUseClassRepository : JpaRepository<CampDicBarrackUseClass, String>

interface CampDicBatKindRepository : JpaRepository<CampDicBatKind, String>

interface CampDicBuildingStructureRepository : JpaRepository<CampDicBuildingStructure, String>

interface CampDicCampKindRepository : JpaRepository<CampDicCampKind, String>

interface CampDicDangerousAttrRepository : JpaRepository<CampDicDangerousAttr, String>

interface CampDicElecsupplyModeRepository : JpaRepository<CampDicElecsupplyMode, String>

interface CampDicGassupplyModeRepository : JpaRepository<CampDicGassupplyMode, String>

interface CampDicHeatsupplyModeRepository : JpaRepository<CampDicHeatsupplyMode, String>

interface CampDicHouseStandardGradeRepository : JpaRepository<CampDicHouseStandardGrade, String>

interface CampDicMaterialKindRepository : JpaRepository<CampDicMaterialKind, String>

interface CampDicQualityGradeRepository : JpaRepository<CampDicQualityGrade, String>

interface CampDicSiteKindRepository : JpaRepository<CampDicSiteKind, String>

interface CampDicStaffKindRepository : JpaRepository<CampDicStaffKind, String>

interface CampDicTrafieldKindRepository : JpaRepository<CampDicBarrackUseClass, String>

interface CampDicTraUsingConditionRepository : JpaRepository<CampDicBarrackUseClass, String>

interface CampDicUsingStatusRepository : JpaRepository<CampDicUsingStatus, String>

interface CampDicWarKindRepository : JpaRepository<CampDicWarKind, String>

interface CampDicWatersupplyModeRepository : JpaRepository<CampDicWatersupplyMode, String>

interface CampLocationRepository : JpaRepository<CampLocation, String> {

    @Query(value = "select t from CampLocation t where t.realorvirtual = '0'")
    fun findAllVirtualCampLocation(): List<CampLocation>

    fun findByRelatedMainCampid(nm: String): List<CampLocation>

    fun findByNm(nm: String): List<CampLocation>
}

interface CampStaffHousingRepository : JpaRepository<CampStaffHousing, String> {

    fun findByApartId(apartId: String): List<CampStaffHousing>

}

interface CampTrainingFieldRepository : JpaRepository<CampTrainingField, String> {

    fun findByCampId(campId: String): List<CampTrainingField>

}

interface CampWarehouseMaterialRepository : JpaRepository<CampWarehouseMaterial, String> {

    fun findByWarehouseId(warehouseId: String): List<CampWarehouseMaterial>

    fun findByCampId(campId: String): List<CampWarehouseMaterial>

}

interface CampWarehouseRepository : JpaRepository<CampWarehouse, String> {

    fun findByCampId(campId: String): List<CampWarehouse>

}

interface ResidentDicAdminDivisionRepository : JpaRepository<ResidentDicAdminDivision, String>

interface ResidentDicUnitGradeRepository : JpaRepository<ResidentDicUnitGrade, String>

interface ResidentDicUnitKindRepository : JpaRepository<ResidentDicUnitKind, String> {

    @Query(value = "select t.nm from ResidentDicUnitKind t where t.nm like :nm%")
    fun findNmFamily(@Param("nm") nm: String): List<String>

    @Query(value = "select * from RESIDENT_DIC_UNIT_KIND order by NM", nativeQuery = true)
    fun findAllOrderByNm(): List<ResidentDicUnitKind>

}

interface ResidentUnitRepository : JpaRepository<ResidentUnit, String> {

    fun findByBdnm(bdnm: String): List<ResidentUnit>

}

interface TxzhTsBddwmlRepository : JpaRepository<TxzhTsBddwml, String> {

    @Query("select t from TxzhTsBddwml t where t.parentnm = :id")
    fun findByParentnm(@Param("id") id: String): List<TxzhTsBddwml>

    fun findByParentnmIsNull(): List<TxzhTsBddwml>

    @Query(value = "select t.nm from TxzhTsBddwml t where t.xh like :xh%")
    fun findBdnmFamily(@Param("xh") xh: String): List<String>

}

interface TxzyTsZbgcRepository : JpaRepository<TxzyTsZbgc, String>

interface VApartCoordinateJsonRepository : JpaRepository<VApartCoordinateJson, String>

interface VApartCoordinateRepository : JpaRepository<VApartCoordinate, String> {

    @Query(value = "select * from V_APART_COORDINATE t where COOR_X>:left and COOR_X<:right and COOR_Y > :bottom and COOR_Y<:top", nativeQuery = true)
    fun findByRect(left: Double, bottom: Double, right: Double, top: Double): List<VApartCoordinate>
}

interface VCampLocationRepository : JpaRepository<VCampLocation, String>, JpaSpecificationExecutor<VCampLocation> {

    @Query(value = "select max(t.campArea) from VCampLocation t")
    fun findMaxCampArea(): Float

    @Query(value = "select t from VCampLocation t where t.bdxh like :xh%")
    fun findByBdxh(@Param("xh") xh: String): List<VCampLocation>

    @Query(value = "select distinct * from V_CAMP_LOCATION order by BDXH", nativeQuery = true)
    fun findAllOrderByBdxh(): List<VCampLocation>
}

interface VUseCampLocationRepository : JpaRepository<VUseCampLocation, String>, JpaSpecificationExecutor<VUseCampLocation> {

    @Query(value = "select t from VUseCampLocation t where t.useBdxh like :xh%")
    fun findByBdxh(@Param("xh") xh: String): List<VUseCampLocation>
}

interface VUnitInforRepository : JpaRepository<VUnitInfor, String> {

    @Query(value = "select t from VUnitInfor t where t.xh like :xh%")
    fun findByXhFamily(@Param("xh") xh: String): List<VUnitInfor>

    fun findByXh(xh: String): VUnitInfor

    fun findByUsingCampId(campId: String): List<VUnitInfor>

    @Query(value = "select * from V_UNIT_INFOR order by XH", nativeQuery = true)
    fun findAllOderByXh(): List<VUnitInfor>

    @Query(value = "select * from V_UNIT_INFOR where USING_CAMP_ID is not null and length(USING_CAMP_ID) > 0 order by XH", nativeQuery = true)
    fun findByUsingCampIdOrderByXh(): List<VUnitInfor>

    fun findByUnitKind(unitKind: String): List<VUnitInfor>

    @Query(value = "select t from VUnitInfor t where t.unitKind like :unitKind%")
    fun findByUnitKindFamily(@Param("unitKind") unitKind: String): List<VUnitInfor>
}

interface CampLocationKindRepository: JpaRepository<CampLocationKind, String> {
    fun findByCampId(campId: String): List<CampLocationKind>

    @Query(value = "select t from CampLocationKind t where t.campKindNm like :nm%")
    fun findByCampKindNm(nm: String): List<CampLocationKind>

    @Query(value = "select distinct t.campId from CampLocationKind t")
    fun findDistincCampId(): List<String>
}
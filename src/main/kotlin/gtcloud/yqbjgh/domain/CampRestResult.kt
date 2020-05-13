package gtcloud.yqbjgh.domain

class CampRestResult<T>(

    var endpoint: String? = "",

    var campLocations: List<T> = listOf()
)
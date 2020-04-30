package gtcloud.yqbjgh.domain

import com.fasterxml.jackson.annotation.JsonIgnore

class UnitNode(
        val xh: String,
        val nm: String,
        val mc: String? = null,
        val bdjc: String? = null,
        val parentnm: String? = null,
        val dicUnitKind: Boolean? = false,
        val unitKind: String? = "",
        val unitKindName: String? = "",
        var children: MutableList<UnitNode> = mutableListOf(),
        @JsonIgnore
        var parent: UnitNode? = null
)
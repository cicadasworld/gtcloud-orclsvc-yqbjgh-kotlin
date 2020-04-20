package gtcloud.yqbjgh.domain

class UnitNode(
        val xh: String,
        val nm: String,
        val mc: String?,
        val bdjc: String?,
        val parentnm: String? = null,
        val unitKind: String? = "",
        val unitKindName: String? = "",
        var children: MutableList<UnitNode> = ArrayList()
)
package gtcloud.yqbjgh.domain

class BddwmlNode(
        var nm: String = "",
        var mc: String? = "",
        var xh: String? = "",
        var bdjc: String? = "",
        var parentnm: String? = "",
        var children: List<BddwmlNode> = ArrayList(),
        var show: Boolean = false,
        var hasManagedCamp: Boolean = false,
        var hasUsingCamp: Boolean = false,
        var isLeaf: Boolean = false,
        var unitKind: String? = ""
)
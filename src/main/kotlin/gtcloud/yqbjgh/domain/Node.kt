package gtcloud.yqbjgh.domain

import com.fasterxml.jackson.annotation.JsonIgnore

class Node(
    val bdjc: String?,
    val mc: String?,
    val xh: String,
    val unitKindName: String?,
    val children: MutableList<Node> = mutableListOf(),
    @JsonIgnore
    var parent: Node? = null
)
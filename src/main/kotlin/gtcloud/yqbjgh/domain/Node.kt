package gtcloud.yqbjgh.domain

import com.fasterxml.jackson.annotation.JsonIgnore

class Node(
    val bdjc: String?,
    val mc: String?,
    val xh: String,
    val children: MutableList<Node> = ArrayList(),
    @JsonIgnore
    var parent: Node? = null
)
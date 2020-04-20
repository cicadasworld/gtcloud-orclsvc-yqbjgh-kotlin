package gtcloud.yqbjgh.bootstrap

import gtcloud.yqbjgh.domain.BddwmlNode
import org.springframework.stereotype.Component

@Component
class BddwmlNodeBean {

    lateinit var rootToNodes: Map<String, BddwmlNode>
}
package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "BDF2_ROLE_MEMBER")
data class Bdf2RoleMember(
    @Id
    @Column(name = "ID_")
    val id: String,

    @Column(name = "ROLE_ID_")
    val roleId: String,

    @Column(name = "USERNAME_")
    val username: String
)
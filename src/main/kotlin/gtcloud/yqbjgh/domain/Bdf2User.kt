package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "BDF2_USER")
data class Bdf2User(
    @Id
    @Column(name = "USERNAME_")
    val username: String,

    @Column(name = "PASSWORD_")
    val password: String,

    @Column(name = "SALT_")
    val salt: String
)
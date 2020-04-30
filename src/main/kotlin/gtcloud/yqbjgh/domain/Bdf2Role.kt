package gtcloud.yqbjgh.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "BDF2_ROLE")
data class Bdf2Role(
    @Id
    @Column(name = "ID_")
    val id: String,

    @Column(name = "ROLE_NM")
    val roleNm: String
)
package pt.ulusofona.tfc.trabalho.dao

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class DisseminationResearcher (
        @Id
        val disseminationId: Long,
        val researcherId: String //orcid
)
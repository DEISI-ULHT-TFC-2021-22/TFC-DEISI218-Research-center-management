package pt.ulusofona.tfc.trabalho.dao.scientificActivities

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class PublicationInstitution (
        @Id
        val publicationId: Long,
        val institutionId: Long
)
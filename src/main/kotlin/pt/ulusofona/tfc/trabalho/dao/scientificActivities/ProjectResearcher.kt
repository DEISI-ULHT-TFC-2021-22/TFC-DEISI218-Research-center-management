package pt.ulusofona.tfc.trabalho.dao.scientificActivities

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(ProjectResearcherId::class)
data class ProjectResearcher(
        @Id
        val researcherId: String,

        @Id
        val projectId: Long
)

data class ProjectResearcherId(val researcherId: String, val projectId: String) : Serializable
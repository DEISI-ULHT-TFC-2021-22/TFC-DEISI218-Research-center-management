package pt.ulusofona.tfc.trabalho.dao.scientificActivities

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(ProjectResearcherId::class)
data class ProjectResearcher(
        @Id
        val projectId: Long,
        @Id
        val researcherId: String
)

data class ProjectResearcherId(var projectId: Long = 0, var researcherId: String = "") : Serializable
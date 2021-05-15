package pt.ulusofona.tfc.trabalho.dao.scientificActivities

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Project(
        @Id
        @GeneratedValue
        val id: Long = 0,
        var projectCategory: ProjectCategory,
        var title: String,
        var inicialDate: Date,
        var finalDate: Date,
        var abstract : String,
        var description : String // ou descriptor
)

enum class ProjectCategory {
    NACIONAL_PROJECT, INTERNACIONAL_PROJECT
}
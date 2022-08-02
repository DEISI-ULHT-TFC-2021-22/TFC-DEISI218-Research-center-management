package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import java.util.*

interface ResearcherRepository: JpaRepository<Researcher, String> {
    @Query
    fun findByName(name: String): Optional<Researcher>

    @Query(value="""
        SELECT
            * 
        FROM
            researcher                           AS res LEFT JOIN
            project_researcher                   AS prr ON res.orcid = prr.researcher_id LEFT JOIN
            dissemination_researcher             AS dir ON res.orcid = dir.researcher_id LEFT JOIN
            publication_researcher               AS pur ON res.orcid = pur.researcher_id LEFT JOIN
            other_scientific_activity_researcher AS otr ON res.orcid = otr.researcher_id LEFT JOIN
            project                              AS prj ON prr.project_id                   = prj.id LEFT JOIN
            dissemination                        AS dis ON dir.dissemination_id             = dis.id LEFT JOIN
            publication                          AS pub ON pur.publication_id               = pub.id LEFT JOIN
            other_scientific_activity            AS oth ON otr.other_scientific_activity_id = oth.id
    """, nativeQuery = true)
    fun findResearchers(
        @Param("activity_type") activityType : Int?,
        @Param("researcher_category") researcherCategory : String?,
        @Param("activity_category") activityCategory : Int?,
        @Param("date_from") dateFrom : String?,
        @Param("date_to") dateTo : String?,
        @Param("entries") entries : Int?,
        @Param("search") search : String?
        ): List<Researcher>
}
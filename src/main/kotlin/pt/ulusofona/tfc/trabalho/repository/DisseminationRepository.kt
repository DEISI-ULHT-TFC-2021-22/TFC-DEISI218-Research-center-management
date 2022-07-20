package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Dissemination

interface DisseminationRepository: JpaRepository<Dissemination, Long> {

    @Query(value="""
        SELECT
            dissemination.* 
        FROM
            dissemination  JOIN
            dissemination_researcher ON dissemination.id = dissemination_researcher.dissemination_id JOIN
            researcher ON researcher.orcid = dissemination_researcher.researcher_id
        WHERE
            (date >= :dateFrom) AND 
            (date <= :dateTo) AND
            (
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%') OR
                LOWER(researcher.name) LIKE CONCAT('%', LOWER(:search), '%')
            )
    """, nativeQuery = true, )
    fun search(
        @Param("dateFrom") dateFrom : String?,
        @Param("dateTo") dateTo : String?,
        @Param("search") search : String?
    ): List<Dissemination>

    @Query(value="""
        SELECT
            dissemination.* 
        FROM
            dissemination  JOIN
            dissemination_researcher ON dissemination.id = dissemination_researcher.dissemination_id JOIN
            researcher ON researcher.orcid = dissemination_researcher.researcher_id
        WHERE
            LOWER(title) LIKE CONCAT('%', LOWER(:search), '%') OR
            LOWER(researcher.name) LIKE CONCAT('%', LOWER(:search), '%')
    """, nativeQuery = true, )
    fun search(
        @Param("search") search : String?,
    ): List<Dissemination>
}
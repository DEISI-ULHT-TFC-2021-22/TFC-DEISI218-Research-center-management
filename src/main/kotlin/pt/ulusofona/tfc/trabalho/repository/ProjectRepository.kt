package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Publication
import java.util.*

interface ProjectRepository: JpaRepository<Project, String> {

    @Query
    fun findById(id:Long): Optional<Project>

    @Transactional
    fun deleteById(id:Long)

    @Query(value="""
        SELECT DISTINCT
            project.* 
        FROM
            project JOIN
            project_researcher ON project.id = project_researcher.project_id JOIN
            researcher ON researcher.orcid = project_researcher.researcher_id
        WHERE
            (initial_date >= :dateFrom) AND 
            (initial_date <= :dateTo) AND
            (
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%') OR
                LOWER(researcher.name) LIKE CONCAT('%', LOWER(:search), '%')
            )
            
    """, nativeQuery = true, )
    fun search(
        @Param("dateFrom") dateFrom : String?,
        @Param("dateTo") dateTo : String?,
        @Param("search") search : String?
    ): List<Project>

    @Query(value="""
        SELECT
            * 
        FROM
            project 
        WHERE
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%') OR
                LOWER(authors) LIKE CONCAT('%' + LOWER(:search), '%')
    """, nativeQuery = true, )
    fun search(
        @Param("search") search : String?
    ): List<Project>
}
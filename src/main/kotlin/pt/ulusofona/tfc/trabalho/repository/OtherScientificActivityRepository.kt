package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.OtherScientificActivity
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.OtherType
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import java.util.*

interface OtherScientificActivityRepository:JpaRepository<OtherScientificActivity, String> {

    @Query
    fun findById(id:Long): Optional<OtherScientificActivity>
    fun findByOtherType(type : OtherType): Optional<OtherScientificActivity>
    fun findByOtherTypeAndId(type : OtherType, id: Long): Optional<OtherScientificActivity>

    @Transactional
    fun deleteById(id:Long)

    @Query(value="""
        SELECT
            * 
        FROM
            other_scientific_activity AS activity
        WHERE
            (other_type = :type) AND
            (project_date >= :dateFrom) AND 
            (project_date <= :dateTo) AND
            (
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%')
            )
    """, nativeQuery = true, )
    fun search(
        @Param("dateFrom") dateFrom : String?,
        @Param("dateTo") dateTo : String?,
        @Param("search") search : String?,
        @Param("type") type : Int?
    ): List<OtherScientificActivity>

    @Query(value="""
        SELECT
            * 
        FROM
            other_scientific_activity AS activity
        WHERE
            (other_type = :type) AND
            LOWER(title) LIKE CONCAT('%', LOWER(:search), '%')
    """, nativeQuery = true, )
    fun search(
        @Param("search") search : String?,
        @Param("type") type : Int?
    ): List<OtherScientificActivity>
}
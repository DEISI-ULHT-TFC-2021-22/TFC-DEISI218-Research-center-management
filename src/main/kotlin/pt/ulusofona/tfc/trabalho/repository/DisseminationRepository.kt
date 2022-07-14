package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Dissemination
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.OtherScientificActivity
import java.util.*

interface DisseminationRepository: JpaRepository<Dissemination, Long> {

    @Query(value="""
        SELECT
            * 
        FROM
            dissemination
        WHERE
            (date >= :dateFrom) AND 
            (date <= :dateTo) AND
            (
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%')
            )
    """, nativeQuery = true, )
    fun search(
        @Param("dateFrom") dateFrom : String?,
        @Param("dateTo") dateTo : String?,
        @Param("search") search : String?
    ): List<Dissemination>

    @Query(value="""
        SELECT
            * 
        FROM
            dissemination
        WHERE
            LOWER(title) LIKE CONCAT('%', LOWER(:search), '%')
    """, nativeQuery = true, )
    fun search(
        @Param("search") search : String?,
    ): List<Dissemination>
}
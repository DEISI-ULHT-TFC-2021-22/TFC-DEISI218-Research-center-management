package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Publication
import java.util.*

interface PublicationRepository: JpaRepository<Publication, String> {
    @Query
    fun findById(id:Long): Optional<Publication>

    @Transactional
    fun deleteById(id:Long)

    @Query(value="""
        SELECT
            * 
        FROM
            publication AS pub
        WHERE
            (publication_date >= :dateFrom) AND 
            (publication_date <= :dateTo) AND
            (
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%') OR
                LOWER(authors) LIKE CONCAT('%' + LOWER(:search), '%')
            )
    """, nativeQuery = true, )
    fun search(
        @Param("dateFrom") dateFrom : String?,
        @Param("dateTo") dateTo : String?,
        @Param("search") search : String?
    ): List<Publication>

    @Query(value="""
        SELECT
            * 
        FROM
            publication AS pub
        WHERE
                LOWER(title) LIKE CONCAT('%', LOWER(:search), '%') OR
                LOWER(authors) LIKE CONCAT('%' + LOWER(:search), '%')
    """, nativeQuery = true, )
    fun search(
        @Param("search") search : String?
    ): List<Publication>
}
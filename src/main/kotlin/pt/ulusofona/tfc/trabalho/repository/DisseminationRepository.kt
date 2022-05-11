package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Dissemination
import java.util.*

interface DisseminationRepository: JpaRepository<Dissemination, Long> {}
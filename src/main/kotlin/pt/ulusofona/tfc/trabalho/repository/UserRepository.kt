package pt.ulusofona.tfc.trabalho.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tfc.trabalho.dao.User

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): List<User>
}
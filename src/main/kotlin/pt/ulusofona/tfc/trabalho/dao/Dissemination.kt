package pt.ulusofona.tfc.trabalho.dao

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Dissemination(
        @Id
        @GeneratedValue
        val id: Long = 0,

        var title: String,
        var date: Date,
        var descriptor: String,
        )
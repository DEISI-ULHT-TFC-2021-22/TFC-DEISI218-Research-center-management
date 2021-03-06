package pt.ulusofona.tfc.trabalho.dao

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Researcher(
        @Id
        var orcid: String,
        var name: String,
        var user: String,
        var email: String,

        var cienciaId: String,
        var publicKeyFct: String,
        var associationKeyFct: String,

        var phoneNumber: String,
        var origin: String,
        var siteCeied: String,
        var professionalStatus: String,
        var professionalCategory:String,
        var category: String,
        var phdYear: Date,
        var isAdmin: Boolean
        )
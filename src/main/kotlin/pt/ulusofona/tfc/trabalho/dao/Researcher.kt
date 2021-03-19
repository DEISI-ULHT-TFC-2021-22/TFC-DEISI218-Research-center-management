package pt.ulusofona.tfc.trabalho.dao

import java.util.Date
import javax.persistence.*

@Entity
data class Researcher(

        /*@OneToOne
        val user: User,*/

        @Id
        var orcid: String,
        var name: String,
        var utilizador: String,
        var email: String,
        var cienciaId: String,
        var associationKeyFct: String,
        var researcherCategory: String,
        var origin: String,
        var phoneNumber: String,
        var siteCeied: String,
        var professionalStatus: String,
        var professionalCategory:String,
        //var phdYear: Date?,
        //var isAdmin: Boolean
        )
package pt.ulusofona.tfc.trabalho.form

import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Email

data class ReasearcherForm(

        @field:NotEmpty(message = "Erro: O Orcid ID tem que estar preenchido")

        var orcid: String,

        @field:NotEmpty(message = "Erro: O nome tem que estar preenchido")
        var name: String,

        @field:NotEmpty(message = "Erro: O utilizador tem que estar preenchido")
        var user: String,

        @Email(message = "Email tem que ser válido")
        @field:NotEmpty(message = "Erro: O e-mail tem que estar preenchido")
        var email: String,

        @field:NotEmpty(message = "Erro: O Ciência ID tem que estar preenchido")
        var cienciaId: String,

        @field:NotEmpty(message = "Erro: A chave pública tem que estar preenchida")
        var publicKeyFct: String,

        @field:NotEmpty(message = "Erro: A chave de associação tem que estar preenchida")
        var associationKeyFct: String,


        @field:NotEmpty(message = "Erro: O número de telefone tem que estar preenchido")
        var phoneNumber: String,

        @field:NotEmpty(message = "Erro: A origem tem que estar preenchida")
        var origin: String,

        @field:NotEmpty(message = "Erro: O site CeiED tem que estar preenchido")
        var siteCeied: String,

        @field:NotEmpty(message = "Erro: A situação profissional tem que estar preenchida")
        var professionalStatus: String,

        @field:NotEmpty(message = "Erro: A categoria profissional tem que estar preenchida")
        var professionalCategory:String,

        @field:NotEmpty(message = "Erro: A categoria tem que estar preenchida")
        var category: String,


        var phdYear: Date,
        var isAdmin: Boolean,
)
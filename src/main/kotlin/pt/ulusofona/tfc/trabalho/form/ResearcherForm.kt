package pt.ulusofona.tfc.trabalho.form

import java.util.*
import javax.validation.constraints.*

data class ResearcherForm(
        @NotEmpty(message = "Erro: O Orcid ID tem que estar preenchido")
        @NotNull
        var orcid: String? = null,

        @NotEmpty(message = "Erro: O nome tem que estar preenchido")
        var name: String? = null,

        @NotEmpty(message = "Erro: O utilizador tem que estar preenchido")
        var utilizador: String? = null,

        @Email(message = "Email tem que ser válido")
        @NotEmpty(message = "Erro: O e-mail tem que estar preenchido")
        var email: String? = null,

        @NotEmpty(message = "Erro: O Ciência ID tem que estar preenchido")
        var cienciaId: String? = null,

        @field:NotEmpty(message = "Erro: A chave pública tem que estar preenchida")
        var publicKeyFct: String? = null,

        @field:NotEmpty(message = "Erro: A chave de associação tem que estar preenchida")
        var associationKeyFct: String? = null,

        @field:NotEmpty(message = "Erro: O número de telefone tem que estar preenchido")
        var phoneNumber: String? = null,

        @field:NotEmpty(message = "Erro: A origem tem que estar preenchida")
        var origin: String? = null,

        @field:NotEmpty(message = "Erro: O site CeiED tem que estar preenchido")
        var siteCeied: String? = null,

        @field:NotEmpty(message = "Erro: A situação profissional tem que estar preenchida")
        var professionalStatus: String? = null,

        @field:NotEmpty(message = "Erro: A categoria profissional tem que estar preenchida")
        var professionalCategory: String? = null,

        @field:NotEmpty(message = "Erro: A categoria tem que estar preenchida")
        var category: String? = null,

        var phdYear: Date? = null,

        var isAdmin: Boolean? = null
)
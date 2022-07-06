package pt.ulusofona.tfc.trabalho.form

import pt.ulusofona.tfc.trabalho.dao.scientificActivities.OtherCategory
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.OtherType
import java.util.*
import javax.validation.constraints.*

data class ResearcherSearchForm(

        //@field:NotEmpty(message = "Campo obrigatório")
        var activityType: Int? = null,

        //@field:NotEmpty(message = "Campo obrigatório")
        var activityCategory: Int? = null,

        //@field:NotEmpty(message = "Campo obrigatório")
        var researcherCategory: String? = null,

        //@field:NotEmpty(message = "Campo obrigatório")
        var dateFrom: String? = null,

        //@field:NotEmpty(message = "Campo obrigatório")
        var dateTo: String? = null,

        var entries: Int? = null,

        var search: String? = null,
)
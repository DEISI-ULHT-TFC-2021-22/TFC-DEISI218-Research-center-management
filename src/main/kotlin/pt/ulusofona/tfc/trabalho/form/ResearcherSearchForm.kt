package pt.ulusofona.tfc.trabalho.form

data class ResearcherSearchForm(
        var activityType: Int? = null,
        var dateFrom: String? = null,
        var dateTo: String? = null,
        var entries: Int? = null,
        var search: String? = null,
)
package pt.ulusofona.tfc.trabalho.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class AdminController{
    @GetMapping(value = ["/searches"])
    fun showSearches(model: ModelMap): String{
        return "admin-section/searches"
    }
    @GetMapping(value = ["/researcher-management"])
    fun showResearcherManagement(model: ModelMap): String{
        return "admin-section/researcher-management"
    }

    @GetMapping(value = ["/annual-report"])
    fun showAnnualReport(model: ModelMap): String{
        return "admin-section/annual-report"
    }
}
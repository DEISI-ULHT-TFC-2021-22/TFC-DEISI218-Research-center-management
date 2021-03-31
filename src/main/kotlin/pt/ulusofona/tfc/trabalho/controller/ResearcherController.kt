package pt.ulusofona.tfc.trabalho.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/researcher")
class ResearcherController{
    @GetMapping(value = ["/personal-information"])
    fun showPersonalInformation(model: ModelMap): String{
        return "researcher-section/personal-information"
    }
    @GetMapping(value = ["/scientific-activities"])
    fun showScientificActivities(model: ModelMap): String{
        return "researcher-section/scientific-activities"
    }
}
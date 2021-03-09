package pt.ulusofona.tfc.trabalho.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class SessionController{

    @GetMapping(value = [""])
    fun showLoginPage(model: ModelMap): String{
        return "login-page"
    }
    @GetMapping(value = ["/home"])
    fun showHomePage(model: ModelMap): String{
        return "home-page"
    }
    /*@GetMapping(value = ["/personal-information-form"])
    fun showPersonalInformationForm(model: ModelMap): String{
        return "forms-section/personal-information-form"
    }*/
    @GetMapping(value = ["/scientific-activity-form"])
    fun showScientificActivityForm(model: ModelMap): String{
        return "forms-section/scientific-activity-form"
    }
}
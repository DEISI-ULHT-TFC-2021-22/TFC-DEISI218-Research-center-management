package pt.ulusofona.tfc.trabalho.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.support.BasicAuthenticationInterceptor
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate

@Controller
@RequestMapping("")
class SessionController{

    @Value("\${ciencia.vitae.token}")
    lateinit var token: String

    @Value("\${ciencia.vitae.secret}")
    lateinit var secret: String

    @GetMapping(value = ["/ceied-login"])
    fun showLoginPage(model: ModelMap): String{
        return "login-page"
    }
    @GetMapping(value = ["/home"])
    fun showHomePage(model: ModelMap): String{
        return "home-page"
    }
    @GetMapping(value = ["/showCV"])
    fun showCienciaVitae(@RequestParam(name="id") id: String, model: ModelMap): String {

        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(BasicAuthenticationInterceptor(token, secret))
        val response = restTemplate.getForEntity("https://vitaeapi.playdev.ulusofona.pt/curriculum/$id", String::class.java)

        val mapper = ObjectMapper()
        val root: JsonNode = mapper.readTree(response.body)
        val fullName = root.at("/identifying-info/person-info/full-name").asText()

        val article = root.at("/outputs/output/0/conference-paper/paper-title")

        model["fullName"] = fullName
        model["article"] = article

        return "cv"
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
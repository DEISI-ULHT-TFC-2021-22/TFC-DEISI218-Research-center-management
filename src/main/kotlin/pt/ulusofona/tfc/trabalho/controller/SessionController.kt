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

        var articles_title = mutableListOf<String>()
        var event_type = ""
        var event_name = ""
        var event_date = ""
        var event_description = ""

        val fundingsSize = root.at("/fundings/total").asInt()
        val outputsSize = root.at("/outputs/total").asInt()
        val servicesSize = root.at("/services/total").asInt()
        val count = maxOf(fundingsSize, outputsSize, servicesSize)

        for(i in 0..count) {
            //P101 = Artigo em Revista
            if(root.at("/outputs/output/$i/output-type/code").asText() == "P101") {
                articles_title.add(root.at("/outputs/output/$i/journal-article/article-title").asText())
            }
        }

        //S202 = Participação em evento
        if(root.at("/services/service/2/service-category/code").asText() == "S202") {
            event_name = root.at("/services/service/2/event-participation/event-name").asText()
            event_type = root.at("/services/service/2/event-participation/event-type/value").asText()
            event_description = root.at("/services/service/2/event-participation/event-description").asText()
            event_date = root.at("/services/service/2/event-participation/start-date/year").asText()
        }

        model["fullName"] = fullName
        model["article"] = articles_title
        model["event_name"] = event_name
        model["event_type"] = event_type
        model["event_date"] = event_date
        model["event_description"] = event_description

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
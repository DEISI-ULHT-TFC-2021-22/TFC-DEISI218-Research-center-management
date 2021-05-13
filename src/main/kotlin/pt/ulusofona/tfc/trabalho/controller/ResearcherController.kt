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
@RequestMapping("/researcher")
class ResearcherController{

    @Value("\${ciencia.vitae.token}")
    lateinit var token: String

    @Value("\${ciencia.vitae.secret}")
    lateinit var secret: String

    @GetMapping(value = ["/personal-information"])
    fun showPersonalInformation(model: ModelMap): String{
        return "researcher-section/personal-information"
    }
    @GetMapping(value = ["/scientific-activities"])
    fun showScientificActivities(model: ModelMap): String{
        return "researcher-section/scientific-activities"
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
}
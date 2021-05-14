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
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Dissemination
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.DisseminationResearcher
import pt.ulusofona.tfc.trabalho.repository.DisseminationRepository
import pt.ulusofona.tfc.trabalho.repository.DisseminationResearcherRepository
import java.text.SimpleDateFormat

@Controller
@RequestMapping("")
class SessionController (val disseminationRepository: DisseminationRepository,
                         val disseminationResearcherRepository: DisseminationResearcherRepository){

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

        //var articles_title = mutableListOf<String>()

        val fundingsSize = root.at("/fundings/total").asInt()
        val outputsSize = root.at("/outputs/total").asInt()
        val servicesSize = root.at("/services/total").asInt()
        val count = maxOf(fundingsSize, outputsSize, servicesSize)

        println(root.at("/services/service//event-name").asText())

        for(i in 0..count) {
            //fundings

            //outputs

            //services
            if(root.at("/services/service/$i/service-category/value").asText() == "Participação em evento") {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                var year = "2000"
                var month = "01"
                var day = "01"

                //check year
                if(!root.at("/services/service/$i/event-participation/start-date/year").isNull) {
                    year = root.at("/services/service/$i/event-participation/start-date/year").asText()
                }

                //check month
                if(!root.at("/services/service/$i/event-participation/start-date/month").isNull) {
                    month = root.at("/services/service/$i/event-participation/start-date/month").asText()
                }

                //check day
                if(!root.at("/services/service/$i/event-participation/start-date/day").isNull) {
                    day = root.at("/services/service/$i/event-participation/start-date/day").asText()
                }

                val validatedDate = "$year-$month-$day"

                val dissemination = Dissemination(
                    title = root.at("/services/service/$i/event-participation/event-name").asText(),
                    date = dateFormat.parse(validatedDate),
                    description = root.at("/services/service/$i/event-description").asText()
                )

                disseminationRepository.save(dissemination)

                val disseminationResearcher = DisseminationResearcher(
                    disseminationId = dissemination.id,
                    researcherId = "xxxx-xxxx-xxxx-xxxx"
                )

                //--save ResearcherDissemination
                disseminationResearcherRepository.save(disseminationResearcher)
            }
        }

        //S202 = Participação em evento


        model["fullName"] = fullName
        //model["article"] = articles_title

        /*//P101 = Artigo em Revista
        if(root.at("/outputs/output/$i/output-type/code").asText() == "P101") {

            articles_title.add(root.at("/outputs/output/$i/journal-article/article-title").asText())
        }*/

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
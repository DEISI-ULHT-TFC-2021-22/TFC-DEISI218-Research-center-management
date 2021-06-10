package pt.ulusofona.tfc.trabalho.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.*
import pt.ulusofona.tfc.trabalho.form.ResearcherForm
import pt.ulusofona.tfc.trabalho.repository.*


@Controller
@RequestMapping("/researcher")
class ResearcherController(val researcherRepository: ResearcherRepository,
                           val disseminationRepository: DisseminationRepository,
                           val disseminationResearcherRepository: DisseminationResearcherRepository,
                           val publicationRepository: PublicationRepository,
                           val publicationResearcherRepository: PublicationResearcherRepository,
                           val projectRepository: ProjectRepository,
                           val projectResearcherRepository: ProjectResearcherRepository,
                           val otherScientificActivityRepository: OtherScientificActivityRepository,
                           val otherScientificActivityResearcherRepository: OtherScientificActivityResearcherRepository){

    @GetMapping(value = ["/personal-information"])
    fun showPersonalInformation(model: ModelMap, @ModelAttribute("getId") orcid: String): String{
        val researcherOptional = researcherRepository.findById(orcid)
        if (researcherOptional.isPresent) {
            val researcher = researcherOptional.get()
            model["researcher"] = Researcher(
                orcid = researcher.orcid,
                name = researcher.name,
                utilizador = researcher.utilizador,
                email = researcher.email,
                cienciaId = researcher.cienciaId,
                associationKeyFct = researcher.associationKeyFct,
                researcherCategory = researcher.researcherCategory,
                origin = researcher.origin,
                phoneNumber = researcher.phoneNumber,
                siteCeied = researcher.siteCeied,
                professionalStatus = researcher.professionalStatus,
                professionalCategory = researcher.professionalCategory,
                phdYear = researcher.phdYear,
                isAdmin = researcher.isAdmin,
            )
            return "researcher-section/personal-information"
        } else {
            return "not-found/researcher404"
        }
    }
    @GetMapping(value = ["/personal-information/edit"])
    fun editResearcherProfile(@ModelAttribute("getId") orcid: String,
                              model: ModelMap,
                              redirectAttributes: RedirectAttributes): String{

        val researcherOptional = researcherRepository.findById(orcid)
        if (researcherOptional.isPresent) {
            val researcher = researcherOptional.get()
            var isAdminOptional = researcher.isAdmin
            if (isAdminOptional == null){
                isAdminOptional = false
            }
            model["researcherForm"] = ResearcherForm(
                orcid = researcher.orcid,
                name = researcher.name,
                utilizador = researcher.utilizador,
                email = researcher.email,
                cienciaId = researcher.cienciaId,
                associationKeyFct = researcher.associationKeyFct,
                researcherCategory = researcher.researcherCategory,
                origin = researcher.origin,
                phoneNumber = researcher.phoneNumber,
                siteCeied = researcher.siteCeied,
                professionalStatus = researcher.professionalStatus,
                professionalCategory = researcher.professionalCategory,
                phdYear = researcher.phdYear,
                isAdmin = isAdminOptional
            )
        }
        return "forms-section/personal-information-form"
    }
    @GetMapping(value = ["/scientific-activities"])
    fun showScientificActivities(model: ModelMap, @ModelAttribute("getId") orcid: String): String{
        val researcherOptional = researcherRepository.findById(orcid)

        if (researcherOptional.isPresent) {
            val researcher = researcherOptional.get()
            //Recolha de apenas alguns dados (e não do investigador completo) que queiramos usar na pagina das atividades
            model["researcherInfo"] = mapOf(
                "orcid" to researcher.orcid,
                "name" to researcher.name,
                "email" to researcher.email,
                "cienciaId" to researcher.cienciaId,
                "researcherCategory" to researcher.researcherCategory,
                "isAdmin" to researcher.isAdmin,
            )

            val disseminations = ArrayList<Dissemination>()
            val publications = ArrayList<Publication>()
            val projects = ArrayList<Project>()
            val otherScientificActivities = ArrayList<OtherScientificActivity>()

            val disseminationResearcherlist = disseminationResearcherRepository.findByResearcherId(orcid)
            disseminationResearcherlist
                .map { disseminationRepository.findById(it.disseminationId) }
                .filter { it.isPresent }
                .mapTo(disseminations) { it.get() }
            model["disseminations"] = disseminations

            val publicationResearcherlist = publicationResearcherRepository.findByResearcherId(orcid)
            publicationResearcherlist
                .map { publicationRepository.findById(it.publicationId) }
                .filter { it.isPresent }
                .mapTo(publications) { it.get() }
            model["publications"] = publications

            val projectResearcherlist = projectResearcherRepository.findByResearcherId(orcid)
            projectResearcherlist
                .map { projectRepository.findById(it.projectId) }
                .filter { it.isPresent }
                .mapTo(projects) { it.get() }
            model["projects"] = projects

            val otherScientificActivityResearcherList = otherScientificActivityResearcherRepository.findByResearcherId(orcid)
            otherScientificActivityResearcherList
                .map { otherScientificActivityRepository.findById(it.otherScientificActivityId) }
                .filter { it.isPresent }
                .mapTo(otherScientificActivities) { it.get() }

            val advancedEducations = otherScientificActivities.filter { it.otherType == OtherType.ADVANCED_EDUCATION }
            model["advancedEducations"] = advancedEducations

            val scientificInitOfYoungStudents = otherScientificActivities.filter { it.otherType == OtherType.SCIENTIFIC_INIT_OF_YOUNG_STUDENTS }
            model["scientificInitOfYoungStudents"] = scientificInitOfYoungStudents

            return "researcher-section/scientific-activities"
        }else{
            return "not-found/researcher404"
        }
    }
}
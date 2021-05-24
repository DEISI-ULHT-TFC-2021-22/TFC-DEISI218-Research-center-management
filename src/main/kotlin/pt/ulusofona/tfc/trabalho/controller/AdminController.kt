package pt.ulusofona.tfc.trabalho.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.*
import pt.ulusofona.tfc.trabalho.form.DisseminationForm
import pt.ulusofona.tfc.trabalho.form.ResearcherForm
import pt.ulusofona.tfc.trabalho.repository.*
import java.text.SimpleDateFormat
import javax.validation.Valid
import kotlin.collections.ArrayList


@Controller
@RequestMapping("/admin")
class AdminController(val researcherRepository: ResearcherRepository,
                      val disseminationRepository: DisseminationRepository,
                      val disseminationResearcherRepository: DisseminationResearcherRepository,
                      val publicationRepository: PublicationRepository,
                      val publicationResearcherRepository: PublicationResearcherRepository,
                      val projectRepository: ProjectRepository,
                      val projectResearcherRepository: ProjectResearcherRepository,
                      val otherScientificActivityRepository: OtherScientificActivityRepository,
                      val otherScientificActivityResearcherRepository: OtherScientificActivityResearcherRepository){

    @GetMapping(value = ["/searches"])
    fun showSearches(model: ModelMap): String{
        return "admin-section/searches"
    }

    @GetMapping(value = ["/researcher-management"])
    fun showResearcherManagement(model: ModelMap): String{
        val researchers = researcherRepository.findAll()
        model["researchers"] = researchers
        return "admin-section/researcher-management"
    }

    @GetMapping(value = ["/user/{orcid}"])
    fun viewResearcherProfile(@PathVariable("orcid") orcid : String, model: ModelMap): String{

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
        }else{
            return "not-found/researcher404"
        }
    }

    @GetMapping(value = ["/user/edit/{orcid}"])
    fun editResearcherProfile(@PathVariable("orcid") orcid : String,
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
        redirectAttributes.addFlashAttribute("message","Investigador editado com sucesso!")
        return "forms-section/personal-information-form"
    }

    @GetMapping(value = ["/personal-information-form"])
    fun showResearcherForm(model: ModelMap): String{
        model["researcherForm"] = ResearcherForm()
        return "forms-section/personal-information-form"
    }

    @PostMapping(value = ["/personal-information-form"])
    fun createResearcher(@Valid @ModelAttribute("researcherForm") researcherForm: ResearcherForm,
                         bindingResult: BindingResult,
                         redirectAttributes: RedirectAttributes): String{

        if(bindingResult.hasErrors()){
           return "forms-section/personal-information-form"
            //return "admin-section/researcher-management"
        }
        var isAdminOptional = researcherForm.isAdmin
        if (isAdminOptional == null){
            isAdminOptional = false
        }
        val researcher = Researcher(
                orcid = researcherForm.orcid!!,
                name = researcherForm.name!!,
                utilizador = researcherForm.utilizador!!,
                email = researcherForm.email!!,
                cienciaId = researcherForm.cienciaId!!,
                associationKeyFct = researcherForm.associationKeyFct!!,
                researcherCategory = researcherForm.researcherCategory!!,
                origin = researcherForm.origin!!,
                phoneNumber = researcherForm.phoneNumber!!,
                siteCeied = researcherForm.siteCeied!!,
                professionalStatus = researcherForm.professionalStatus!!,
                professionalCategory = researcherForm.professionalCategory!!,
                phdYear = researcherForm.phdYear,
                isAdmin = researcherForm.isAdmin!!,
        )

        researcherRepository.save(researcher)

        //TODO adiconar a extensao bulma-toast para receber estes alertas vvvvv
        redirectAttributes.addFlashAttribute("message","Investigador ${researcher.name} inserido com sucesso!")
        return "redirect:/admin/researcher-management"
    }

    @GetMapping("/user/delete/{orcid}")
    fun deleteResearcher(@PathVariable orcid: String, redirectAttributes: RedirectAttributes): String{
        if (researcherRepository.findById(orcid).isPresent){
            redirectAttributes.addFlashAttribute("message","Investigador ${researcherRepository.findById(orcid).get().name} eliminado com sucesso!")
            researcherRepository.deleteById(orcid)
        }
        return "redirect:/admin/researcher-management"
    }


    @GetMapping(value = ["/scientific-activities/{orcid}"])
    fun viewResearcherSA(@PathVariable("orcid") orcid : String, model: ModelMap): String{

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

    @GetMapping(value = ["{orcid}/dissemination-form"])
    fun showDisseminationForm(@PathVariable orcid : String,
                              model: ModelMap): String{
        model["disseminationForm"] = DisseminationForm()
        model["orcid"] = orcid
        //model["orcid"] = orcid
        return "forms-section/dissemination-form"
    }

    @PostMapping(value = ["{orcid}/dissemination-form"])
    fun createDissemination(@Valid
                            @ModelAttribute("disseminationForm") disseminationForm: DisseminationForm,
                            @PathVariable orcid : String,
                            bindingResult: BindingResult,
                            redirectAttributes: RedirectAttributes):String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        //--criar Dissemination - sem ID
        val dissemination = Dissemination(
                disseminationCategory = disseminationForm.disseminationCategory!!,
                title = disseminationForm.title!!,
                date = dateFormat.parse(disseminationForm.date!!),
                description = disseminationForm.description!!
        )

        //--save Dissemination - já tenho ID
        disseminationRepository.save(dissemination)

        //--criar ResearcherDissemination com o ID do dissemination e com o orcid do principal todo ver mais sobre principal
        val disseminationResearcher = DisseminationResearcher(
                disseminationId = dissemination.id,
                researcherId = orcid
        )

        //--save ResearcherDissemination
        disseminationResearcherRepository.save(disseminationResearcher)

        return "redirect:/admin/user/$orcid"
    }

    @GetMapping("/dissemination/delete/{id}")
    fun deleteDissemination(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String{
        var orcid = "ORCID"
        if (disseminationRepository.findById(id).isPresent){

            redirectAttributes.addFlashAttribute("message","Investigador ${disseminationRepository.findById(id).get().title} eliminado com sucesso!")
            orcid = disseminationResearcherRepository.findByDisseminationId(id).get().researcherId
            disseminationResearcherRepository.deleteByDisseminationId(id)
            disseminationRepository.deleteById(id)
        }
        return "redirect:/admin/scientific-activities/$orcid"
    }

    @GetMapping(value = ["/annual-report"])
    fun showAnnualReport(model: ModelMap): String{
        return "admin-section/annual-report"
    }


}
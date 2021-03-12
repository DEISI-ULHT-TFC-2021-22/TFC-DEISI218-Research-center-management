package pt.ulusofona.tfc.trabalho.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.form.ResearcherForm
import pt.ulusofona.tfc.trabalho.repository.ResearcherRepository
import javax.validation.Valid
import java.util.Optional

@Controller
@RequestMapping("/admin")
class AdminController(val researcherRepository: ResearcherRepository){
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

    @GetMapping(value = ["/view/{orcid}"])
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
                    publicKeyFct = researcher.publicKeyFct,
                    associationKeyFct = researcher.associationKeyFct,
                    phoneNumber = researcher.phoneNumber,
                    origin = researcher.origin,
                    siteCeied = researcher.siteCeied,
                    professionalStatus = researcher.professionalStatus,
                    professionalCategory = researcher.professionalCategory,
                    category = researcher.category,
            )
        }
        return "researcher-section/personal-information"
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

        val researcher = Researcher(
                orcid = researcherForm.orcid!!,
                name = researcherForm.name!!,
                utilizador = researcherForm.utilizador!!,
                email = researcherForm.email!!,
                cienciaId = researcherForm.cienciaId!!,
                publicKeyFct = researcherForm.publicKeyFct!!,
                associationKeyFct = researcherForm.associationKeyFct!!,
                phoneNumber = researcherForm.phoneNumber!!,
                origin = researcherForm.origin!!,
                siteCeied = researcherForm.siteCeied!!,
                professionalStatus = researcherForm.professionalStatus!!,
                professionalCategory = researcherForm.professionalCategory!!,
                category = researcherForm.category!!,
                //phdYear = researcherForm.phdYear,
                //isAdmin = researcherForm.isAdmin
        )

        researcherRepository.save(researcher)

        //TODO adiconar a extensao bulma-toast para receber estes alertas vvvvv
        //redirectAttributes.addFlashAttribute("message","Investigador inserido com sucesso")
        return "redirect:/admin/researcher-management"
    }

    @GetMapping(value = ["/annual-report"])
    fun showAnnualReport(model: ModelMap): String{
        return "admin-section/annual-report"
    }
}
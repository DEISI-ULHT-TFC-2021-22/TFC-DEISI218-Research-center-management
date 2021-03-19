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

            )
        }
        return "researcher-section/personal-information"
    }

    @GetMapping(value = ["/edit/{orcid}"])
    fun editResearcherProfile(@PathVariable("orcid") orcid : String, model: ModelMap): String{
        val researcherOptional = researcherRepository.findById(orcid)
        if (researcherOptional.isPresent) {
            val researcher = researcherOptional.get()
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

            )
        }
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
                //phdYear = researcherForm.phdYear,
                //isAdmin = researcherForm.isAdmin
        )

        researcherRepository.save(researcher)

        //TODO adiconar a extensao bulma-toast para receber estes alertas vvvvv
        //redirectAttributes.addFlashAttribute("message","Investigador inserido com sucesso")
        return "redirect:/admin/researcher-management"
    }

    @GetMapping("/delete/{orcid}")
    fun deleteResearcher(@PathVariable orcid: String): String{
        if (researcherRepository.findById(orcid).isPresent){
            researcherRepository.deleteById(orcid)
        }
        return "redirect:/admin/researcher-management"
    }

    @GetMapping(value = ["/annual-report"])
    fun showAnnualReport(model: ModelMap): String{
        return "admin-section/annual-report"
    }
}
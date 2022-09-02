package pt.ulusofona.tfc.trabalho;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Publication
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.PublicationCategory
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.servlet.http.HttpServletResponse


class WordExporter(
    private var listProject: MutableList<Project>,
    private var mapProjectResearcher: MutableMap<Long, MutableList<Researcher>>,
    private var listPublication: MutableList<Publication>
    ) {

    private fun projects(doc: XWPFDocument) {
        val paragrafo = doc.createParagraph()
        paragrafo.isPageBreak = true
        paragrafo.alignment = ParagraphAlignment.LEFT
        paragrafo.style = "Ttulo1"

        val titulo = paragrafo.createRun()
        titulo.setText("Projetos em curso")

        val paragrafo1 = doc.createParagraph()
        paragrafo1.alignment = ParagraphAlignment.LEFT
        paragrafo1.style = "Normal"

        for (project in listProject) {
            val tituloProjeto = paragrafo1.createRun()
            tituloProjeto.addCarriageReturn()
            tituloProjeto.addCarriageReturn()
            tituloProjeto.setText("Title: ${project.title}")
            tituloProjeto.isBold = true

            // Lista dos investigadores do projeto
            val researchers = mapProjectResearcher[project.id]
            var researchersString = ""
            if (researchers != null) {
                for ((i, researcher) in researchers.withIndex()) {
                    researchersString += researcher.name
                    if (i < researchers.size - 1) {
                        researchersString += ", "
                    }
                }
            }

            val researchTeam = paragrafo1.createRun()
            researchTeam.addCarriageReturn()
            researchTeam.setText("Research team: $researchersString")

            val funding = paragrafo1.createRun()
            funding.addCarriageReturn()
            funding.setText("Funding:") //TODO

            val partners = paragrafo1.createRun()
            partners.addCarriageReturn()
            partners.setText("Partners:") //TODO

            val dates = paragrafo1.createRun()
            dates.addCarriageReturn()
            val initialDate = project.initialDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val finalDate = project.finalDate?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            dates.setText("Dates: ${initialDate.format(formatter)} - ${finalDate?.format(formatter)?.toString().orEmpty()}")

            val abstrato = paragrafo1.createRun()
            abstrato.addCarriageReturn()
            abstrato.setText("Abstract:\n${project.abstract}")

            val website = paragrafo1.createRun()
            website.addCarriageReturn()
            website.setText("Website: ${project.website}")
        }
    }

    private fun publications(doc: XWPFDocument) {
        val paragrafo = doc.createParagraph()
        paragrafo.isPageBreak = true
        paragrafo.alignment = ParagraphAlignment.LEFT
        paragrafo.style = "Ttulo1"

        val titulo = paragrafo.createRun()
        titulo.setText("Produção Científica")

        val paragrafo1 = doc.createParagraph()
        paragrafo1.alignment = ParagraphAlignment.LEFT
        paragrafo1.style = "Normal"

        // Ordenar pela categoria da publicação
        val publicationCategories = listPublication.groupBy { it.publicationCategory }

        for ((category, publications) in publicationCategories.entries) {
            if (publications.size > 0) {
                var categoryName = ""

                when (category) {
                    PublicationCategory.KNOWLEDGE_CONTRIBUTION -> categoryName =
                        "Contribuição para o Avanço e Aplicação do Conhecimento"
                    PublicationCategory.NATIONAL_MAGAZINE -> categoryName = "Artigo em Revista Científica Nacional"
                    PublicationCategory.INTERNATIONAL_MAGAZINE -> categoryName =
                        "Artigo em Revista Científica Internacional"
                    PublicationCategory.BOOK_AUTHORSHIP -> categoryName = "Autoria e Coautoria de Livro"
                    PublicationCategory.BOOK_CHAPTER -> categoryName = "Capítulo de Livro"
                    PublicationCategory.BOOK_EDITING_AND_ORGANISATION -> categoryName = "Edição/organização de Livro"
                    PublicationCategory.INTERNATIONAL_CONFERENCE -> categoryName =
                        "Comunicação em Conferência Internacional"
                    PublicationCategory.NATIONAL_CONFERENCE -> categoryName = "Comunicação em Conferência Nacional"
                    PublicationCategory.OTHER_PUBLICATION -> categoryName = "Outra Publicação"
                }

                val tituloCategoria = paragrafo1.createRun()
                tituloCategoria.addCarriageReturn()
                tituloCategoria.addCarriageReturn()
                tituloCategoria.setText("${categoryName}")
                tituloCategoria.isBold = true

                for (publication in publications) {
                    publication.publisher
                    val cal = Calendar.getInstance()
                    cal.time = publication.publicationDate

                    val publicacao = paragrafo1.createRun()
                    publicacao.addCarriageReturn()
                    publicacao.addCarriageReturn()
                    publicacao.setText("${publication.authors} (${cal.get(Calendar.YEAR)}). ${publication.title}. ")

                    val revista = paragrafo1.createRun()
                    revista.setText("Revista, 1, 1-2. ")
                    revista.isItalic = true

                    if (publication.conferenceName != "" && publication.conferenceName != null) {
                        val conference = paragrafo1.createRun()
                        conference.setText("${publication.conferenceName}. ")
                    }

                    if (publication.publisher != "" && publication.publisher != null) {
                        val publisher = paragrafo1.createRun()
                        publisher.setText("${publication.publisher}. ")
                    }

                    if (publication.descriptor != "" && publication.descriptor != null) {
                        val doi = paragrafo1.createRun()
                        doi.setText("${publication.descriptor}. ")
                    }
                }

                val espaco = paragrafo1.createRun()
                espaco.addCarriageReturn()
            }
        }
    }

    @Throws(IOException::class)
    fun export(response: HttpServletResponse) {
        val doc = XWPFDocument(FileInputStream(File("src/main/resources/report_template.docx")))

        projects(doc)
        publications(doc)

        val outputStream = response.outputStream
        doc.write(outputStream)
        doc.close()
        outputStream.close()
    }
}
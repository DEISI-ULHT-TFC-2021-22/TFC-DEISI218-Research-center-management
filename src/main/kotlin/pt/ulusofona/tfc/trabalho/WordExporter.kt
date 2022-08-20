package pt.ulusofona.tfc.trabalho;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Publication
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
        titulo.setText("Publicações")

        val paragrafo1 = doc.createParagraph()
        paragrafo1.alignment = ParagraphAlignment.LEFT
        paragrafo1.style = "Normal"

        for ((index, publication) in listPublication.withIndex()) {
            val tituloPublicacao = paragrafo1.createRun()
            tituloPublicacao.addCarriageReturn()
            tituloPublicacao.addCarriageReturn()
            tituloPublicacao.setText("${index+1}. ${publication.title}")
            tituloPublicacao.isBold = true

            val authorsTitle = paragrafo1.createRun()
            authorsTitle.addCarriageReturn()
            authorsTitle.setText("Autor(es) / Editor(es)")

            val authors = paragrafo1.createRun()
            authors.addCarriageReturn()
            authors.setText(publication.authors)

            val cal = Calendar.getInstance()
            cal.time = publication.publicationDate
            val publicationYear = paragrafo1.createRun()
            publicationYear.addCarriageReturn()
            publicationYear.setText("Ano de edição: ${cal.get(Calendar.YEAR)}")

            val descriptor = paragrafo1.createRun()
            descriptor.addCarriageReturn()
            descriptor.setText(publication.descriptor)
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
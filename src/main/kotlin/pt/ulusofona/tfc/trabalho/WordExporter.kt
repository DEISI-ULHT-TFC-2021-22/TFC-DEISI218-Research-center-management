package pt.ulusofona.tfc.trabalho;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Publication
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

    private var doc: XWPFDocument = XWPFDocument()
    private var researcherReference = "A4"
    private var SAReference = "A4"

    @Throws(IOException::class)
    fun export(response: HttpServletResponse) {

        val paragrafo = doc.createParagraph()
        paragrafo.alignment = ParagraphAlignment.LEFT
        val titulo = paragrafo.createRun()
        titulo.setText("Projetos em curso")
        titulo.addCarriageReturn()
        titulo.addCarriageReturn()
        titulo.isBold = true
        titulo.fontSize = 14
        titulo.fontFamily = "Calibri (Corpo)"

        for (project in listProject) {
            val paragrafo1 = doc.createParagraph()
            paragrafo1.alignment = ParagraphAlignment.LEFT
            val titulo2 = paragrafo1.createRun()
            titulo2.setText(
                "Title: ${project.title}"
            )
            titulo2.isBold = true
            titulo2.fontFamily = "Calibri (Corpo)"
            titulo2.fontSize = 12

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

            val paragrafo2 = doc.createParagraph()
            paragrafo2.alignment = ParagraphAlignment.LEFT
            val researchTeam = paragrafo2.createRun()
            researchTeam.setText("Research team: $researchersString")
            researchTeam.fontFamily = "Calibri (Corpo)"
            researchTeam.fontSize = 12

            val paragrafo3 = doc.createParagraph()
            paragrafo3.alignment = ParagraphAlignment.LEFT
            val funding = paragrafo3.createRun()
            funding.setText("Funding:") //TODO
            funding.fontFamily = "Calibri (Corpo)"
            funding.fontSize = 12

            val paragrafo4 = doc.createParagraph()
            paragrafo4.alignment = ParagraphAlignment.LEFT
            val partners = paragrafo4.createRun()
            partners.setText("Partners:") //TODO
            partners.fontFamily = "Calibri (Corpo)"
            partners.fontSize = 12

            val paragrafo5 = doc.createParagraph()
            paragrafo5.alignment = ParagraphAlignment.LEFT
            val dates = paragrafo5.createRun()
            val initialDate = project.initialDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val finalDate = project.finalDate?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            dates.setText("Dates: ${initialDate.format(formatter)} - ${finalDate?.format(formatter)?.toString().orEmpty()}")
            dates.fontFamily = "Calibri (Corpo)"
            dates.fontSize = 12

            val paragrafo6 = doc.createParagraph()
            paragrafo6.alignment = ParagraphAlignment.LEFT
            val abstrato = paragrafo6.createRun()
            abstrato.setText("Abstract:\n${project.abstract}")
            abstrato.fontFamily = "Calibri (Corpo)"
            abstrato.fontSize = 12

            val paragrafo7 = doc.createParagraph()
            paragrafo7.alignment = ParagraphAlignment.LEFT
            val website = paragrafo7.createRun()
            website.setText("Website: ${project.website}")
            website.fontFamily = "Calibri (Corpo)"
            website.fontSize = 12
            website.addCarriageReturn()
        }

        val paragrafo1 = doc.createParagraph()
        paragrafo1.alignment = ParagraphAlignment.LEFT
        val titulo1 = paragrafo1.createRun()
        titulo1.setText("Publicações")
        titulo1.addCarriageReturn()
        titulo1.addCarriageReturn()
        titulo1.isBold = true
        titulo1.fontSize = 14
        titulo1.fontFamily = "Calibri (Corpo)"

        for ((index, publication) in listPublication.withIndex()) {
            val paragrafo2 = doc.createParagraph()
            paragrafo2.alignment = ParagraphAlignment.LEFT
            val titulo2 = paragrafo2.createRun()
            titulo2.setText(
                "${index+1}. ${publication.title}"
            )
            titulo2.isBold = true
            titulo2.fontFamily = "Calibri (Corpo)"
            titulo2.fontSize = 12

            val paragrafo3 = doc.createParagraph()
            paragrafo3.alignment = ParagraphAlignment.LEFT
            val authorsTitle = paragrafo3.createRun()
            authorsTitle.setText("Autor(es) / Editor(es)")
            authorsTitle.fontFamily = "Calibri (Corpo)"
            authorsTitle.fontSize = 12

            val paragrafo4 = doc.createParagraph()
            paragrafo4.alignment = ParagraphAlignment.LEFT
            val authors = paragrafo4.createRun()
            authors.setText(publication.authors)
            authors.fontFamily = "Calibri (Corpo)"
            authors.fontSize = 12

            val paragrafo5 = doc.createParagraph()
            paragrafo5.alignment = ParagraphAlignment.LEFT
            val cal = Calendar.getInstance()
            cal.time = publication.publicationDate
            val publicationYear = paragrafo5.createRun()
            publicationYear.setText("Ano de edição: ${cal.get(Calendar.YEAR)}")
            publicationYear.fontFamily = "Calibri (Corpo)"
            publicationYear.fontSize = 12

            val paragrafo6 = doc.createParagraph()
            paragrafo6.alignment = ParagraphAlignment.LEFT
            val descriptor = paragrafo6.createRun()
            descriptor.setText(publication.descriptor)
            descriptor.fontFamily = "Calibri (Corpo)"
            descriptor.fontSize = 12
            descriptor.addCarriageReturn()
        }

        val outPutStream = response.outputStream
        doc.write(outPutStream)
        doc.close()
        outPutStream.close()
    }
}
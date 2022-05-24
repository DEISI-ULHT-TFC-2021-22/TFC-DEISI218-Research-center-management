package pt.ulusofona.tfc.trabalho;

import org.apache.poi.xwpf.usermodel.BreakType
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.UnderlinePatterns
import org.apache.poi.xwpf.usermodel.XWPFDocument
import pt.ulusofona.tfc.trabalho.dao.Institution
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Dissemination
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.OtherScientificActivity
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Project
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.Publication
import java.io.IOException
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse


class WordExporter(
    private var listProject: MutableList<Project>,
    ) {

    private var doc: XWPFDocument = XWPFDocument()
    private var researcherReference = "A4"
    private var SAReference = "A4"

    @Throws(IOException::class)
    fun export(response: HttpServletResponse) {

        val paragrafo = doc.createParagraph()
        paragrafo.alignment = ParagraphAlignment.LEFT
        val titulo1 = paragrafo.createRun()
        titulo1.setText("Projetos em curso")
        titulo1.addCarriageReturn()
        titulo1.addCarriageReturn()
        titulo1.isBold = true
        titulo1.fontSize = 14
        titulo1.fontFamily = "Calibri (Corpo)"

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

            val paragrafo2 = doc.createParagraph()
            paragrafo2.alignment = ParagraphAlignment.LEFT
            val researchTeam = paragrafo2.createRun()
            researchTeam.setText("Research team:") //TODO
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
            abstrato.setText("Abstract:\n${project.abstract}") //TODO
            abstrato.fontFamily = "Calibri (Corpo)"
            abstrato.fontSize = 12

            val paragrafo7 = doc.createParagraph()
            paragrafo7.alignment = ParagraphAlignment.LEFT
            val website = paragrafo7.createRun()
            website.setText("Website:")
            website.fontFamily = "Calibri (Corpo)"
            website.fontSize = 12
            website.addCarriageReturn()
        }

        val outPutStream = response.outputStream
        doc.write(outPutStream)
        doc.close()
        outPutStream.close()
    }
}
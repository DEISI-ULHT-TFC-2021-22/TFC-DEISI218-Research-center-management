package pt.ulusofona.tfc.trabalho;

import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.xml.sax.ext.Attributes2
import pt.ulusofona.tfc.trabalho.dao.Institution
import pt.ulusofona.tfc.trabalho.dao.Researcher
import pt.ulusofona.tfc.trabalho.dao.scientificActivities.*
import java.io.IOException
import javax.servlet.http.HttpServletResponse


public class WordExporter{

    private var doc: XWPFDocument = XWPFDocument()
    private var researcherReference = "A4"
    private var SAReference = "A4"

    @Throws(IOException::class)
    fun export(response: HttpServletResponse) {



        val outPutStream = response.outputStream
        doc.write(outPutStream)
        doc.close()
        outPutStream.close()
    }
}
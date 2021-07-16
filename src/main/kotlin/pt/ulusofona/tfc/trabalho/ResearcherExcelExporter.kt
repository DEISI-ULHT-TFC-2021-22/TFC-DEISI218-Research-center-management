package pt.ulusofona.tfc.trabalho

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import pt.ulusofona.tfc.trabalho.dao.Researcher
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class ResearcherExcelExporter(private var listResearchers: List<Researcher>) {
    private var workbook: XSSFWorkbook = XSSFWorkbook()
    private var sheet: XSSFSheet = workbook.createSheet("Investigadores")

    fun writeHeaderRow(){
        val row = sheet.createRow(0)
        val cellCienciaId = row.createCell(0)
        cellCienciaId.setCellValue("Ciência ID")

        val cellName = row.createCell(1)
        cellName.setCellValue("Nome")

        val cellEmail = row.createCell(2)
        cellEmail.setCellValue("Email")

        val cellUserFCT = row.createCell(3)
        cellUserFCT.setCellValue("Utilizador FCT")

        val cellAssociationKeyFct = row.createCell(4)
        cellAssociationKeyFct.setCellValue("Chave Pública FCT")

        val cellSiteCeied = row.createCell(5)
        cellSiteCeied.setCellValue("Site CeiED")

        val cellOrcid = row.createCell(6)
        cellOrcid.setCellValue("ORCID")

        val cellOrigin = row.createCell(7)
        cellOrigin.setCellValue("Origem")

        val cellPhdYear = row.createCell(8)
        cellPhdYear.setCellValue("Ano de Doutoramento")

        val cellProfessionalStatus = row.createCell(9)
        cellProfessionalStatus.setCellValue("Situação Profissional")

        val cellProfessionalCategory = row.createCell(10)
        cellProfessionalCategory.setCellValue("Categoria Profissional")

        val cellPhoneNumber = row.createCell(11)
        cellPhoneNumber.setCellValue("Telefone")
    }

    fun writeDataRows(){
        var rowCount = 1

        for(researcher in listResearchers){
            var row = sheet.createRow(rowCount++)
            val cellCienciaId = row.createCell(0)
            cellCienciaId.setCellValue(researcher.cienciaId)

            val cellName = row.createCell(1)
            cellName.setCellValue(researcher.name)

            val cellEmail = row.createCell(2)
            cellEmail.setCellValue(researcher.email)

            val cellUserFCT = row.createCell(3)
            cellUserFCT.setCellValue(researcher.utilizador)

            val cellAssociationKeyFct = row.createCell(4)
            cellAssociationKeyFct.setCellValue(researcher.associationKeyFct)

            val cellSiteCeied = row.createCell(5)
            cellSiteCeied.setCellValue(researcher.siteCeied)

            val cellOrcid = row.createCell(6)
            cellOrcid.setCellValue(researcher.orcid)

            val cellOrigin = row.createCell(7)
            cellOrigin.setCellValue(researcher.origin)

            val cellPhdYear = row.createCell(8)
            cellPhdYear.setCellValue(researcher.phdYear.toString())

            val cellProfessionalStatus = row.createCell(9)
            cellProfessionalStatus.setCellValue(researcher.professionalStatus)

            val cellProfessionalCategory = row.createCell(10)
            cellProfessionalCategory.setCellValue(researcher.professionalCategory)

            val cellPhoneNumber = row.createCell(11)
            cellPhoneNumber.setCellValue(researcher.phoneNumber)
        }
    }
    @Throws(IOException::class)
    fun export(response: HttpServletResponse) {
        writeHeaderRow()
        writeDataRows()
        val outPutStream = response.outputStream
        workbook.write(outPutStream)
        workbook.close()
        outPutStream.close()
    }
}
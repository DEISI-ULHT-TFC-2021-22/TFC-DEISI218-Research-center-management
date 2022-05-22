import org.apache.poi.hpsf.Section;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class main {
    public static void main2(String[] args) throws IOException {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragrafo = doc.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);

        XWPFHyperlinkRun h = paragrafo.createHyperlinkRun("http://poi.apache.org/");
        h.setText("Apache POI");
        h.setColor("0000FF");
        h.setUnderline(UnderlinePatterns.SINGLE);

        XWPFParagraph paragrafo14 = doc.createParagraph();
        paragrafo14.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun website2 = paragrafo14.createRun();
        XWPFHyperlinkRun h2 = paragrafo14.createHyperlinkRun("https://www.ceied.ulusofona.pt/pt/investigacao/stakeholders-together-adapting-ideas-to-readjust-local-systems-to-promote-inclusive-education-stairs/");
        h2.setText("Apache POI");
        h2.setColor("0000FF");
        h2.setUnderline(UnderlinePatterns.SINGLE);
        website2.setText("Website: ");
        website2.setFontFamily("Calibri (Corpo)");
        website2.setFontSize(12);

        FileOutputStream outStream = new FileOutputStream("teste.doc");
        doc.write(outStream);
        outStream.close();
    }


    public static void main(String[] args) throws IOException, URISyntaxException, InvalidFormatException {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();
        //File image = new File("image.png");
        //FileInputStream stream = new FileInputStream(image);
        //XWPFRun rImage = p.createRun();
        //rImage.addPicture(stream, Document.PICTURE_TYPE_PNG, "logo.png", Units.toEMU(48), Units.toEMU(48));  // 48x48 pixeis
        //rImage.addBreak();


        XWPFParagraph paragrafo = doc.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titulo1 = paragrafo.createRun();
        titulo1.setText("Projetos em curso");
        titulo1.addCarriageReturn();
        titulo1.addCarriageReturn();
        titulo1.setBold(true);
        titulo1.setUnderline(UnderlinePatterns.valueOf(Font.U_SINGLE));
        titulo1.setFontSize(14);
        titulo1.setFontFamily("Calibri (Corpo)");

        XWPFParagraph paragrafo1 = doc.createParagraph();
        paragrafo1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titulo2 = paragrafo1.createRun();
        titulo2.setText("Title: Fostering and assessing students’ creative and critical thinking skills in higher education and \n" +
                "teacher education");
        titulo2.setBold(true);
        titulo2.setFontFamily("Calibri (Corpo)");
        titulo2.setFontSize(12);

        XWPFParagraph paragrafo2 = doc.createParagraph();
        paragrafo2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun researchTeam = paragrafo2.createRun();
        researchTeam.setText("Research team: Alcina Martins, Carla Galego, Célia Pires, Elsa Estrela, Manuel José Damásio, Sónia\n" +
                "Vladimira, Susana Sá, Teresa Teixeira Lopo and Vítor Rosa.");
        researchTeam.setFontFamily("Calibri (Corpo)");
        researchTeam.setFontSize(12);

        XWPFParagraph paragrafo3 = doc.createParagraph();
        paragrafo3.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun funding = paragrafo3.createRun();
        funding.setText("Funding: Higher Education Ministry and COFAC");
        funding.setFontFamily("Calibri (Corpo)");
        funding.setFontSize(12);

        XWPFParagraph paragrafo4 = doc.createParagraph();
        paragrafo4.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun partners = paragrafo4.createRun();
        partners.setText("Partners: 20 higher education institutions from 14 countries.");
        partners.setFontFamily("Calibri (Corpo)");
        partners.setFontSize(12);

        XWPFParagraph paragrafo5 = doc.createParagraph();
        paragrafo5.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun dates = paragrafo5.createRun();
        dates.setText("Dates: 2018 - 2022");
        dates.setFontFamily("Calibri (Corpo)");
        dates.setFontSize(12);



        XWPFParagraph paragrafo6 = doc.createParagraph();
        paragrafo6.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun abstrato = paragrafo6.createRun();
        abstrato.setText("Abstract:\n" +
                "Creativity and critical thinking are key skills for the complex and globalized economies and societies of the \n" +
                "21st century. A new project at the OECD Centre for Educational Research and Innovation (CERI) aims to \n" +
                "support higher education institutions to enhance the quality of their teaching to foster students’ creative and \n" +
                "critical thinking. The project will build an international community of practice around teaching, learning and \n" +
                "assessing creativity and critical thinking in higher education. The effects of the pedagogical interventions on \n" +
                "students and teachers will be monitored through a quasi-experimental research design involving pre- and \n" +
                "post-testing on intervention and control groups. Qualitative reporting will complement quantitative data \n" +
                "collection to provide evidence of the effects of the different pedagogies tested. Ultimately, the project aims \n" +
                "to trigger a sustainable improvement process in teaching and learning in higher education and beyond.");
        abstrato.setFontFamily("Calibri (Corpo)");
        abstrato.setFontSize(12);

        XWPFParagraph paragrafo7 = doc.createParagraph();
        paragrafo7.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun website = paragrafo7.createRun();
        website.setFontFamily("Calibri (Corpo)");
        website.setFontSize(12);
        website.addCarriageReturn();

        XWPFParagraph paragrafo8 = doc.createParagraph();
        paragrafo8.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun title2 = paragrafo8.createRun();
        title2.setText("Title: Stakeholders Together Adapting Ideas to Readjust Local Systems to Promote Inclusive \n" +
                "Education (STAIRS)");
        title2.setBold(true);
        title2.setFontFamily("Calibri (Corpo)");
        title2.setFontSize(12);

        XWPFParagraph paragrafo9 = doc.createParagraph();
        paragrafo9.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun researchTeam2 = paragrafo9.createRun();
        researchTeam2.setText("Research team: Ana Paula Silva");
        researchTeam2.setFontFamily("Calibri (Corpo)");
        researchTeam2.setFontSize(12);

        XWPFParagraph paragrafo10 = doc.createParagraph();
        paragrafo10.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun funding2 = paragrafo10.createRun();
        funding2.setText("Funding: Erasmus+ K3");
        funding2.setFontFamily("Calibri (Corpo)");
        funding2.setFontSize(12);

        XWPFParagraph paragrafo11 = doc.createParagraph();
        paragrafo11.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun dates2 = paragrafo11.createRun();
        dates2.setText("Dates: 2019-2022");
        dates2.setFontFamily("Calibri (Corpo)");
        dates2.setFontSize(12);

        XWPFParagraph paragrafo12 = doc.createParagraph();
        paragrafo12.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun partners2 = paragrafo12.createRun();
        partners2.setText("Partners: Portugal, Hungary, Slovenia, Czech Republic, Croatia, Ireland (participating members), Austria \n" +
                "(associated member");
        partners2.setFontFamily("Calibri (Corpo)");
        partners2.setFontSize(12);

        XWPFParagraph paragrafo13 = doc.createParagraph();
        paragrafo13.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun abstrato2 = paragrafo13.createRun();
        abstrato2.setText("Abstract: \n" +
                "The main purpose of the STAIRS project is to promote social inclusion through educational good practices. \n" +
                "In order to achieve this, the partnership is going to build up a massive collaboration of sharing and learning \n" +
                "countries. All of them will have complex benefits and purposes: sharing countries are going to compile \n" +
                "materials based on their own set of good practices and learning countries will create their national adaptation \n" +
                "plans. With upscaling their acquired knowledge and the recommendations of the national adaptation plans, \n" +
                "a Final Synthetizing Report will be created for the use of the Commission and decision makers.");
        abstrato2.setFontFamily("Calibri (Corpo)");
        abstrato2.setFontSize(12);


        XWPFParagraph paragrafo14 = doc.createParagraph();
        paragrafo14.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun website2 = paragrafo14.createRun();
        XWPFHyperlinkRun h2 = paragrafo14.createHyperlinkRun("https://www.ceied.ulusofona.pt/pt/investigacao/stakeholders-together-adapting-ideas-to-readjust-local-systems-to-promote-inclusive-education-stairs/");
        h2.setText("https://www.ceied.ulusofona.pt/pt/investigacao/stakeholders-together-adapting-ideas-to-readjust-local-systems-to-promote-inclusive-education-stairs/");
        h2.setColor("0000FF");
        h2.setUnderline(UnderlinePatterns.SINGLE);
        website2.setText("Website: ");
        website2.setFontFamily("Calibri (Corpo)");
        website2.setFontSize(12);
        XWPFRun r2 = paragrafo14.createRun();
        r2.addBreak(BreakType.PAGE);



        XWPFParagraph paragrafo15 = doc.createParagraph();
        paragrafo15.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun title3 = paragrafo15.createRun();
        title3.setText("UNESCO Chair Education, Citizenship and Cultural Diversity");
        title3.addCarriageReturn();
        title3.setBold(true);
        title3.setFontSize(14);
        title3.setFontFamily("Calibri (Corpo)");

        XWPFParagraph paragrafo16 = doc.createParagraph();
        paragrafo16.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun committee = paragrafo16.createRun();
        committee.setText("Coordinating Committee: Judite Primo, Luísa Janeirinho, Mário Moutinho, Maria Neves Gonçalves and \n" +
                "Pedro Leite");
        committee.setFontSize(12);

        XWPFParagraph paragrafo17 = doc.createParagraph();
        paragrafo17.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun externalAdvisors = paragrafo17.createRun();
        committee.setText("External Advisory Committee: Ana Fitas, Cristina Bruno and Iva Cabral\n");
        committee.setFontSize(12);

        XWPFParagraph paragrafo18 = doc.createParagraph();
        paragrafo18.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun investigadoresAssociados = paragrafo18.createRun();
        investigadoresAssociados.setText("Associated researchers: Aida Rechena (DGPC – Directorate-General for Cultural Heritage, Portugal), \n" +
                "António Teodoro (Lusófona University), Gabriela Cavaco (Museum of the Presidency, Portugal), Graça \n" +
                "Teixeira (UFBA – Afro-Brazilian Museum), José Pimentel Teixeira (Anthropologist), Manuel Antunes \n" +
                "(Lusófona University), Manuel Costa Leite (Lusófona University), Manuel Furtado Mendes (Lusófona \n" +
                "University), Manuel Serafim (Lusófona University), Marcele Pereira (UNIR – Federal University of Rondônia, \n" +
                "Brazil), Marcelo Cunha (UFBA – Federal University of Bahia, Brazil), Maria Célia M Barros (UFBA – Federal \n" +
                "University of Bahia, Brazil), Mário Antas (National Museum of Archeology, Portugal), Mário Chagas (UNIRIO \n" +
                "/Republic Museum, Brazil), Paula Assunção (Reinwart Academie – Amsterdam University of the Arts, \n" +
                "Netherlands), Paulo Peixoto (University of Coimbra, Portugal), Rossana Nascimento (UFSC – Federal \n" +
                "University of Santa Catarina – Brazil), Simone Flores Monteiro (PUCRS – Pontifical Catholic University of \n" +
                "Rio Grande do Sul, Brazil) and Vladimir Sibila (UNIRIO – Federal University of the State of Rio de Janeiro, \n" +
                "Brazil)");
        investigadoresAssociados.setFontSize(12);
        investigadoresAssociados.addCarriageReturn();

        XWPFRun title4 = paragrafo18.createRun();
        title4.setText("Research Projects:");
        title4.addCarriageReturn();
        title4.addCarriageReturn();
        title4.setBold(true);
        title4.setFontSize(12);
        title4.setFontFamily("Calibri (Corpo)");
        title4.addCarriageReturn();

        //por melhorar, colocar os enters
        XWPFParagraph paragrafo19 = doc.createParagraph();
        paragrafo19.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun reseatchProj = paragrafo19.createRun();
        reseatchProj.setText("× Projeto piloto: Educação Patrimonial (2018-19)\n" +
                "× Community School Museums (COSMUS) ERASMUS+ (2018-21)\n" +
                "× Arquivo multimédia da poesia dos países da CPLP (2018-20)\n" +
                "× Renova Museu: Revitalização de um museu por meio de ações educativas IBERMUSEUS (2018-\n" +
                "19)\n" +
                "× Education, Citizenship and Cultural Diversity: Theory and practice of Sociomuseology (2018-24) \n" +
                "FCT");
        reseatchProj.setFontSize(12);
        reseatchProj.setColor("FF0000");


        /*
        p1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun r1 = p1.createRun();
        r1.setText("Olá Mundo");
        XWPFRun r2 = p1.createRun();
        r2.addBreak(BreakType.PAGE);
         */



        /*String[] strings = c.getSentece().split(k.getName());
        for (int i = 0; i < strings.length; i++) {
            XWPFRun r2 = p.createRun();
            r2.setText(strings[i]);

            if (i < strings.length - 1) {
                XWPFRun r3 = p.createRun();
                r3.setBold(true);
                r3.setItalic(true);
                //r3.setColor(t.getHexColor());
                //r3.setText(k.getName());

            }
        }
         */

        /*
        //add a section
        Section section = doc.addSection();

        //add a paragraph
        Paragraph paragraph = section.addParagraph();

        //insert a picture
        DocPicture picture = paragraph.appendPicture("Image.png");

        //set the width and height of the picture
        picture.setWidth(300f);
        picture.setHeight(200f);

        save the document
        document.saveToFile("InsertImage.docx", FileFormat.Docx_2013);
        */

        FileOutputStream outStream = new FileOutputStream("teste.doc");
        doc.write(outStream);
        outStream.close();
    }
}







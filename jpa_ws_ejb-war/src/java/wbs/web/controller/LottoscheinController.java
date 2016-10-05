package wbs.web.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wbs.lotto.domain.Adresse;
import wbs.lotto.domain.Kunde;
import wbs.lotto.domain.Lottoschein;
import wbs.lotto.persistence.KundeFacadeLocal;
import wbs.lotto.service.LottoscheinEinreichenLocal;
import wbs.lotto.util.ByteLongConverterBB;
import wbs.lotto.util.ByteLongConverter;
import wbs.lotto.util.LottoUtil;

@SessionScoped
@Named
public class LottoscheinController implements Serializable {

    @EJB
    private KundeFacadeLocal kundeFacadeLocal;

    @EJB
    LottoscheinEinreichenLocal lottoscheinEinreichenLocal;

    private long kundeId;
    private int losNummer;
    private int ziehungstage = 1;
    private int laufzeit;
    private int anzahlTipps;
    private Boolean isSpiel77;
    private Boolean isSuper6;
    private Lottoschein lottoschein = null;

    public Lottoschein getLottoschein() {
        return lottoschein;
    }

    public void setLottoschein(Lottoschein lottoschein) {
        this.lottoschein = lottoschein;
    }

    public Boolean getIsSpiel77() {
        return isSpiel77;
    }

    public void setIsSpiel77(Boolean isSpiel77) {
        this.isSpiel77 = isSpiel77;
    }

    public Boolean getIsSuper6() {
        return isSuper6;
    }

    public void setIsSuper6(Boolean isSuper6) {
        this.isSuper6 = isSuper6;
    }

    public int getLosNummer() {
        return losNummer;
    }

    public void setLosNummer(int losNummer) {
        this.losNummer = losNummer;
    }

    public int getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(int laufzeit) {
        this.laufzeit = laufzeit;
    }

    public int getAnzahlTipps() {
        return anzahlTipps;
    }

    public void setAnzahlTipps(int anzahlTipps) {
        this.anzahlTipps = anzahlTipps;
    }

    public long[] getTippsAsArray() {

        if (lottoschein == null) {
            return new long[]{};
        } else {

            return ByteLongConverterBB.byteToLong(lottoschein.getTipps());
        }
    }

    public String[] getTippsAsStringArray() {

        long[] tipps = getTippsAsArray();
        String[] result = new String[tipps.length];
        for (int i = 0; i < tipps.length; i++) {
            result[i] = LottoUtil.tippsAsString(tipps[i]);
        }
        return result;
    }

    public int getZiehungstage() {
        return ziehungstage;
    }

    public void setZiehungstage(int ziehungstage) {
        this.ziehungstage = ziehungstage;
    }

    public long getKundeId() {
        return kundeId;
    }

    public void setKundeId(long kundeId) {
        this.kundeId = kundeId;
    }

    public Date getErsteZiehung() {
        //return new Date();
        return lottoschein.getLottoscheinziehungList().get(0).getZiehungId().getZiehungsdatum();
    }

    public String senden() {
        System.out.println("wbs.web.controller.LottoscheinController.senden()");
        // Lottoscheinobjekt erzeugen
        lottoschein = new Lottoschein();

        // Felder setzen
        lottoschein.setKundeId(kundeFacadeLocal.find(kundeId));

        // Ziehungstage
        lottoschein.setIsMittwoch(ziehungstage == 2 || ziehungstage == 3);
        lottoschein.setIsSamstag(ziehungstage == 1 || ziehungstage == 3);

        // Spiel77 und Super6
        lottoschein.setIsSpiel77(isSpiel77);
        lottoschein.setIsSuper6(isSuper6);

        // Tipps
        lottoschein.setTipps(LottoUtil.randomTippsAsByteArray(anzahlTipps));
        lottoschein.setLosnummer(getLosNummer());
        lottoscheinEinreichenLocal.lottoscheinEinreichen(lottoschein, laufzeit);
        return "ok";
    }

    private int[] tippToArray(long tipp) {
        int[] result = new int[6];

        int index = 0;
        while (tipp != 0 && index < 6) {
            result[index] = Long.numberOfTrailingZeros(tipp);
            tipp &= ~Long.lowestOneBit(tipp);
            index++;
        }

        return result;
    }

    private PdfPTable getLottoTippTable(long[] tipps, int teil, Font font) throws DocumentException {
        PdfPTable table = new PdfPTable(7);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        float zahlBreite = 20f;
        table.setTotalWidth(new float[]{80, zahlBreite, zahlBreite, zahlBreite, zahlBreite, zahlBreite, zahlBreite});
        table.setLockedWidth(true);
        PdfPCell cell;
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        BaseColor green1 = new BaseColor(193, 222, 133);
        BaseColor green2 = new BaseColor(251, 253, 247);
        int start = 0;
        if (teil == 2) {
            start = 6;
        }

        for (int i = start; i < tipps.length && i < start + 6; i++) {
            cell = new PdfPCell(new Phrase(String.format("%2d. Feld", i + 1), font));
            cell.setBorder(Rectangle.BOX);
            cell.setUseBorderPadding(true);
            cell.setBorderWidth(2f);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setBackgroundColor(green2);
            cell.setPadding(3);
            table.addCell(cell);
            int[] zahlen = tippToArray(tipps[i]);
            for (int zahl : zahlen) {
                cell = new PdfPCell(new Phrase(String.format("%d", zahl), font));
                cell.setPadding(3);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.BOX);
                cell.setUseBorderPadding(true);
                cell.setBorderWidth(2f);
                cell.setBorderColor(BaseColor.WHITE);
                cell.setBackgroundColor(green1);
                table.addCell(cell);
            }
        }

        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        return table;
    }

    private PdfPTable getZusatzSpielTable(Lottoschein lottoschein, Font font) throws DocumentException {
        int losnummer = lottoschein.getLosnummer();
        PdfPTable table = new PdfPTable(8);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        float zahlBreite = 20f;
        table.setTotalWidth(new float[]{80, zahlBreite, zahlBreite, zahlBreite, zahlBreite, zahlBreite, zahlBreite, zahlBreite});
        table.setLockedWidth(true);
        PdfPCell cell;

        BaseColor green1 = new BaseColor(193, 222, 133);
        BaseColor green2 = new BaseColor(251, 253, 247);

        // Zahlen erzeugen
        String[][] zusatzSpiele
                = {
                    {"Superzahl:", "", "", "", "", "", "", ""},
                    {"Spiel77:", "", "", "", "", "", "", ""},
                    {"Super 6:", "", "", "", "", "", "", ""},};
        for (int i = 0; i < 7; i++) {
            Integer zahl = losnummer % 10;
            if (i == 0) {
                zusatzSpiele[0][7] = zahl.toString();
            }
            zusatzSpiele[1][7 - i] = zahl.toString();
            if (i < 6) {
                zusatzSpiele[2][7 - i] = zahl.toString();
            }
            losnummer /= 10;
        }

        for (int i = 0; i < 3; i++) {
            if (i == 0 || (i == 1 && lottoschein.getIsSpiel77()) || (i == 2 && lottoschein.getIsSuper6()))  {
                cell = new PdfPCell(new Phrase(zusatzSpiele[i][0], font));
                cell.setBorder(Rectangle.BOX);
                cell.setUseBorderPadding(true);
                cell.setBorderWidth(2f);
                cell.setBorderColor(BaseColor.WHITE);
                cell.setBackgroundColor(green2);
                cell.setPadding(5);
                table.addCell(cell);
                for (int j = 1; j < 8; j++) {
                    cell = new PdfPCell(new Phrase(zusatzSpiele[i][j], font));
                    cell.setPadding(5);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Rectangle.BOX);
                    cell.setUseBorderPadding(true);
                    cell.setBorderWidth(2f);
                    cell.setBorderColor(BaseColor.WHITE);
                    cell.setBackgroundColor(green1);
                    table.addCell(cell);

                }
            }
        }

        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        return table;
    }

    private PdfPTable getDetailsTable(Lottoschein lottoschein, Font font) throws DocumentException {

        class MyPdfPCell extends PdfPCell {

            public MyPdfPCell(Phrase phrase, BaseColor color) {
                super(phrase);
                this.setBorder(Rectangle.BOX);
                this.setUseBorderPadding(true);
                this.setBorderWidth(2f);
                this.setBorderColor(BaseColor.WHITE);
                this.setBackgroundColor(color);
                this.setPadding(5);
            }

        }

        String spiel77 = lottoschein.getIsSpiel77() ? "Ja" : "Nein";
        String super6 = lottoschein.getIsSuper6() ? "Ja" : "Nein";
        String ziehungstage;
        if (lottoschein.getIsMittwoch() && lottoschein.getIsSamstag()) {
            ziehungstage = "Mittwoch und Samstag";
        } else if (lottoschein.getIsMittwoch()) {
            ziehungstage = "Mittwoch";

        } else {
            ziehungstage = "Samstag";
        }

        DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, Locale.GERMAN);
        String abgabedatum = df.format(lottoschein.getAbgabeDatum());
        String ersteZiehung = df.format(lottoschein.getLottoscheinziehungList().get(0).getZiehungId().getZiehungsdatum());
        String letzteZiehung = df.format(lottoschein.getLottoscheinziehungList().get(lottoschein.getLottoscheinziehungList().size() - 1).getZiehungId().getZiehungsdatum());

        Integer lz = lottoschein.getLaufzeit();
        String laufzeit = lz.toString() + (lz == 1 ? " Woche" : " Wochen");
        Double preis = (double) lottoschein.getKosten() / 100;
        String sPreis = String.format("%.2f EUR", preis);

        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        float zahlBreite = 120f;
        table.setTotalWidth(new float[]{80, zahlBreite});
        table.setLockedWidth(true);
        BaseColor green1 = new BaseColor(193, 222, 133);
        BaseColor green2 = new BaseColor(251, 253, 247);
        PdfPCell cell;

        cell = new MyPdfPCell(new Phrase("Spiel77:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(spiel77, font), green1);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase("Super 6:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(super6, font), green1);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase("Abgabedatum:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(abgabedatum, font), green1);
        table.addCell(cell);

        cell = new MyPdfPCell(new Phrase("Ziehungstage:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(ziehungstage, font), green1);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase("Erste Ziehung:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(ersteZiehung, font), green1);
        table.addCell(cell);

        cell = new MyPdfPCell(new Phrase("Letzte Ziehung:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(letzteZiehung, font), green1);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase("Laufzeit:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(laufzeit, font), green1);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase("Preis:", font), green2);
        table.addCell(cell);
        cell = new MyPdfPCell(new Phrase(sPreis, font), green1);
        table.addCell(cell);
        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);

        return table;
    }

    public String generateQuittung() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        //HttpServletResponse response
        //      = (HttpServletResponse) context.getExternalContext().getResponse();
        HttpServletRequest request
                = (HttpServletRequest) context.getExternalContext().getRequest();
        //response.setContentType("application/pdf");
        //response.setHeader("Content-disposition",
        //      "inline=filename=file.pdf");
        try {
            String quittungPath = "/resources/text/quittung_text.html";
//            String quittungPath="/text/quittung_text.html";
            String briefbogenPath = "/resources/text/briefbogen.pdf";
            Document document = new Document();
            document.setMargins(Utilities.millimetersToPoints(20), Utilities.millimetersToPoints(25),
                    Utilities.millimetersToPoints(100), Utilities.millimetersToPoints(15));

            BaseFont bf_myriad = BaseFont.createFont("/fonts/MyriadPro-Regular.otf", BaseFont.WINANSI, BaseFont.EMBEDDED);
            //BaseFont bf_myriad = BaseFont.createFont("/fonts/MyriadRegularCondensed.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
            //BaseFont bf_myriad = BaseFont.createFont("/fonts/MotionPicture_PersonalUseOnly.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font myriad = new Font(bf_myriad, 10);
            Font myriadbold = new Font(bf_myriad, 10, Font.BOLD);
            Font myriad11 = new Font(bf_myriad, 11);

            //FontFactory.register("/fonts/MyriadPro-Regular.otf", "Myriad");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            document.open();

            InputStream is = context.getExternalContext().getResourceAsStream(quittungPath);
            //InputStream is = new FileInputStream(quittungPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            String quittungtext = sb.toString();

            long[] tipps = ByteLongConverter.byteToLong(lottoschein.getTipps());
            int losnummer = lottoschein.getLosnummer();

            quittungtext = quittungtext.replace("[SPIELERNAME]", lottoschein.getKundeId().getVorname() + " " + lottoschein.getKundeId().getName());
            quittungtext = quittungtext.replace("[SPIELERNACHNAME]", lottoschein.getKundeId().getName());
            quittungtext = quittungtext.replace("[KUNDEID]", String.format("%07d", lottoschein.getKundeId().getKundeId()));
            quittungtext = quittungtext.replace("[LOSNUMMER]", String.format("%07d", losnummer));
            Date date = new Date();
            quittungtext = quittungtext.replace("[DATUM]", SimpleDateFormat.getDateInstance().format(date));
            quittungtext = quittungtext.replace("[ANZAHLFELDER]", String.format("%d " + (tipps.length == 1 ? "Lottofeld" : "Lottofelder"), tipps.length));

            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontProvider.register("/fonts/MyriadPro-Regular.otf", "Lottofont");
            //fontProvider.register("/fonts/MyriadRegularCondensed.ttf", "Lottofont");
            //fontProvider.register("/fonts/MotionPicture_PersonalUseOnly.ttf","Lottofont");

            String css = "body {font-family: Lottofont; font-size: 10pt;} \n"
                    + "p { margin-bottom: 5pt;} div.quittung_gross {color: #5F852E; height: 40px; font-size: 16pt; font-weight: bold; font-style: italic;} \n"
                    + "div.betreff {height: 40px; font-size: 11pt; font-weight: bold; float:left;} \n"
                    + "div.datum {float:right;} \n"
                    + "div.vorrede {padding-top: 15pt;}";

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

            worker.parseXHtml(pdfWriter, document, new ByteArrayInputStream(quittungtext.getBytes()), new ByteArrayInputStream(css.getBytes()), fontProvider);

// *******************************************************************
            PdfPTable tippTable1 = getLottoTippTable(tipps, 1, myriad);
            PdfPTable tippTable2 = null;
            if (tipps.length > 6) {
                tippTable2 = getLottoTippTable(tipps, 2, myriad);
            }

            PdfPTable zusatzTable = getZusatzSpielTable(lottoschein, myriad);
            PdfPTable detailsTable = getDetailsTable(lottoschein, myriad);

// ******************* Lottotipps *********************************
            PdfPTable outerTable = new PdfPTable(2);

            outerTable.setTotalWidth(new float[]{240, 240});
            outerTable.setLockedWidth(true);

            PdfPCell layoutCellLeft = new PdfPCell();
            layoutCellLeft.setBorder(Rectangle.NO_BORDER);

            layoutCellLeft.addElement(tippTable1);

            PdfPCell layoutCellRight = new PdfPCell();
            layoutCellRight.setBorder(Rectangle.NO_BORDER);
            if (tippTable2 != null) {
                layoutCellRight.addElement(tippTable2);

            }

            if (tipps.length <= 8) {
                layoutCellRight.addElement(zusatzTable);
            }

            outerTable.addCell(layoutCellLeft);
            outerTable.addCell(layoutCellRight);
            document.add(outerTable);

// ******************************************************************
            outerTable = new PdfPTable(2);

            outerTable.setTotalWidth(new float[]{240, 240});
            outerTable.setLockedWidth(true);

// ******************* Zusatzspiele *********************************
            layoutCellLeft = new PdfPCell();
            layoutCellLeft.setBorder(Rectangle.NO_BORDER);

            layoutCellLeft.addElement(new Phrase("Details:", myriadbold));
            layoutCellLeft.addElement(detailsTable);
            outerTable.addCell(layoutCellLeft);
// ******************* Details *********************************

            layoutCellRight = new PdfPCell();
            layoutCellRight.setBorder(Rectangle.NO_BORDER);
            if (tipps.length > 8) {
                layoutCellRight.addElement(new Phrase(" ", myriadbold));
                layoutCellRight.addElement(zusatzTable);
            } else {
                layoutCellRight.addElement(null);
            }
            outerTable.addCell(layoutCellRight);

            document.add(outerTable);

// ******************* Abschied *********************************
            document.add(new Phrase("Mit freundlichen Grüßen\n", myriad));
            document.add(new Phrase("Ihr wbs-Lotto Team", myriad));

//            document.add(new Paragraph("Schönen guten Tag " + lottoschein.getKundeId().getVorname() + " " + lottoschein.getKundeId().getName() + ",", myriad));
// ******************* Adresse einfügen ************************************
            Kunde kunde = lottoschein.getKundeId();
            Adresse adr = kunde.getAdresseList().get(0);
            Phrase address = new Phrase(kunde.getVorname() + " " + kunde.getName() + "\n", myriad);
            address.add(new Chunk(adr.getStrasse() + " " + adr.getHausnummer() + "\n", myriad));
            address.add(new Chunk(adr.getPlz() + " " + adr.getOrt() + "\n", myriad));
            float x = Utilities.millimetersToPoints(20);
            float y = Utilities.millimetersToPoints(250);
            ColumnText ct = new ColumnText(pdfWriter.getDirectContent());
            ct.setSimpleColumn(
                    address,
                    x + 10, y,
                    Utilities.millimetersToPoints(100), Utilities.millimetersToPoints(70), 12, Element.ALIGN_LEFT);
            ct.go();

            document.close();
            baos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            PdfReader quittungReader = new PdfReader(bais);
            PdfReader briefbogenReader = new PdfReader(context.getExternalContext().getResourceAsStream(briefbogenPath));

            HttpServletResponse response
                    = (HttpServletResponse) context.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=spielquittung.pdf");
            PdfStamper stamper = new PdfStamper(quittungReader, response.getOutputStream());

            PdfImportedPage briefbogenSeite = stamper.getImportedPage(briefbogenReader, 1);
            PdfContentByte background;
            background = stamper.getUnderContent(1);
            background.addTemplate(briefbogenSeite, 0, 0);
            stamper.close();

        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }

        return "ok";
    }

}

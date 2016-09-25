package ua.nure.bycheva.SummaryTask4.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.nure.bycheva.SummaryTask4.Path;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetBean;
import ua.nure.bycheva.SummaryTask4.db.bean.SheetEntrantBean;
import ua.nure.bycheva.SummaryTask4.db.dao.DAOManager;
import ua.nure.bycheva.SummaryTask4.db.dao.SheetDAO;
import ua.nure.bycheva.SummaryTask4.db.dao.Table;
import ua.nure.bycheva.SummaryTask4.db.entity.User;
import ua.nure.bycheva.SummaryTask4.exception.AppException;
import ua.nure.bycheva.SummaryTask4.exception.MessageManager;
import ua.nure.bycheva.SummaryTask4.util.FileManager;

/**
 * Generate pdf file generation controller.
 */
public class GeneratePdfFile extends HttpServlet{

    private static final Logger LOG = Logger.getLogger(GeneratePdfFile.class);

    private static final String SHEET_FILE_NAME = "Sheet.pdf";

    private static final String FONT = "font/FreeSans.ttf";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request,response);
    }

    /**
     * Generation file method of this controller.
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOG.debug("Servlet starts");

        try {
            Long sheetId = Long.valueOf(request.getParameter("id"));

            SheetBean sheet = ((SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET)).findSheetBeanById(sheetId);

            List<SheetEntrantBean> entrants = ((SheetDAO) DAOManager.getInstance().getDAO(Table.SHEET)).findAllEntrantsSheets(sheetId);

            File generatedFile = generatePdfFile(request, sheet, entrants);

            if (generatedFile.exists()) {
                openPdfFile(generatedFile, response);
            } else {
                request.setAttribute("file_error", MessageManager.getInstance().getProperty(MessageManager.ERR_CANNOT_GENERATE_AND_OPEN_FILE));
            }
        } catch (AppException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request,response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        LOG.debug("Servlet finished");
    }

    /**
     * Open file and show into browser.
     * @param generatedFile File object
     * @param response HttpServletResponse object
     * @throws IOException
     */
    private void openPdfFile(File generatedFile, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletOutputStream outs =  response.getOutputStream();
        //---------------------------------------------------------------
        // Set the output data's mime type
        //---------------------------------------------------------------
        response.setContentType( "application/pdf" );  // MIME type for pdf doc

        //------------------------------------------------------------
        // Content-disposition header - don't open in browser and
        // set the "Save As..." filename.
        // *There is reportedly a bug in IE4.0 which  ignores this...
        //------------------------------------------------------------
        response.setHeader("Content-disposition","inline; filename=" + generatedFile.getName());

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            InputStream isr=new FileInputStream(generatedFile);
            bis = new BufferedInputStream(isr);
            bos = new BufferedOutputStream(outs);
            byte[] buff = new byte[bis.available()];
            int bytesRead;
            // Simple read/write loop.
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception ----- Message ---"+e);
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }

    }

    /**
     * Generate pdf file with sheet information and passed entrants list
     * @param request HttpServletRequest object
     * @param sheet Sheet object
     * @param entrants List of SheetEntrantBean objects
     * @return Created file. File object
     * @throws IOException
     * @throws DocumentException
     */
    private File generatePdfFile(HttpServletRequest request, SheetBean sheet, List<SheetEntrantBean> entrants) throws IOException, DocumentException {
        File sheetFile = null;

        try {
            Document document = new Document();

            Locale userLocale = new Locale((String) request.getSession().getAttribute("userLocale"));

            String sessionId = request.getRequestedSessionId();

            String pathToWeb = request.getServletContext().getRealPath(Path.TMP_DIRECTORY_NAME);

            String fileName = "/" + sessionId + "_" + SHEET_FILE_NAME;

            String generatedFileName = pathToWeb + fileName;

            sheetFile = new File(generatedFileName);

            PdfWriter.getInstance(document, new FileOutputStream(sheetFile));

            document.open();

            addMetaData(document,request);

            Anchor anchor = new Anchor("Result");
            anchor.setName("Result");

            String sheetHeader = MessageManager.getInstance().getProperty(FileManager.FILE_SHEET_TITLE, userLocale);
            Paragraph paragraph = new Paragraph(addPhrase(sheetHeader));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            String facultyName = sheet.getFacultyName();
            LOG.trace("Faculty name --> " + facultyName);
            paragraph = new Paragraph(addPhrase(facultyName));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            String date = new SimpleDateFormat("dd-MM-YYYY").format(sheet.getCreateDate());
            String text = "# "+ sheet.getUid() + " " + MessageManager.getInstance().getProperty(FileManager.FILE_SHEET_CREATION_TITLE, userLocale) + " " + date;

            paragraph = new Paragraph(addPhrase(text));
            paragraph.setAlignment(Element.ALIGN_CENTER);

            createTable(paragraph, entrants, userLocale);

            document.add(paragraph);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return sheetFile;
    }

    /**
     * Work with pdf file. Add new Phrase using custom font.
     * @param phrase Target text
     * @return Phrase object
     * @throws IOException
     * @throws DocumentException
     */
    private static Phrase addPhrase(String phrase) throws IOException, DocumentException {
        BaseFont cyrilicFont = BaseFont.createFont(FONT, "Cp1251", BaseFont.EMBEDDED);
        return new Phrase(phrase, new Font(cyrilicFont, 14));
    }

    /**
     * Work with pdf document. Add meta data about template sheet file.
     * @param document com.itextpdf.text.Document object.
     * @param request HttpServletRequest object
     */
    private static void addMetaData(Document document, HttpServletRequest request) {
        document.addTitle("Generated Sheet");
        document.addSubject("Sheet by faculty");

        User user = (User) request.getSession().getAttribute("user");
        document.addAuthor(user.getFirstName() + " " + user.getLastName());
        document.addCreator("ua.nure.bycheva.SummaryTask4");
    }

    /**
     * Work with pdf document. Add table with entrant information rows.
     * @param paragraph
     * @param entrants
     * @param locale
     * @throws DocumentException
     * @throws IOException
     */
    private static void createTable(Paragraph paragraph, List<SheetEntrantBean> entrants, Locale locale)
            throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(90);

        // Defiles the relative width of the columns
        float[] columnWidths = new float[] {15f, 40f, 20f, 15f};
        table.setWidths(columnWidths);

        String[] headers = {
                FileManager.FILE_TABLE_HEADER_ID,
                FileManager.FILE_TABLE_HEADER_FULL_NAME,
                FileManager.FILE_TABLE_HEADER_SCORE,
                FileManager.FILE_TABLE_HEADER_STATUS
        };

        for(String header: headers){
            String name = MessageManager.getInstance().getProperty(header, locale);
            addCell(name, table);
        }
        table.setHeaderRows(1);

        for(SheetEntrantBean entrantBean: entrants){
            prepareRow(entrantBean, table);
        }

        paragraph.add(table);
    }

    private static void prepareRow(SheetEntrantBean entrantBean, PdfPTable table) throws IOException, DocumentException {
        addCell(entrantBean.getEntrantId(), table);
        addCell(entrantBean.getFullName(), table);
        addCell(entrantBean.getAvgPoint(), table);
        addCell(entrantBean.getPassedStatus(), table);

    }

    private static void addCell(Object info, PdfPTable table) throws IOException, DocumentException {
        PdfPCell c1 = new PdfPCell(addPhrase(String.valueOf(info)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(c1);
    }
}

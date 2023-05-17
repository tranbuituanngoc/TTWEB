package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.Color;
import model.Product;
import model.Size;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.ProductCategoryService;
import service.ProductImportedService;
import service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@WebServlet(name = "ExportFile", value = "/ExportFile")
public class ExportFile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("excel")) {
            GenerateExcelFile(request, response);
        } else if (action.equalsIgnoreCase("pdf")) {
            generatePDF(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void GenerateExcelFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> productList = ProductService.getAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Product List");
            int rowIndex = 0;
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Product Name");
            row.createCell(1).setCellValue("Color");
            row.createCell(2).setCellValue("Size");
            row.createCell(3).setCellValue("Category");
            row.createCell(4).setCellValue("Price");
            row.createCell(5).setCellValue("Sale");
            row.createCell(6).setCellValue("Quantity");
            row.createCell(7).setCellValue("New");
            row.createCell(8).setCellValue("Description");

            // Tạo style cho header
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerStyle.setFont(headerFont);

            // Áp dụng style cho header
            for (int i = 0; i < 9; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    cell = row.createCell(i);
                }
                cell.setCellStyle(headerStyle);
            }

            for (Product product : productList) {
                for (Color color : product.getColor()) {
                    for (Size size : product.getSize()) {
                        row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(product.getProductName());
                        row.createCell(1).setCellValue(color.getDescrip());
                        row.createCell(2).setCellValue(size.getDescrip());
                        row.createCell(3).setCellValue(ProductCategoryService.selectByID(product.getCategory()));
                        row.createCell(4).setCellValue(ProductImportedService.getPrice(product.getProductID(), size.getIdSize(), color.getId_color()));
                        row.createCell(5).setCellValue(product.getSalePrice());
                        row.createCell(6).setCellValue(ProductImportedService.getQuantityDetail(product.getProductID(), size.getIdSize(), color.getId_color()));
                        row.createCell(7).setCellValue(product.getIsNew());
                        String description= product.getDescription();
                        if (description.length() > 100) {
                            description = description.substring(0, 100) + "...";
                        }
                        row.createCell(8).setCellValue(description);

                        //không lấy được ảnh do không có quyền truy cập vào thư mục Root :'<
//                        // Lấy đường dẫn của hình ảnh
//                        ServletContext context = request.getServletContext();
//                        String fullPath = context.getRealPath(product.getThumb());
//                        InputStream inputStream = new FileInputStream(fullPath);
//
//                        // Đọc và chuyển đổi hình ảnh thành mảng byte
//                        byte[] bytes = inputStream.readAllBytes();
//                        // Tạo biến pictureIdx để lấy loại định dạng ảnh cần để lưu vào file excel
//                        int pictureIdx = 0;
//                        String fileName = product.getThumb();
//                        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//
//                        List<? extends PictureData> pictureTypes = workbook.getAllPictures();
//                        for (int i = 0; i < pictureTypes.size(); i++) {
//                            if (pictureTypes.get(i).suggestFileExtension().equalsIgnoreCase(fileExtension)) {
//                                pictureIdx = workbook.addPicture(bytes, pictureTypes.get(i).getPictureType());
//                                break;
//                            }
//                        }
//
//                        // Dùng để tạo drawing và anchor
//                        CreationHelper helper = workbook.getCreationHelper();
//                        // Vùng vẽ --> đại diện cho một vùng vẽ trong bảng tính
//                        Drawing<?> drawing = sheet.createDrawingPatriarch();
//                        // Điểm neo --> đại diện cho vị trí mà ta dùng để đặt hình ảnh vào ô bảng tính
//                        ClientAnchor anchor = helper.createClientAnchor();
//                        anchor.setCol1(8);
//                        // rowIndex -1 vì để hình ảnh
//                        anchor.setCol1(8);
//                        //rowIndex -1 vì để hình ảnh được thêm vào cùng dòng với dữ liệu cuối cùng được thêm vào sheet
//                        anchor.setRow1(rowIndex - 1);
//                        Picture pict = drawing.createPicture(anchor, pictureIdx);
//                        pict.resize();
                    }
                }
            }
            // Tự động điều chỉnh độ rộng cột
            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi file
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=ProductList.xlsx");
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                workbook.write(outputStream);
            }
        }
    }

    public void generatePDF(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> productList = ProductService.getAll();
            Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onStartPage(PdfWriter writer, Document document) {
                    super.onStartPage(writer, document);
                    PdfContentByte cb = writer.getDirectContent();
                    BaseFont bf = null;
                    try {
                        bf = BaseFont.createFont("C:/Windows/Fonts/timesbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }
                    cb.setFontAndSize(bf, 12);
                }
            });
            document.open();
            PdfPTable table = new PdfPTable(9);
            PdfPCell cell = new PdfPCell(new Phrase("Product List"));
            cell.setColspan(10);
            table.addCell(cell);
            table.addCell(new Phrase("Product Name"));
            table.addCell(new Phrase("Color"));
            table.addCell(new Phrase("Size"));
            table.addCell(new Phrase("Category"));
            table.addCell(new Phrase("Price"));
            table.addCell(new Phrase("Sale"));
            table.addCell(new Phrase("Quantity"));
            table.addCell(new Phrase("New"));
//            table.addCell(new Phrase("Thumbnail"));
            table.addCell(new Phrase("Description"));
            for (Product product : productList) {
                for (Color color : product.getColor()) {
                    for (Size size : product.getSize()) {
                        table.addCell(new Phrase(product.getProductName()));
                        table.addCell(new Phrase(color.getDescrip()));
                        table.addCell(new Phrase(size.getDescrip()));
                        table.addCell(new Phrase(ProductCategoryService.selectByID(product.getCategory())));
                        table.addCell(new Phrase(String.valueOf(ProductImportedService.getPrice(product.getProductID(), size.getIdSize(), color.getId_color()))));
                        table.addCell(new Phrase(product.getSalePrice() + "%"));
                        table.addCell(new Phrase(String.valueOf(ProductImportedService.getQuantityDetail(product.getProductID(), size.getIdSize(), color.getId_color()))));
                        boolean isNew = product.getIsNew() == 1 ? true : false;
                        table.addCell(new Phrase(String.valueOf(isNew)));


                        //không lấy được ảnh do không có quyền truy cập vào thư mục Root :'<
//                        Image image = null;
//                        try {
//                            // Đọc hình ảnh từ URL hoặc đường dẫn file
//                            ServletContext context = request.getServletContext();
//                            URL imageUrl = new URL(context.getRealPath(product.getThumb()));
//                            image = Image.getInstance(imageUrl);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (image != null) {
//                            // Thêm hình ảnh vào trang PDF
//                            image.scaleToFit(100f, 100f); // thiết lập kích thước ảnh
//                            PdfPCell cellImage = new PdfPCell(image);
//                            cellImage.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                            cellImage.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            table.addCell(cellImage);
//                        } else {
//                            table.addCell(new Phrase("No Image"));
//                        }
                        String description = product.getDescription();
                        if (description.length() > 100) {
                            description = description.substring(0, 100) + "...";
                        }
                        table.addCell(description);
                    }
                }
            }
            document.add(table);
            document.close();
            writer.close();

            byte[] pdfContent = baos.toByteArray();
            response.setContentType("application/pdf");
            response.setContentLength(pdfContent.length);
            response.setHeader("Content-Disposition", "attachment;filename=productList.pdf");
            response.getOutputStream().write(pdfContent);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}

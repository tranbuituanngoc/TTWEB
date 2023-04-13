package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("/") + "UploadFileStore" + File.separator;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String thumbnailFileName = null;
        Part thumbnailPart = request.getPart("thumbnail");
        if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
            thumbnailFileName = UUID.randomUUID().toString() + "_" + thumbnailPart.getSubmittedFileName();
            String thumbnailFilePath = uploadPath + thumbnailFileName;
            File thumbnailFile = new File(thumbnailFilePath);
            FileUtils.copyInputStreamToFile(thumbnailPart.getInputStream(), thumbnailFile);
        }

        String imagesFileName = "";
        List<Part> imagesParts = (List<Part>) request.getParts();
        for (Part imagesPart : imagesParts) {
            if (imagesPart.getName().equals("images") && imagesPart.getSize() > 0) {
                String imagesPartFileName = UUID.randomUUID().toString() + "_" + imagesPart.getSubmittedFileName();
                String imagesPartFilePath = uploadPath + imagesPartFileName;
                File imagesPartFile = new File(imagesPartFilePath);
                FileUtils.copyInputStreamToFile(imagesPart.getInputStream(), imagesPartFile);
                imagesFileName += imagesPartFileName + ",";
            }
        }
        if (!imagesFileName.isEmpty()) {
            imagesFileName = imagesFileName.substring(0, imagesFileName.length() - 1);
        }

        request.setAttribute("thumbnailFileName", thumbnailFileName);
        request.setAttribute("imagesFileName", imagesFileName);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}

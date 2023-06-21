package controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import model.*;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

@WebServlet(name = "AddOrUpdateProduct", value = "/AddOrUpdateProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB

public class AddOrUpdateProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dcuoluhoa",
            "api_key", "345639439183833",
            "api_secret", "xoJDHnss7DBL1R_6cpQrph9p908"));

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String err = "";
        List<Color> listColor = ProductColorService.getAll();
        List<Size> listSize = ProductSizeService.getAll();
        List<Category> listCategory = ProductCategoryService.getAll();
        if (!action.equals(null)) {
            if (action.equals("getadd")) {
                request.setAttribute("err", "");
                request.setAttribute("listColor", listColor);
                request.setAttribute("listSize", listSize);
                request.setAttribute("listCategory", listCategory);
                request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
            }

            if (action.equals("getupdate")) {
                Product product = ProductService.getByIdAd(id);

                List<String> colors = new ArrayList<>();
                List<String> sizes = new ArrayList<>();

                for (Color pc : product.getColor()) {
                    String c = pc.getDescrip();
                    colors.add(c);
                }
                for (Size ps : product.getSize()) {
                    String c = ps.getDescrip();
//                    System.out.println("Size: " + c);
                    sizes.add(c);
                }

                String type = ProductCategoryService.selectByID(product.getCategory());

                String error = (request.getAttribute("err") + "").equals("null") ? "" : (String) request.getAttribute("err");

                List<Integer> inputListQ = product.getQuantityList();
                List<Integer> inputListP = product.getPriceList();
                List<Integer> inputListC = product.getCostList();

                request.setAttribute("typeSelected", type);
                request.setAttribute("sizeSelected", sizes);
                request.setAttribute("colorSelected", colors);

                request.setAttribute("inputListQ", inputListQ);
                request.setAttribute("inputListP", inputListP);
                request.setAttribute("inputListC", inputListC);

                request.setAttribute("product", product);
                request.setAttribute("err", error);
                request.setAttribute("listColor", listColor);
                request.setAttribute("listSize", listSize);
                request.setAttribute("listCategory", listCategory);

                String forward = "admin/AddProduct.jsp?";

                forward += "productname=" + product.getProductName();
                forward += "&sale=" + product.getSalePrice();
                forward += "&new=" + product.getIsNew();
                forward += "&description=" + product.getDescription();

                request.getRequestDispatcher(forward).forward(request, response);
            }

            if (action.equals("add")) {
                boolean isErr = false;
                String name = request.getParameter("productname");
                String type = request.getParameter("type");
                List<String> sizes = new ArrayList<>(Arrays.asList(request.getParameterValues("sizeProduct")));
                List<String> colors = new ArrayList<>(Arrays.asList(request.getParameterValues("colorProduct")));
                String sale = request.getParameter("sale");
                sale = sale.equals("") ? "0" : sale;
                String newProduct = request.getParameter("newProduct");
                String description = request.getParameter("description");

                Random rd = new Random();
                String random = System.currentTimeMillis() + rd.nextInt(100) + "";
                String idPro = "sp" + random.substring(random.length() - 8);
                Product p = new Product();

                Category category = ProductCategoryService.selectByDescrip(type);

                java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(todaysDate);
                Timestamp importDate = new Timestamp(calendar.getTimeInMillis());


                // Nếu lỗi thì set lại các attribute cho nó và gọi lại
                if (ProductService.existProductName(name) || description.isEmpty()) {
                    request.setAttribute("err", description.isEmpty() ? "Vui lòng không để trống phần mô tả!" : "Tên sản phẩm đã tồn tại");

                    //get all cate, size, color
                    request.setAttribute("listColor", listColor);
                    request.setAttribute("listSize", listSize);
                    request.setAttribute("listCategory", listCategory);

                    //get cate, size, color is chose
                    request.setAttribute("typeSelected", type);
                    request.setAttribute("sizeSelected", sizes);
                    request.setAttribute("colorSelected", colors);

                    List<String> inputListQ = new ArrayList<>();
                    List<String> inputListP = new ArrayList<>();
                    List<String> inputListC = new ArrayList<>();

                    //get quantity, price, cost
                    for (String s : sizes) {
                        for (String c : colors) {
                            String quantityDetail = request.getParameter(s + " " + c + "q");
                            String priceDetail = request.getParameter(s + " " + c + "p");
                            String costDetail = request.getParameter(s + " " + c + "c");
                            inputListQ.add(quantityDetail);
                            inputListP.add(priceDetail);
                            inputListC.add(costDetail);
                        }
                    }

                    request.setAttribute("inputListQ", inputListQ);
                    request.setAttribute("inputListP", inputListP);
                    request.setAttribute("inputListC", inputListC);

                    isErr = true;
                    request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
                }
                if (!isErr) {
                    p.setProductID(idPro);
                    p.setCategory(category.getId() + "");
                    p.setProductName(name);
                    p.setDescription(description);
                    p.setStatus(1);
                    p.setSalePrice(Integer.parseInt(sale));
                    p.setIsNew(Integer.parseInt(newProduct));

                    //upload image
                    Part thumbnailPart = request.getPart("thumbnail");
                    String thumbnailFileName = getFileName(thumbnailPart);
                    if (thumbnailFileName != null && !thumbnailFileName.isEmpty()) {
                        try {
                            // Lưu tệp tin vào ổ đĩa cục bộ
                            File thumbnailFile = new File(thumbnailFileName);
                            try (InputStream thumbnailInputStream = thumbnailPart.getInputStream();
                                 OutputStream thumbnailOutputStream = new FileOutputStream(thumbnailFile)) {
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = thumbnailInputStream.read(buffer)) != -1) {
                                    thumbnailOutputStream.write(buffer, 0, length);
                                }
                            }

                            // Upload tệp tin lên Cloudinary
                            Map<String, String> thumbnailUploadResult = cloudinary.uploader().upload(thumbnailFile,
                                    ObjectUtils.asMap("public_id", thumbnailFileName));
                            String thumbnailUrl = thumbnailUploadResult.get("url");
                            p.setThumb(thumbnailUrl);

                            // Xóa tệp tin cục bộ
                            thumbnailFile.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    // Upload image files (multiple file upload)
                    List<ImageProduct> images = new ArrayList<>();
                    List<Part> imageParts = (List<Part>) request.getParts();
                    for (Part imagePart : imageParts) {
                        if (imagePart.getName().equals("images")) {
                            String imageFileName = getFileName(imagePart);
                            if (imageFileName != null && !imageFileName.isEmpty()) {
                                try {
                                    // Lưu tệp tin vào ổ đĩa cục bộ
                                    File imageFile = new File(imageFileName);
                                    try (InputStream imageInputStream = imagePart.getInputStream();
                                         OutputStream imageOutputStream = new FileOutputStream(imageFile)) {
                                        byte[] buffer = new byte[1024];
                                        int length;
                                        while ((length = imageInputStream.read(buffer)) != -1) {
                                            imageOutputStream.write(buffer, 0, length);
                                        }
                                    }

                                    // Upload tệp tin lên Cloudinary với public ID là imageFileName
                                    Map<String, String> imageUploadResult = cloudinary.uploader().upload(imageFile,
                                            ObjectUtils.asMap("public_id", imageFileName));
                                    String imageUrl = imageUploadResult.get("url");
                                    ImageProduct i = new ImageProduct(imageUrl);
                                    images.add(i);

                                    // Xóa tệp tin cục bộ
                                    imageFile.delete();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    p.setImage(images);

                    //Insert Product Quantity, Price And Cost
                    for (String s : sizes) {
                        Size size = ProductSizeService.selectByDescrip(s);
                        for (String c : colors) {
                            Color color = ProductColorService.selectByDescrip(c);
                            String quantityDetail = request.getParameter(s + " " + c + "q");
                            String priceDetail = request.getParameter(s + " " + c + "p");
                            String costDetail = request.getParameter(s + " " + c + "c");
                            ProductImportedService.insert(p.getProductID(), size.getIdSize(), color.getId_color(), Integer.parseInt(quantityDetail), importDate, Integer.parseInt(priceDetail), Integer.parseInt(costDetail));
                        }
                    }

                    //insert image and thumb
                    ProductImageService.insertThumbProduct(p);
                    ProductImageService.insertImageProduct(p);


                    //insert product
                    ProductService.insert(p);
                    request.getSession().setAttribute("msg", "Thêm sản phẩm thành công!");
                    request.getSession().setAttribute("res", "true");
                    response.sendRedirect("ListProductAd");
                }
            }

            if (action.equals("update")) {
                boolean isErr = false;
                String name = request.getParameter("productname");
                String type = request.getParameter("type");
                List<String> sizes = new ArrayList<>(Arrays.asList(request.getParameterValues("sizeProduct")));
                List<String> colors = new ArrayList<>(Arrays.asList(request.getParameterValues("colorProduct")));
                String sale = request.getParameter("sale");
                sale = sale.equals("") ? "0" : sale;
                String newProduct = request.getParameter("newProduct");
                String description = request.getParameter("description");
                int status = Integer.parseInt(request.getParameter("newProduct"));

                java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(todaysDate);
                Timestamp importDate = new Timestamp(calendar.getTimeInMillis());

                Product p = new Product();
                Product product = ProductService.getByIdAd(id);
                if (!name.equalsIgnoreCase(product.getProductName())) {
                    if (ProductService.existProductName(name)) {
                        request.setAttribute("err", "Tên sản phẩm đã tồn tại");
                        request.getRequestDispatcher("AddOrUpdateProduct?action=getupdate&id=" + id).forward(request, response);
                        isErr = true;
                    }
                }
                if (!isErr) {
                    Category category = ProductCategoryService.selectByDescrip(type);

                    p.setProductID(id);
                    p.setCategory(category.getId() + "");
                    p.setProductName(name);
                    if (!description.isEmpty()) {
                        p.setDescription(description);
                    }
                    p.setStatus(status);
                    p.setSalePrice(Integer.parseInt(sale));
                    p.setIsNew(Integer.parseInt(newProduct));

                    //upload image
                    // Upload thumbnail file (single file upload)
                    Part thumbnailPart = request.getPart("thumbnail");
                    String thumbnailFileName = getFileName(thumbnailPart);
                    if (thumbnailFileName != null && !thumbnailFileName.isEmpty()) {
                        try {
                            // Lưu tệp tin vào ổ đĩa cục bộ
                            File thumbnailFile = new File(thumbnailFileName);
                            try (InputStream thumbnailInputStream = thumbnailPart.getInputStream();
                                 OutputStream thumbnailOutputStream = new FileOutputStream(thumbnailFile)) {
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = thumbnailInputStream.read(buffer)) != -1) {
                                    thumbnailOutputStream.write(buffer, 0, length);
                                }
                            }

                            // Upload tệp tin lên Cloudinary
                            Map<String, String> thumbnailUploadResult = cloudinary.uploader().upload(thumbnailFile,
                                    ObjectUtils.asMap("public_id", thumbnailFileName));
                            String thumbnailUrl = thumbnailUploadResult.get("url");
                            p.setThumb(thumbnailUrl);
                            ProductImageService.updateThumbProduct(p);
                            // Xóa tệp tin cục bộ
                            thumbnailFile.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Upload image files (multiple file upload)
                    List<ImageProduct> images = new ArrayList<>();
                    List<Part> imageParts = (List<Part>) request.getParts();
                    for (Part imagePart : imageParts) {
                        if (imagePart.getName().equals("images")) {
                            String imageFileName = getFileName(imagePart);
                            if (imageFileName != null && !imageFileName.isEmpty()) {
                                try {
                                    // Lưu tệp tin vào ổ đĩa cục bộ
                                    File imageFile = new File(imageFileName);
                                    try (InputStream imageInputStream = imagePart.getInputStream();
                                         OutputStream imageOutputStream = new FileOutputStream(imageFile)) {
                                        byte[] buffer = new byte[1024];
                                        int length;
                                        while ((length = imageInputStream.read(buffer)) != -1) {
                                            imageOutputStream.write(buffer, 0, length);
                                        }
                                    }

                                    // Upload tệp tin lên Cloudinary với public ID là imageFileName
                                    Map<String, String> imageUploadResult = cloudinary.uploader().upload(imageFile,
                                            ObjectUtils.asMap("public_id", imageFileName));
                                    String imageUrl = imageUploadResult.get("url");
                                    ImageProduct i = new ImageProduct(imageUrl);
                                    images.add(i);

                                    // Xóa tệp tin cục bộ
                                    imageFile.delete();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                    if (images.size() > 0) {
                        p.setImage(images);
                        ProductImageService.updateImageProduct(p);
                    }

                    //Delete Old Product Quantity, Price And Cost
                    ProductImportedService.delete(id);

                    //Insert Product Quantity, Price And Cost
                    for (String s : sizes) {
                        Size size = ProductSizeService.selectByDescrip(s);
                        for (String c : colors) {
                            Color color = ProductColorService.selectByDescrip(c);
                            String quantityDetail = request.getParameter(s + " " + c + "q");
                            String priceDetail = request.getParameter(s + " " + c + "p");
                            String costDetail = request.getParameter(s + " " + c + "c");
                            ProductImportedService.insert(p.getProductID(), size.getIdSize(), color.getId_color(), Integer.parseInt(quantityDetail), importDate, Integer.parseInt(priceDetail), Integer.parseInt(costDetail));
                        }
                    }

                    //update product
                    ProductService.updateProduct(id, p);
                    request.getSession().setAttribute("msg", "Chỉnh sửa thành công!");
                    request.getSession().setAttribute("res", "true");
                    response.sendRedirect("ListProductAd");
                }
            }
            if (action.equals("delete")) {
                ProductService.deleteProduct(id);
                ProductImportedService.delete(id);
                ProductImageService.deleteImageProduct(id);
                request.getSession().setAttribute("msg", "Xóa sản phẩm thành công!");
                request.getSession().setAttribute("res", "true");
                response.sendRedirect("ListProductAd");
            }
            if (action.equals("hide")) {
                ProductService.hideProduct(id);
                request.getSession().setAttribute("msg", "Cập nhật trạng thái sản phẩm thành công!");
                request.getSession().setAttribute("res", "true");
                response.sendRedirect("ListProductAd");
            }
            if (action.equals("show")) {
                ProductService.nothideProduct(id);
                request.getSession().setAttribute("msg", "Cập nhật trạng thái sản phẩm thành công!");
                request.getSession().setAttribute("res", "true");
                response.sendRedirect("ListProductAd");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 5);
            }
        }
        return null;
    }

}

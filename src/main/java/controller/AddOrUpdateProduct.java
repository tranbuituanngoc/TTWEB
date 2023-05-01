package controller;

import model.*;
import service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet(name = "AddOrUpdateProduct", value = "/AddOrUpdateProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB

public class AddOrUpdateProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "UploadFileStore";

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String err = "";
        List<ProductColor> listColor = ProductColorService.getAll();
        List<ProductSize> listSize = ProductSizeService.getAll();
        List<ProductCategory> listCategory = ProductCategoryService.getAll();
        if (!action.equals(null)) {
            if (action.equals("getadd")) {
                request.setAttribute("err", "");
                request.setAttribute("listColor", listColor);
                request.setAttribute("listSize", listSize);
                request.setAttribute("listCategory", listCategory);
                request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
            }

            if (action.equals("getupdate")) {
                Product product = ProductService.getById(id);

                List<String> colors = new ArrayList<>();
                List<String> sizes = new ArrayList<>();

                for (ProductColor pc : product.getColor()) {
                    String c = pc.getDescription();
                    colors.add(c);
                }
                for (ProductSize ps : product.getSize()) {
                    String c = ps.getDescription();
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

                ProductCategory category = ProductCategoryService.selectByDescrip(type);

                java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(todaysDate);
                Timestamp importDate = new Timestamp(calendar.getTimeInMillis());


                // Nếu lỗi thì set lại các attribute cho nó và gọi lại
                if (ProductService.existProductName(name)) {
                    request.setAttribute("err", "Tên sản phẩm đã tồn tại");

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
                    p.setCategory(category.getId_Category() + "");
                    p.setProductName(name);
                    p.setDescription(description);
                    p.setStatus(1);
                    p.setSalePrice(Integer.parseInt(sale));
                    p.setIsNew(Integer.parseInt(newProduct));

                    //upload image
                    ServletContext servletContext = getServletContext();
                    int majorVersion = servletContext.getMajorVersion();
                    int minorVersion = servletContext.getMinorVersion();
                    System.out.println("Servlet API version: " + majorVersion + "." + minorVersion);
                    String uploadPath = getServletContext().getRealPath("/") + "UploadFileStore";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
//                  Upload thumbnail file (single file upload)
                    Part thumbnailPart = request.getPart("thumbnail");
                    String thumbnailFileName = getFileName(thumbnailPart);
                    if (thumbnailFileName != null && !thumbnailFileName.isEmpty()) {
                        thumbnailPart.write(uploadPath + File.separator + thumbnailFileName);
                        System.out.println("Thumbnail file uploaded to: " + uploadPath + File.separator + thumbnailFileName);
                        p.setThumb("UploadFileStore/" + thumbnailFileName);
                    }

                    // Upload image files (multiple file upload)
                    List<ImageProduct> images = new ArrayList<>();
                    List<Part> imageParts = (List<Part>) request.getParts();
                    for (Part imagePart : imageParts) {
                        System.out.println(imagePart.getName());
                        if (imagePart.getName().equals("images")) {
                            String imageFileName = getFileName(imagePart);
                            if (imageFileName != null && !imageFileName.isEmpty()) {
                                imagePart.write(uploadPath + File.separator + imageFileName);
                                System.out.println("Image file uploaded to: " + uploadPath + File.separator + imageFileName);
                                ImageProduct i = new ImageProduct("UploadFileStore/" + imageFileName);
                                images.add(i);
                            }
                        }
                    }


                    p.setImage(images);

                    //Insert Product Quantity, Price And Cost
                    for (String s : sizes) {
                        ProductSize size = ProductSizeService.selectByDescrip(s);
                        for (String c : colors) {
                            ProductColor color = ProductColorService.selectByDescrip(c);
                            String quantityDetail = request.getParameter(s + " " + c + "q");
                            String priceDetail = request.getParameter(s + " " + c + "p");
                            String costDetail = request.getParameter(s + " " + c + "c");
                            ProductImportedService.insert(p.getProductID(), size.getId_Size(), color.getId_Color(), Integer.parseInt(quantityDetail), importDate, Integer.parseInt(priceDetail), Integer.parseInt(costDetail));
                        }
                    }

                    //insert image and thumb
                    ProductImageService.insertThumbProduct(p);
                    ProductImageService.insertImageProduct(p);


                    //insert product
                    ProductService.insert(p);
                    request.setAttribute("err", "Thêm sản phẩm thành công");
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

                java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(todaysDate);
                Timestamp importDate = new Timestamp(calendar.getTimeInMillis());

                Product p = new Product();
                Product product = ProductService.getById(id);
                if(!name.equalsIgnoreCase(product.getProductName())){
                    if (ProductService.existProductName(name)) {
                        request.setAttribute("err", "Tên sản phẩm đã tồn tại");
                        request.getRequestDispatcher("AddOrUpdateProduct?action=getupdate&id=" + id).forward(request, response);
                        isErr = true;
                    }
                }
                if (!isErr) {
                    ProductCategory category = ProductCategoryService.selectByDescrip(type);

                    p.setProductID(id);
                    p.setCategory(category.getId_Category() + "");
                    p.setProductName(name);
                    p.setDescription(description);
                    p.setStatus(1);
                    p.setSalePrice(Integer.parseInt(sale));
                    p.setIsNew(Integer.parseInt(newProduct));

                    //upload image
                    ServletContext servletContext = getServletContext();
                    int majorVersion = servletContext.getMajorVersion();
                    int minorVersion = servletContext.getMinorVersion();
                    System.out.println("Servlet API version: " + majorVersion + "." + minorVersion);
                    String uploadPath = getServletContext().getRealPath("/") + "UploadFileStore";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
//                  Upload thumbnail file (single file upload)
                    Part thumbnailPart = request.getPart("thumbnail");
                    String thumbnailFileName = getFileName(thumbnailPart);
                    if (thumbnailFileName != null && !thumbnailFileName.isEmpty()) {
                        thumbnailPart.write(uploadPath + File.separator + thumbnailFileName);
                        System.out.println("Thumbnail file uploaded to: " + uploadPath + File.separator + thumbnailFileName);
                        p.setThumb("UploadFileStore/" + thumbnailFileName);
                    } else {
                        //Xử lý khi người dùng không upload ảnh mới
                        p.setThumb(product.getThumb());
                    }

                    // Upload image files (multiple file upload)
                    List<ImageProduct> images = new ArrayList<>();

                    List<Part> imageParts = (List<Part>) request.getParts();

                    for (Part imagePart : imageParts) {
                        System.out.println(imagePart.getName());
                        if (imagePart.getName().equals("images")) {
                            String imageFileName = getFileName(imagePart);
                            if (imageFileName != null && !imageFileName.isEmpty()) {
                                imagePart.write(uploadPath + File.separator + imageFileName);
                                System.out.println("Image file uploaded to: " + uploadPath + File.separator + imageFileName);
                                ImageProduct i = new ImageProduct("UploadFileStore/" + imageFileName);
                                images.add(i);
                            }
                        }
                    }

                    if (images.size() > 0) {
                        p.setImage(images);
                    } else {
                        p.setImage(product.getImage());
                    }

                    //Delete Old Product Quantity, Price And Cost
                    ProductImportedService.delete(id);

                    //Insert Product Quantity, Price And Cost
                    for (String s : sizes) {
                        ProductSize size = ProductSizeService.selectByDescrip(s);
                        for (String c : colors) {
                            ProductColor color = ProductColorService.selectByDescrip(c);
                            String quantityDetail = request.getParameter(s + " " + c + "q");
                            String priceDetail = request.getParameter(s + " " + c + "p");
                            String costDetail = request.getParameter(s + " " + c + "c");
                            ProductImportedService.insert(p.getProductID(), size.getId_Size(), color.getId_Color(), Integer.parseInt(quantityDetail), importDate, Integer.parseInt(priceDetail), Integer.parseInt(costDetail));
                        }
                    }

                    //update image and thumb
                    ProductImageService.updateThumbProduct(p);
                    ProductImageService.updateImageProduct(p);

                    //update product
                    ProductService.updateProduct(id, p);
                    request.setAttribute("err", "Chỉnh sửa thành công");
                    response.sendRedirect("ListProductAd");
                }
            }
            if (action.equals("delete")) {
                ProductService.deleteProduct(id);
                response.sendRedirect("ListProductAd");
            }
            if (action.equals("hide")) {
                ProductService.hideProduct(id);
                response.sendRedirect("ListProductAd");
            }
            if (action.equals("show")) {
                ProductService.nothideProduct(id);
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
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
}

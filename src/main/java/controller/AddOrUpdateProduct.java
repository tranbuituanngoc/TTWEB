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
        if (!action.equals(null)) {
            if (action.equals("getadd")) {
                request.setAttribute("err", "");
                List<ProductColor> listColor = ProductColorService.getAll();
                List<ProductSize> listSize = ProductSizeService.getAll();
                List<ProductCategory> listCategory = ProductCategoryService.getAll();
                request.setAttribute("listColor", listColor);
                request.setAttribute("listSize", listSize);
                request.setAttribute("listCategory", listCategory);
                request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
            }

            if (action.equals("getupdate")) {
                Product product = ProductService.getById(id);
                String forward = "admin/AddProduct.jsp?";
                List<ProductColor> listColor = ProductColorService.getAll();
                List<ProductSize> listSize = ProductSizeService.getAll();
                List<ProductCategory> listCategory = ProductCategoryService.getAll();
                request.setAttribute("listColor", listColor);
                request.setAttribute("listSize", listSize);
                request.setAttribute("listCategory", listCategory);
                forward += "productname=" + product.getProductName();
                forward += "&type=" + product.getCategory();
                forward += "&size=" + product.getSize();
                forward += "&price=" + product.getPrice();
                forward += "&quantity=" + product.getQuantity();
                forward += "&sale=" + product.getSalePrice();
//                forward += "&link1=" + product.getImage1();
//                forward += "&link2=" + product.getImage2();
                forward += "&new=" + product.getIsNew();
                forward += "&description=" + product.getDescription();

                request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
                request.getRequestDispatcher(forward).forward(request, response);
            }

            if (action.equals("add")) {
                boolean isErr = false;
                String name = request.getParameter("productname");
                String type = request.getParameter("type");
                List<String> sizes = new ArrayList<>(Arrays.asList(request.getParameterValues("sizeProduct")));
                List<String> colors = new ArrayList<>(Arrays.asList(request.getParameterValues("colorProduct")));
                String price = request.getParameter("price");
                String cost = request.getParameter("cost");
                String quantity = request.getParameter("quantity");
                String sale = request.getParameter("sale");
                sale = sale.equals("") ? "0" : sale;
                String newProduct = request.getParameter("newProduct");
                String description = request.getParameter("description");

                Random rd = new Random();
                String random = System.currentTimeMillis() + rd.nextInt(100) + "";
                String idPro = "sp" + random.substring(random.length() - 8);
                Product p = new Product();
                p.setProductID(idPro);

                ProductCategory category = ProductCategoryService.selectByDescrip(type);
                p.setCategory(category.getId_Category() + "");

                java.sql.Date todaysDate = new java.sql.Date(new java.util.Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(todaysDate);
                Timestamp importDate = new Timestamp(calendar.getTimeInMillis());

                //Insert Product Details Quantity
                for (String s : sizes) {
                    ProductSize size = ProductSizeService.selectByDescrip(s);
                    for (String c : colors) {
                        ProductColor color = ProductColorService.selectByDescrip(c);
                        String quantityDetail = request.getParameter(s + " " + c + "");
                        ProductImportedService.insert(p.getProductID(), size.getId_Size(), category.getId_Category(), color.getId_Color(), Integer.parseInt(quantityDetail), importDate);
                    }
                }


                if (ProductService.existProductName(name)) {
                    request.setAttribute("err", "Tên sản phẩm đã tồn tại");
                    request.setAttribute("type", type);
                    request.setAttribute("sizeSelect", sizes);
                    request.setAttribute("colorSelect", colors);
                    request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
                    isErr = true;
                }
                if (!isErr) {
                    p.setProductName(name);
                    p.setDescription(description);
                    p.setPrice(Integer.parseInt(price));
                    p.setCost(Integer.parseInt(cost));
                    p.setQuantity(Integer.parseInt(quantity));
                    p.setSalePrice(Integer.parseInt(sale));
                    p.setIsNew(Integer.parseInt(newProduct));


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
                        p.setThumb(uploadPath + File.separator + thumbnailFileName);
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
                                ImageProduct i = new ImageProduct(uploadPath + File.separator + imageFileName);
                                images.add(i);
                            }
                        }
                    }

                    p.setImage(images);

                    //insert image and thumb
                    ProductImageService.insertThumbProduct(p);
                    ProductImageService.insertImageProduct(p);

                    //insertquantity

                    Quantity q = new Quantity(p, importDate);
                    QuantityService.insert(q);

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
//        ArrayList<String> type = (ArrayList<String>) Arrays.asList(request.getParameterValues("type"));
                ArrayList<String> size = (ArrayList<String>) Arrays.asList(request.getParameterValues("sizeProduct"));
                ArrayList<String> color = (ArrayList<String>) Arrays.asList(request.getParameterValues("colorProduct"));
//        String size = request.getParameter("sizeProduct");
//        String color = request.getParameter("colorProduct");
                String price = request.getParameter("price");
                String quantity = request.getParameter("quantity");
                String sale = request.getParameter("sale");
//        String link1 = request.getParameter("thumb");
//        String link2 = request.getParameter("imageP");
                String newProduct = request.getParameter("newProduct");
                String description = request.getParameter("description");
                if (name.equals("") || type.equals("") || size.equals("") || price.equals("") || quantity.equals("") || sale.equals("") || quantity.equals("") || newProduct.equals(null) || description.equals("")) {
                    request.setAttribute("err", "Vui lòng nhập dữ liệu trong các mục có đánh dấu *");
                    request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
                    isErr = true;
                } else if (ProductService.existProductName(name)) {
                    request.setAttribute("err", "Tên sản phẩm đã tồn tại");
                    request.getRequestDispatcher("admin/AddProduct.jsp").forward(request, response);
                    isErr = true;
                }
                if (!isErr) {
//                    Product p = new Product(id, name, description, size, type, Integer.parseInt(price), Integer.parseInt(sale), link1, link2, Integer.parseInt(quantity), Integer.parseInt(newProduct), 1, 1);
//                    ProductService.updateProduct(id, p);
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

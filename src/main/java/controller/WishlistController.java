package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Product;
import model.User;
import model.Wishlist;
import service.ProductService;
import service.WishlistService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "WishlistController", value = "/danh-sach-quan-tam")
public class WishlistController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("update")) {
            update(request, response);
        } else if (action.equals("check")) {
            check(request, response);
        } else if (action.equals("header")) {
            header(request, response);
        }else if (action.equals("get")) {
            getWishList(request, response);
        }
    }

    private void getWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ArrayList<Product> wishList = WishlistService.selectPByUserId(user.getId_User());
        int page,show=12;
        int size = wishList.size();
        int numberPage = size%show==0?size/show :(size/show+1);
        String currentPage=request.getParameter("page");
        if(currentPage==null){
            page=1;
        }else{
            page=Integer.parseInt(currentPage);
        }
        int start,end;
        start= (page-1)*show;
        end=Math.min(page*show, wishList.size());
        List<Product> listP= ProductService.getByPage(wishList,start,end);
        request.setAttribute("listP",listP);
        request.setAttribute("page",page);
        request.setAttribute("numberPage",numberPage);
        request.getRequestDispatcher("wishlist.jsp").forward(request,response);
    }

    private void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        WishlistService wishListDAO = new WishlistService();

        ArrayList<String> list = wishListDAO.selectByUserId(user.getId_User());


        session.setAttribute("wishNum", list.size());

        // Tạo đối tượng JSON từ danh sách
        Gson gson = new Gson();
        String json = gson.toJson(list);

        // Gửi JSON về phía client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private void header(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ArrayList<Product> wishList = WishlistService.selectPByUserId(user.getId_User());

        // Giới hạn số lượng sản phẩm cần lấy ra
        int limit = 3;
        int count = 0;

        // Tạo đối tượng JSON để lưu trữ kết quả kiểm tra
        JsonObject wishlistData = new JsonObject();

        // Lặp qua danh sách wishList và thêm sản phẩm vào đối tượng JSON
        for (Product p : wishList) {
            if (count >= limit) {
                break;
            }

            // Tạo một đối tượng JSON để lưu trữ thông tin sản phẩm
            JsonObject productData = new JsonObject();
            productData.addProperty("id", p.getProductID());
            productData.addProperty("name", p.getProductName());
            productData.addProperty("thumb", p.getThumb());

            // Tạo một đối tượng NumberFormat
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(0);
            formatter.setMaximumFractionDigits(0);

            // Định dạng giá tiền
            String formattedPrice = formatter.format(p.getPrice());
            productData.addProperty("price", formattedPrice);

            // Thêm sản phẩm vào đối tượng wishlistData
            wishlistData.add(p.getProductID(), productData);

            count++;
        }
        // Lưu số lượng sản phẩm trong wishlist vào đối tượng JSON
        wishlistData.addProperty("count", wishList.size());

        // Chuyển đối tượng wishlistData thành chuỗi JSON
        String json = wishlistData.toString();

        // Gửi JSON về phía client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String idS = request.getParameter("id_product");

        ArrayList<String> list;
        WishlistService wishListDAO = new WishlistService();

        String messageResponse = "";

        boolean isExist = false;
        if (user == null) {
            messageResponse = "error";
            request.setAttribute("messageResponse", messageResponse);
            request.setAttribute("error", "Vui lòng đăng nhập để thực hiện chức năng này");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        } else {
            list = wishListDAO.selectByUserId(user.getId_User());
            if (list.size() > 0) {
                for (String p : list) {
                    if (p.equals(idS)) {
                        wishListDAO.delete(idS);
                        isExist = true;
                        break;
                    }
                }
            }
            if (!isExist) {
                Wishlist wishList = new Wishlist();
                wishList.setId_user(user.getId_User());
                wishList.setId_product(idS);
                wishListDAO.insert(wishList);
            }
        }

        session.setAttribute("wishNum", list.size());

        // Tạo đối tượng JSON để trả về kết quả
        Gson gson = new Gson();
        String json = gson.toJson(isExist);

        // Gửi JSON về phía client
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
//        System.out.println(json);
        out.flush();
    }
}

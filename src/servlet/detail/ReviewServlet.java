package servlet.detail;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Account;
import beans.Review;
import dao.BookDAO;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String currentURL = request.getHeader("referer"); //lấy trang hiện tại
		
		int userId = -1;
		String username = "";
		int bookId = -1;
		String content = "";
		int rating = 1;
		Account user = (Account) session.getAttribute("loginedUser"); //lấy người dùng từ session
		
		if (user != null) { //nếu có ng dùng
			
			userId = user.getId(); 
			username = user.getUsername();
			content = request.getParameter("content");
			try {
				bookId = Integer.parseInt(request.getParameter("pid"));
			} catch (Exception e) {
				
			}
			try {
				rating = Integer.parseInt(request.getParameter("rating"));
			} catch (Exception e) {
				
			}
			
			if (bookId == -1) {
				
				response.sendRedirect("404.jsp");
				return;
			}
			
			if (content != "") {
				
				Review temp = new Review(-1, userId, username, bookId, content, rating); //tạo review
				BookDAO bookDAO = new BookDAO();
				bookDAO.review(temp);
				
				response.sendRedirect(currentURL);
			} else {
				
				response.sendRedirect(currentURL);
			}
		} else {
			
			response.sendRedirect(currentURL);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

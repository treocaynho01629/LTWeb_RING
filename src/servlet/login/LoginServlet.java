package servlet.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Account;
import dao.AccountDAO;
import utils.AppUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setAttribute("redirectId", request.getParameter("redirectId"));
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//Tạo liên kết DAO
		AccountDAO accountDAO = new AccountDAO();
		
		//Lấy thông tin nhập từ view
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		
		Account user = accountDAO.login(username, password); //Lấy người dùng
		
		if (user == null) { //Nếu không hợp lệ
			
			request.setAttribute("loginMessage", "Sai tài khoản hoặc mật khẩu");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		} else {
			
			//Lưu vào session
			HttpSession session = request.getSession();
			session.setAttribute("loginedUser", user);
			
			//Chuyển trang vừa ở
			int redirectId = -1;
			
			try {
				redirectId = Integer.parseInt(request.getParameter("redirectId"));
			} catch (Exception e) {
			}
			
			String requestUri = AppUtils.getRedirectAfterLoginUrl(request.getSession(), redirectId);
			
			if (requestUri != null) {
				
				response.sendRedirect(requestUri);
				return;
			} else {
				
				//Chuyển trang admin hoặc home mặc định
				if (user.getIsAdmin() == 1) {
					
					response.sendRedirect("admin");
				} else {
					
					response.sendRedirect("home");
				}
			}
		}
	}

}

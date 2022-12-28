package filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Account;
import request.UserRoleRequestWrapper;
import utils.AppUtils;
import utils.SecurityUtils;

@WebFilter("/SecurityFilter")
public class SecurityFilter implements Filter {

    public SecurityFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();

		String servletPath = httpRequest.getServletPath();

		//Lấy người dùng trên session
		Account user = (Account) session.getAttribute("loginedUser");

		if (servletPath.contains("/login")) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		HttpServletRequest wrapRequest = httpRequest;

		if (user != null) {
			
			//Lấy thông tin ng dùng
			String userName = user.getUsername();
			List<String> roles = user.getRoles();

			// Gói request cũ bởi một Request mới với các thông tin userName và Roles.
			wrapRequest = new UserRoleRequestWrapper(userName, roles, httpRequest);
		}

		// Trang bắt buộc đăng nhập?
		if (SecurityUtils.isSecurityPage(httpRequest)) {

			// Chưa đăng nhập
			if (user == null) {

				String requestUri = httpRequest.getRequestURI();

				// Lưu trữ trang hiện tại để redirect đến sau khi đăng nhập thành công.
				int redirectId = AppUtils.storeRedirectAfterLoginUrl(httpRequest.getSession(), requestUri);

				httpResponse.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
				return;
			}

			// Kiểm tra vai trò hợp lệ
			boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
			if (!hasPermission) {

				RequestDispatcher dispatcher
						= httpRequest.getServletContext().getRequestDispatcher("/security.jsp");

				dispatcher.forward(httpRequest, httpResponse);
				return;
			}
		}

		chain.doFilter(wrapRequest, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

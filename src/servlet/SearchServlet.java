package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Book;
import beans.Category;
import beans.Publisher;
import dao.BookDAO;
import dao.CategoryDAO;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		BookDAO bookDAO = new BookDAO();
		CategoryDAO cateDAO = new CategoryDAO();
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String searchString = request.getParameter("searchBar"); //lấy string trong searchbar
		String selectedPage = request.getParameter("selectedPage");
		int page = 1;
		int total = 15;
		
		if (selectedPage != null) {
			
			page = Integer.parseInt(selectedPage); //trang hiện đang chọn
		}
		
		//phân trang
		int count = bookDAO.getTotalBookFromSearch(searchString);
		int endPage = count / total;
		
		if (count % total != 0) {
			
			endPage++;
		}
		
		List<Book> listBook = bookDAO.getBookPagingFromSearch(searchString, page, total);
		List<Category> listCategory = cateDAO.getAllCategory();
		List<Publisher> listPublisher = cateDAO.getAllPublisher();
		
		request.setAttribute("currentPage", page);
		request.setAttribute("endPage", endPage);
		request.setAttribute("listBook", listBook);
		request.setAttribute("listCategory", listCategory);
		request.setAttribute("listPublisher", listPublisher);
		request.setAttribute("searchBarString", searchString);
		request.getRequestDispatcher("/shop.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

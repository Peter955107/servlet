package cc.openhome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String deleteAccount = request.getParameter("delete");
		if(deleteAccount != null)
		{
			try {
				Connection conn=UserDAo.getconn();
				if(conn != null && !conn.isClosed())
				{
					System.out.println("資料庫連線測試成功！");
	                
	                Statement stmt = conn.createStatement();
	                stmt.executeUpdate("DELETE FROM userdata where account='"+deleteAccount+"'");
	                conn.close();
	                response.sendRedirect("user");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
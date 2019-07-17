package cc.openhome;

import cc.openhome.UserDAo;
import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
        String account = request.getParameter("account");
        String passwd = request.getParameter("passwd");
        try {
        	Connection conn=UserDAo.getconn();
		 
			if(conn != null && !conn.isClosed()) {
                System.out.println("資料庫連線測試成功！");
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM userdata where account='"+account+"' and passwd='"+passwd+"'");
                
                if(rs.next()){
                	request.getSession().setAttribute("name", rs.getString("name"));
                	request.getSession().setAttribute("gender", rs.getString("gender"));
                	conn.close();
                	response.sendRedirect("user");
                }else{
                	conn.close();
					PrintWriter out = response.getWriter();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('User or password incorrect');");
					out.println("location='login.html';");
					out.println("</script>");
                }
            }
		}
        catch(SQLException e) { 
            e.printStackTrace(); 
        } 
    } 
}
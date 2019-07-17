package cc.openhome;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/user")
public class User extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                      throws ServletException, IOException {
        if(request.authenticate(response)) {
        	response.setContentType("text/html;charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	if(request.getSession().getAttribute("gender").equals("男"))
        		out.println("Welcome, Mr."+request.getSession().getAttribute("name"));
        	else
        		out.println("Welcome, Miss"+request.getSession().getAttribute("name"));
        	try {
				Connection conn=UserDAo.getconn();
				
				if(conn != null && !conn.isClosed()) {
	                System.out.println("資料庫連線測試成功！");
	                
	                Statement stmt = conn.createStatement();
	                ResultSet rs = stmt.executeQuery("SELECT * FROM userdata");
	                
	                out.println("<table border='1'>");
	                out.println("<thead>");
	                out.println("<tr>");
	                out.println("<th>姓名</th>");
	                out.println("<th>帳號</th>");
	                out.println("<th>E-mail</th>");
	                out.println("<th>性別</th>");
	                out.println("<th>年齡</th>");
	                out.println("</tr>");
	                out.println("</thead>");
	                String account ;
	                String name;
	                String email;
	                String gender;
	                String age;
	                out.println("<tbody>");
	                while(rs.next()) {
	                	name = rs.getString("name");
	                	account = rs.getString("account");
	                	email = rs.getString("email");
	                	gender = rs.getString("gender");
	                	age = rs.getString("age");
	                	
	                	out.println("<tr>");
	                	out.println("<th>"+name+"</th>");
		                out.println("<th>"+account+"</th>");
		                out.println("<th>"+email+"</th>");
		                out.println("<th>"+gender+"</th>");
		                out.println("<th>"+age+"</th>");
		                out.println("<th><a href='UserDetail?accountDetail="+account+"'>編輯</a></th>");
		                out.println("</tr>");
	                }
	                out.println("</tbody>");
	                out.println("</table>");
	                conn.close();
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            out.println("<a href='logout'>登出</a>");
        } 
    } 
}
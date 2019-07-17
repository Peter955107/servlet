package cc.openhome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserDetail")
public class UserDetail extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Connection conn=UserDAo.getconn();
			
			if(conn != null && !conn.isClosed()) {
                System.out.println("資料庫連線測試成功！");
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM userdata where account='"+request.getParameter("accountDetail")+"'");
                
                if(rs.next())
                {
                	out.println("<table border='1'>");
                	
                    out.println("<tr>");
                    out.println("<th>姓名</th>");
                    out.println("<th><input type='text' value='"+rs.getString("name")+"' readonly=\"readonly\"></th>");
                    out.println("</tr>");
                    
                    out.println("<form action=\"UserDetail\" method=\"post\">");
                    
                    out.println("<tr>");
                    out.println("<th>帳號</th>");
                    out.println("<th><input type='text' name='editAccount' value='"+rs.getString("account")+"' readonly=\"readonly\"></th>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<th>密碼</th>");
                    out.println("<th><input type='password' name='editPasswd' value='"+rs.getString("passwd")+"' required></th>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<th>確認密碼</th>");
                    out.println("<th><input type='password' name='confirmPasswd' value='"+rs.getString("passwd")+"' required></th>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<th>E-mail</th>");
                    out.println("<th><input type='email' name='editEmail' value='"+rs.getString("email")+"' required></th>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<th>E-mail</th>");
                    if(rs.getString("gender").equals("男"))
                    	out.println("<th><input type='radio' name='editGender' value='男' checked='true'>男 <input type='radio' name='editGender' value='女'>女 </th>");
                    else
                    	out.println("<th><input type='radio' name='editGender' value='男'>男 <input type='radio' name='editGender' value='女' checked='true'>女 </th>");
                    out.println("</tr>");
                    
                    out.println("<tr>");
                    out.println("<th>age</th>");
                    out.println("<th><input type='text' name='editAge' value='"+rs.getString("age")+"' required></th>");
                    out.println("</tr>");
                    
                    out.println("</table>");
                    
                    out.println("<br>");
                    
                    out.println("<input type='submit' value='更新'>");
                    out.println("<input type='button' value='刪除'  onclick=\"location.href='DeleteUser?delete="+rs.getString("account")+"' \">");
                    out.println("<input type='reset' value='重設'>");
                    
                    out.println("</form>");
                }
                conn.close();
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.println("<a href='logout'>登出</a>");
    }
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8"); 
		String editAccount = request.getParameter("editAccount");
        String editPasswd = request.getParameter("editPasswd");
        String confirmPasswd = request.getParameter("confirmPasswd");
        String editEmail = request.getParameter("editEmail");
        String editGender = request.getParameter("editGender");
        String editAge = request.getParameter("editAge");
        PrintWriter out = response.getWriter();
        if(!editPasswd.equals(confirmPasswd)){
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Confirm PassWord incorrect');");
			out.println("location='UserDetail';");
			out.println("</script>");
        }
        else{
        	try {
            	Connection conn=UserDAo.getconn();
    		 
    			if(conn != null && !conn.isClosed()) {
                    System.out.println("資料庫連線測試成功！");
                    
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate("UPDATE userdata SET passwd='"+editPasswd+"',email='"+editEmail+"',gender='"+editGender+"',age='"+editAge+"' where account='"+editAccount+"'");
                }
    			conn.close();
    			out.println("<script type=\"text/javascript\">");
    			out.println("alert('correct');");
    			out.println("location='user';");
    			out.println("</script>");
    		}
            catch(SQLException e) { 
                e.printStackTrace(); 
            }
        }
        
    } 
}

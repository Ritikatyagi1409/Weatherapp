package Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		try {
			String user=request.getParameter("username");
			String password=request.getParameter("password");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","systemroot");
			ps=con.prepareStatement("select * from weather_users  where user_name=? OR email=?");
			ps.setString(1,user);
			ps.setString(2,user);
			rs=ps.executeQuery();
			if(rs.next()) {
				String check=rs.getString(6);
				if(check.equals(password)) {
					response.sendRedirect("http://localhost:8085/wapp/index.html");
				}
				else {
					out.println("Wrong Password");
				}
			}
			else {
				out.println("No user name or email found");
			}
		}
		catch(Exception e) {
			out.println(e);
		}
	}

}

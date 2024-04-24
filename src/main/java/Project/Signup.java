package Project;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Signup extends HttpServlet 
{
	public static String validate(String pass1,String pass2) {
		if(pass1.length()<8) {
			return "Password Length must be Greater than or equals to 8 characters";
		}
		else if(! (pass1.equals(pass2))) {
			return "Password and Confirm Password Should be same";
		}
		else {
			return "okay";
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		try {
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			String userName = request.getParameter("username");
			String pass1 = request.getParameter("password1");
			String pass2 = request.getParameter("password2");
			String error=validate(pass1, pass2);
			if(error.equals("okay")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "systemroot");
				ps = con.prepareStatement("Insert Into weather_users Values(Id_INCRIMENT.NEXTVAL,?,?,?,?,?,?,SYSDATE)", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.setString(4, userName);
				ps.setString(5, pass1);
				ps.setString(6, gender);
				int rawEffected = ps.executeUpdate();
				if (rawEffected == 1) 
				{
					response.sendRedirect("login.html");
				}
			}
			else {
				out.print(error);
			}
		}
		catch (Exception e) {
			out.print("Username or Email Exits Please Use Another");
		}
	}

}

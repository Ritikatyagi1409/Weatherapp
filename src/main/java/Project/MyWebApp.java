package Project;

import jakarta.servlet.ServletException;
import java.net.*;
import java.util.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MyWebApp extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// setting the Api.
		String apiKey = "05d76811a6bfb5273d761207945d060e";

		String city = request.getParameter("city");

		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

		try {
			// Api Integration.
			URL url = new URL(apiUrl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			InputStream inputStream = con.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			Scanner sc = new Scanner(reader);

			StringBuilder data = new StringBuilder();

			while (sc.hasNext()) {
				data.append(sc.nextLine());
			}
			sc.close();


			Gson gs = new Gson();
			JsonObject js = gs.fromJson(data.toString(), JsonObject.class);
			
			//Date & Time
            long dateTimestamp = js.get("dt").getAsLong() * 1000;
            String date = new Date(dateTimestamp).toString();
            
            //Temperature
            double temperatureKelvin = js.getAsJsonObject("main").get("temp").getAsDouble();
            int temperatureCelsius = (int) (temperatureKelvin - 273.15);
           
            //Humidity
            int humidity = js.getAsJsonObject("main").get("humidity").getAsInt();
            
            //Wind Speed
            double windSpeed = js.getAsJsonObject("wind").get("speed").getAsDouble();
            
            //Weather Condition
            String weatherCondition = js.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
            
            // Set the data as request attributes (for sending to the jsp page)
            request.setAttribute("date", date);
            request.setAttribute("city", city);
            request.setAttribute("temperature", temperatureCelsius);
            request.setAttribute("weatherCondition", weatherCondition); 
            request.setAttribute("humidity", humidity);    
            request.setAttribute("windSpeed", windSpeed);
            request.setAttribute("weatherData", data.toString());
            
            con.disconnect();
            
            request.getRequestDispatcher("MyWebApp.jsp").forward(request, response);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}


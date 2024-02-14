package ut2_inet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLs {

	public static void main(String[] args) throws IOException {
		URL url = new URL("http://ansat.es:80/index.htm");
		InputStreamReader entrada = new InputStreamReader(url.openStream());
		BufferedReader flujoE = new BufferedReader(entrada);
		
		String linea;
		while ((linea = flujoE.readLine()) != null) {
			System.out.println(linea);
		}
		//flujoE.close();
		
		URLConnection UrlConn = url.openConnection();
		InputStreamReader entrada2 = new InputStreamReader(UrlConn.getInputStream());
		BufferedReader flujoE2 = new BufferedReader(entrada);
		String linea2;
		while ((linea2 = flujoE.readLine()) != null) {
			System.out.println(linea2);
		}
		flujoE.close();
		flujoE2.close();
	}
}

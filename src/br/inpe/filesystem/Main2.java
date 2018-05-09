package br.inpe.filesystem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import org.json.JSONObject;


public class Main2 {
	
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String url = "http://localhost:8080/springRestSecurity/login";
        URL obj = new URL(url);
        java.net.HttpURLConnection con =  (java.net.HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        JSONObject cred = new JSONObject();
        cred.put("username", "admin@hotmail.com");
        cred.put("password", "admin");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(cred.toString());
        wr.flush();
        wr.close();
        System.out.println("a");
        int code = con.getResponseCode();

        if (code == 200) {
        	System.out.println("a");
            String token = con.getHeaderField("Token");
           
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
		System.out.println(response.toString());
        }
		
		
		
		
		
		
		
		
//		try{
//			Controller controller = new Controller(pathPrincipal, pathDB);
//			ArrayList<String> imagesList = controller.getImages();
//			controller.sendToBD(imagesList);
//		}
//		catch(IOException io){
//			//log
//		}
		
//		ApplicationContext ctx =
//	             new AnnotationConfigApplicationContext(SpringMongoConfig1.class);
//		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
//		ImagesFits image = new ImagesFits();
//		Document doc = new Document();
//		
//		doc.append("DAY", 1).append("MONTH", 3).append("SECOND", 3);
//		
//		image.setDocument(doc);
//		
//		mongoOperation.save(image);

	}
	}

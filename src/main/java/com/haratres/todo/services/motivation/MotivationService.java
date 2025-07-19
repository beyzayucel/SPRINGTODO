package com.haratres.todo.services.motivation;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.haratres.todo.dto.MotivationDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class MotivationService {

    public MotivationDto getMotivation() {
        try {

            URL url = new URL("https://zenquotes.io/api/random");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode=con.getResponseCode();
            if(responseCode!=200){
                throw new RuntimeException("Http code: "+responseCode);
            }

            String wordLine="";
            Scanner scanner=new Scanner(url.openStream());

            while(scanner.hasNext()){
                wordLine+= scanner.nextLine();
            }

            JSONParser jsonParser=new JSONParser();
            JSONArray jsonArray=(JSONArray) jsonParser.parse(wordLine);
            JSONObject jsonObject=(JSONObject) jsonArray.get(0);

            MotivationDto motivationDto=new MotivationDto();
            motivationDto.setQuote((String) jsonObject.get("q"));
            motivationDto.setAuth((String) jsonObject.get("a"));

            return motivationDto;

    } catch(Exception e) {
        e.printStackTrace();
    }
        return null;
}

}

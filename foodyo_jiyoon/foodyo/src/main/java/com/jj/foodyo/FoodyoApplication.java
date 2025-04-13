package com.jj.foodyo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodyoApplication {

	public static void main(String[] args) {
        System.out.println("실제 자바 실행 경로: " + System.getProperty("user.dir"));
		try {
            ProcessBuilder pb = new ProcessBuilder("python", "test.py", "관악구 돈까스");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            System.out.println(output.toString());

            process.waitFor();

            JSONArray jsonArray = new JSONArray(output.toString());
			

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject store = jsonArray.getJSONObject(i);
				String name = store.getString("name");
				String degree = store.getString("degree");
                String reviewCount = store.getString("reviewCount");
				String address = store.getString("address");
				String tel = store.getString("tel");

                if (degree.equals("") || degree.equals("0.0")) {
                    degree = "리뷰없음";
                    reviewCount = "";
                }
			
                String formatted = String.format(
                    "%s\n- 평점: %s\n- 리뷰수: %s\n- 주소: %s\n- 전화번호: %s\n",
                    i + 1 + ". " + name, degree, reviewCount, address, tel
                );
                System.out.println(formatted);

			}

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}

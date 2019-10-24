package com.daum.jeremy6019.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonMain {

	public static void main(String[] args) {
		// 웹의 문자열 가져오기
				String json = "";
				try {
					// 다운로드 받을 주소 생성
					String addr = 
							"https://dapi.kakao.com/v3/search/book?target=title&query="
							+ URLEncoder.encode("삼국지", "utf8");
					URL url = new URL(addr);
					// 연결 객체 생성
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					// 옵션 설정
					// 최대 30초동안 연결을 시도
					con.setConnectTimeout(30000);
					// 헤더 설정
					con.addRequestProperty(
							"Authorization", "KakaoAK 9662a7b4d6113ff616ca34fa514f7e65");
					// 웹에서 문자열을 읽어올 스트림 생성
					BufferedReader br = 
							new BufferedReader(
									new InputStreamReader(
											con.getInputStream()));
					// 문자열을 일시적으로 저장할 객체 생성
					StringBuilder sb = new StringBuilder();
					while(true) {
						String line = br.readLine();
						if(line == null) {
							break;
						}
						sb.append(line + "\n");
					}
					// 문자열 변수에 대입
					json = sb.toString();
					System.out.printf("%s\n", json);
					
				}catch(Exception e) {
					System.out.printf("%s\n", e.getMessage());
					e.printStackTrace();
				}
				// 파싱
				try {
					// 다운로드 받은 문자열을 JSON 객체로 변환
					JSONObject data = new JSONObject(json);
					// documents 라는 키의 값을 JSONArray로 가져오기
					JSONArray documents = data.getJSONArray("documents");
					// 배열을 순회
					for(int i=0; i<documents.length(); i=i+1) {
						JSONObject item = documents.getJSONObject(i);
						JSONArray authors = item.getJSONArray("authors");
						System.out.printf("%s\n", authors);
					}			
				}catch(Exception e) {
					System.out.printf("JSON 파싱 예외 : %s\n", e.getMessage());
					e.printStackTrace();
				}


	}

}

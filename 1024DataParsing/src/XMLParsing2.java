import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class XMLParsing2 {

	public static void main(String[] args) {
		//기상청 날씨데이터 가져오기 
		 //다운로드 받을 문자열을 저장할 변수 
		String xml = ""; 
		try {
			//주소 생성 
			String addr =
					"http://www.weather.go.kr/weather/forecast/mid-term-xml.jsp?stnId=109";
			//URL로 생성하기  
			URL url = new URL(addr);
			//연결하기 
			HttpURLConnection con = 
					(HttpURLConnection)
					url.openConnection();
			con.setConnectTimeout(20000);
			//데이터 읽어서 저장  
			BufferedReader br =
					new BufferedReader(
							new InputStreamReader(
									con.getInputStream()));
			//한 줄 씩 읽어서 StringBuilder에 저장 
			StringBuilder sb = new StringBuilder();
			while(true) {
				String line = br.readLine();
				if(line ==null) {
					break;
				}
				sb.append(line + "\n");	
			}
			//데이터 옮기기 
			xml = sb.toString();
//			System.out.printf("%s\n", xml);
			//연결 끊기 
			br.close();
			con.disconnect();
			
		} catch(Exception e) {
			System.out.printf("다운로드 예외%s\n",
					e.getMessage());
		}

	    try {
	    	
	        DocumentBuilderFactory factory = 
	        		 DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = 
	        		factory.newDocumentBuilder();
	        //파싱을 수행할 데이터 만들기 
	        InputStream is =
	        		new ByteArrayInputStream(
	        				xml.getBytes());
	        //메모리에 전부 펼치기 
	        Document document = 
	        		documentBuilder.parse(is);
	        //루트찾기 
	        Element element = 
	        		document.getDocumentElement();
	        //city태그의 내용을 찾아서 
	        //List에 저장 
	        List<String>cities = 
	        		new ArrayList<>();
	        //city태그 전부 가져오기 
	        NodeList cityList = 
	        		element.getElementsByTagName("city");
	        for(int i=0; i<cityList.getLength(); i+=1) {
	        	Node item = cityList.item(i);
	        	Node city = item.getFirstChild();
	        	cities.add(city.getNodeValue());
	        }
//	        System.out.printf("%s\n", cities);
	        
	        //날짜를 가져와서 전부 저장하기 
	        List<String> dateList =
	        		new ArrayList<>(); 
	        //tmEf 태그의 값 가져오기 
	        NodeList tmef = 
	        		element.getElementsByTagName("tmEf");
	        for(int i=0; i<tmef.getLength(); i+=1) {
	        	Node item = tmef.item(i);
	        	Node t = item.getFirstChild();
	        	dateList.add(t.getNodeValue());
	        }  
//	        System.out.printf("%s\n", dateList);
	        
	        //wf 태그의 값 저장하기  
	        List<String> weatherList =
	        		new ArrayList<>(); 
	        //wf 태그의 값 가져오기 
	        NodeList wfList = 
	        		element.getElementsByTagName("wf");
	        for(int i=0; i<wfList.getLength(); i+=1) {
	        	//첫번째 데이터를 사용하지 않기 위해서 
	        	//첫번째 데이터인 경우는 다음으로 넘기기 
	        	if(i==0) {
	        		continue;
	        	}
	        	Node item = wfList.item(i);
	        	Node wf = item.getFirstChild();
	        	weatherList.add(wf.getNodeValue());
	        }  
//	        System.out.printf("%s\n", weatherList);
	       
	        int i = 0;
	        for(String city : cities) {
	        	System.out.printf("%s\n", city);
	        	for(int j=0; j<6; j+=1) {
	        		System.out.printf(
	        				"\t%s%s\n", 
	        				dateList.get(i*6+j), 
	        				weatherList.get(i*6+j));
	        	}
	        	i += 1; 
	        }
	        
	        
	    }catch(Exception e) {
			System.out.printf("XML파싱 예외%s\n",
					e.getMessage());
		}
		
	}

}

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParsing {

	public static void main(String[] args) {
		
        //다운로드 받을 문자열을 저장할 변수 
		String xml = ""; 
		try {
			//다운로드 받을 주소 생성 
			//한글이 포함된 경우는 한들은 인코딩해야 합니다. 
			//URLEncoder.encode(한글, "utf8")
			String addr =
					"http://www.hani.co.kr/rss/";
			//URL로 생성하기  
			URL url = new URL(addr);
			//연결하기 
			HttpURLConnection con = 
					(HttpURLConnection)
					url.openConnection();
			con.setConnectTimeout(30000);
			//데이터 읽기 
			BufferedReader br =
					new BufferedReader(
							new InputStreamReader(
									con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while(true) {
				String line = br.readLine();
				if(line ==null) {
					break;
				}
				sb.append(line+ "\n");			
			}
			xml =sb.toString();
			br.close();
			con.disconnect();
	//		System.out.printf("%s\n", xml);
			
		} catch(Exception e) {
			System.out.printf("다운로드 예외%s\n",
					e.getMessage());
		}
		
		try {
			
		   DocumentBuilderFactory factory  = 
				   DocumentBuilderFactory.newInstance();
		   DocumentBuilder documentBuilder = 
				   factory.newDocumentBuilder();
		   //문자열을 스트림으로 변환하기 
		   InputStream is = new ByteArrayInputStream(xml.getBytes());
		   //메모리에 펼치기 
		   Document document = 
				   documentBuilder.parse(is); 
		   //루트 찾아오기 
		   Element root =
				   document.getDocumentElement();
		   //title태그 찾아오기 
		   NodeList descriptions =
				   root.getElementsByTagName("title");
		   for(int i=0; i<descriptions.getLength(); i+=1) {
			   if(i==0)continue;
			   Node node = descriptions.item(i);
			   Node title = node.getFirstChild();
			   System.out.printf("%s\n",
			       title.getNodeValue());
		   }
		   
			
		} catch(Exception e) {
			System.out.printf("XML파싱 예외%s\n",
					e.getMessage());
		}
		

	}

}

package com.daum.jeremy6019.externallibrary;

public class MySQLLoad {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.printf("드라이버 클래스 로드 성공");
		} catch(Exception e) {
			System.out.printf("%s\n",
					e.getMessage());
		}

	}

}

package jdbc.day01;

import java.sql.*;
import java.util.Scanner;

public class DQL_select_PreparedStatement_02 {

	public static void main(String[] args) {
		
		
		Connection conn = null;
		// Connection conn 은 데이터베이스 서버와 연결을 맺어주는 자바의 객체이다.
		
		PreparedStatement pstmt = null;
		// PreparedStatement pstmt 은 
		// Connection conn(연결한 DB 서버)에 전송할 SQL문(편지)을 전송(전달)을 해주는 객체(우편배달부)이다.
		
		
		
		ResultSet rs = null; 
		// ResultSet rs 은 select 되어진 결과물이 저장되어지는 곳. 
		
		
		
		Scanner sc = new Scanner(System.in);
		
		
		try {
			
			// >>> 1. 오라클 드라이버 로딩 <<< // 
			
			/*
		        === OracleDriver(오라클 드라이버)의 역할 ===
		        1). OracleDriver 를 메모리에 로딩시켜준다.
		        2). OracleDriver 객체를 생성해준다.
		        3). OracleDriver 객체를 DriverManager에 등록시켜준다.
		            --> DriverManager 는 여러 드라이버들을 Vector 에 저장하여 관리해주는 클래스이다.
			*/ 
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			// >>> 2. 어떤 오라클 서버에 연결을 할래? <<< //
			
			System.out.print("▷ 연결할 오라클 서버의 IP 주소 : ");
			String ip = sc.nextLine();
			
			//oracle_user을 하나 생성 
			conn = DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:xe", "JDBC_USER", "gclass");
			
			
			
			
			// >>> 3. SQL문(편지)을 작성한다. <<< //
			
			
			
			
			String sql = " select no, name, msg, to_char(writeday, 'yyyy-mm-dd hh24:mi:ss') AS writeday "
					+ " from tbl_memo "
					+ " order by no desc ";
			// SQL문 뒤에 ; 를 넣으면 오류이다.!!!!!!!
			// SQL 문을 작성할 때 1줄 마다 잎, 뒤로 공백을 꼭 주도록 한다.
			
			
			// >>> 4. 연결한 오라클 서버(conn)에 SQL문(편지)을 전달할 객체 PreparedStatement 객체(우편배달부) 생성하기 <<< //
			pstmt = conn.prepareStatement(sql);
			
			
			
			// >>> 5. PreparedStatement 객체(우편배달부)는 작성된 SQL문(편지)을 오라클 서버에 보내서 실행이 되도록 해야 한다. <<< //
			
			rs = pstmt.executeQuery();
			// SQL문이 DQL문(select) 이므로 ,executeQuery(); 이다
			// pstmt.executeQuery(); 을 실행하면 select 되어진 결과물을 가져오는데 그 타입은 ResultSet 으로 가져온다.
			
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("글번호\t글쓴이\t글내용\t작성일자");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			/*
            === 인터페이스 ResultSet 의 주요한 메소드 ===
            1. next()  : select 되어진 결과물에서 커서를 다음으로 옮겨주는 것             리턴타입은 boolean 
            2. first() : select 되어진 결과물에서 커서를 가장 처음으로 옮겨주는 것       리턴타입은 boolean
            3. last()  : select 되어진 결과물에서 커서를 가장 마지막으로 옮겨주는 것    리턴타입은 boolean
            
            == 커서가 위치한 행에서 컬럼의 값을 읽어들이는 메소드 ==
            getInt(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
                           파라미터 숫자는 컬럼의 위치값 
                          
            getInt(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
                           파라미터 문자는 컬럼명 또는 alias명 
                           
            getLong(숫자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
                              파라미터 숫자는 컬럼의 위치값 
                          
            getLong(문자) : 컬럼의 타입이 숫자이면서 정수로 읽어들이때
                              파라미터 문자는 컬럼명 또는 alias명                
            
            getFloat(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
                               파라미터 숫자는 컬럼의 위치값 
                          
            getFloat(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
                               파라미터 문자는 컬럼명 또는 alias명 
                               
            getDouble(숫자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
                                 파라미터 숫자는 컬럼의 위치값 
                          
            getDouble(문자) : 컬럼의 타입이 숫자이면서 실수로 읽어들이때
                                 파라미터 문자는 컬럼명 또는 alias명    
                                 
            getString(숫자) : 컬럼의 타입이 문자열로 읽어들이때
                                 파라미터 숫자는 컬럼의 위치값 
                          
            getString(문자) : 컬럼의 타입이 문자열로 읽어들이때
                                 파라미터 문자는 컬럼명 또는 alias명                                                        
         */
			
			
			StringBuilder sb = new StringBuilder();
			
			while(rs.next()) {  // 리턴 타입은 boolean 그 다음 행이 있으면 true 없으면 false
				/*
	               rs.next() 는 select 되어진 결과물에서 위치(행의 위치)를 다음으로 옮긴 후 
	               행이 존재하면 true 를 리턴해주고, 행이 없으면 false 를 리턴해주는 메소드이다.
	            */
				
				int no = rs.getInt("NO"); // "NO" 은 select 해온 컬럼명이다. (대문자로 쓰지 않아도 됨)
				// 또는
				//int no = rs.getInt(1);    // 1 은 select 해온 컬럼의 위치값으로서 1번째 컬럼을 가리키는 것이다.
						
				
				String name = rs.getNString("NAME");	// "NAME" 은 select 해온 컬럼명이다. (대문자로 쓰지 않아도 됨)
				// 또는
				//String name = rs.getNString(2);  // 2 는 select 해온 컬럼의 위치값으로서 2번째 컬럼을 가리키는 것이다.
				
				
				String msg = rs.getNString("MSG");	// "MSG" 은 select 해온 컬럼명이다. (대문자로 쓰지 않아도 됨)
				// 또는
				//String msg = rs.getNString(3);  // 3 는 select 해온 컬럼의 위치값으로서 3번째 컬럼을 가리키는 것이다.
				
				
				String WRITEDAY = rs.getNString("WRITEDAY");	// "WRITEDAY" 은 select 해온 컬럼명이다. (대문자로 쓰지 않아도 됨)
				// 또는
				//String WRITEDAY = rs.getNString(2);  // 4 는 select 해온 컬럼의 위치값으로서 4번째 컬럼을 가리키는 것이다.
				
				sb.append(no);
				sb.append("\t" + name);
				sb.append("\t" + msg);
				sb.append("\t" + WRITEDAY + "\n");
				
			} // end of while (rs.next())
			
			
			System.out.println(sb.toString());
			
			
		} 
		catch (ClassNotFoundException e) 
		{			
			System.out.println(">>> ojdbc8.jar 파일이 없습니다. <<<");
		}
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		
		// 반납하기 
		finally {
			// 6. 사용하였던 자원을 반납하기 
			// 반납의 순서는 생성순서의 역순으로 한다.
			
			try {
				if(rs != null)
				{
					rs.close();
					rs = null;
				}
				
				if(pstmt != null)
				{
					pstmt.close();   //null 이 존재하기에 null 이 아니면 정상 종료
					pstmt = null;
				}
				if(conn != null)
				{
					conn.close();   //null 이 존재하기에 null 이 아니면 정상 종료
					conn = null;
				}
				
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
		}
		
		
		
		sc.close();
		System.out.println("~~~~~~ 프로그램 종료 ~~~~~~~");

	}// end of main

}

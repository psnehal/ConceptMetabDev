package org.conceptmetab.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PostgresConnectionTest {

	 public static void main(String argv[]) throws Exception {

		Connection c;
		Statement s;

		String database = "conceptmetab";
		String username = "snehal";
		String password = "snehal";

		Class.forName("com.mysql.jdbc.Driver");
		c = DriverManager.getConnection(
				"jdbc:mysql://metabtest.med.umich.edu:3306/conceptmetab?useUnicode=yes&characterEncoding=UTF-8", username,
				password);
		
		String sql = "select * concepts where id =1";
		s = c.createStatement();
		ResultSet results = s.executeQuery(sql);
		if (results != null)
		{
			while (results.next())
			{
				System.out.println("patient_num = " + results.getInt("id"));
				//System.out.println("trial = " + results.getString("trial"));
				//System.out.println("secure_obj_token = " + results.getString("secure_obj_token"));
			}
		}
	}

}

package com.maxrumo.test;

import com.maxrumo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ToutiaoApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Test
    public void getDataSource() {
		System.out.println(dataSource.getClass().getName());
	}
	@Test
	public void getConnetction() {
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement ps = connection.prepareStatement("select * from user");
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()){
				Object obj1 = resultSet.getObject(1);
				Object obj2 = resultSet.getObject(2);
				System.out.println(obj1+" : "+ obj2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

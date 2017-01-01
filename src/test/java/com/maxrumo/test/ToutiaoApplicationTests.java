package com.maxrumo.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maxrumo.Application;
import com.maxrumo.entity.User;
import com.maxrumo.entity.UserExample;
import com.maxrumo.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
//@WebAppConfiguration
public class ToutiaoApplicationTests {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserMapper userMapper;
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
	
	@Test
	public void testSelectByMapper(){
		List<User> list = userMapper.selectByExample(new UserExample());
		System.out.println(list);
	}
}

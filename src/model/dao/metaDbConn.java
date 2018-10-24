package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import model.utils.PropertiesUtil;

public class metaDbConn {
	 public static Connection connectMetaDB(String dbConfigPath) throws Exception {

	        String jdbcURL = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8&autoReconnect=true",
	        		PropertiesUtil.GetValueByKey(dbConfigPath, "dbHost"), PropertiesUtil.GetValueByKey(dbConfigPath, "dbName"),
	        		PropertiesUtil.GetValueByKey(dbConfigPath, "userName"), PropertiesUtil.GetValueByKey(dbConfigPath, "password"));

	        Class.forName("com.mysql.jdbc.Driver");
	        Connection metaDBConn = DriverManager.getConnection(jdbcURL);
	        return metaDBConn;
	    }
	 public static Connection connectMetaDB(Properties metaDBParameters) throws Exception {

	        String jdbcURL = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8",
	                metaDBParameters.getProperty("dbHost"), metaDBParameters.getProperty("dbName"),
	                metaDBParameters.getProperty("userName"), metaDBParameters.getProperty("password"));

	        Class.forName("com.mysql.jdbc.Driver");
	        Connection metaDBConn = DriverManager.getConnection(jdbcURL);
	        return metaDBConn;
	    }
	 
}


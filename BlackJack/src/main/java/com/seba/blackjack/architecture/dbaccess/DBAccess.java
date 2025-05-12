package com.seba.blackjack.architecture.dbaccess;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.seba.blackjack.architecture.dao.DAOConstants;
import com.seba.blackjack.architecture.dao.DAOException;


public class DBAccess implements DAOConstants{
	
	private static DataSource dataSource;
	
	private static final String CONFIG_FILE = "config.properties";
	
	
	static {

		try {
			initializeDataSource();
		}catch (IOException e) {
			throw new ExceptionInInitializerError("Inizializazione datasource fallita: "+e.getMessage());
		}
		
	}
	
	private static void initializeDataSource() throws IOException {
		Properties p = new Properties();
		try (
			//Per leggere dal package
			InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(CONFIG_FILE);)  {
			if(input == null) {
				throw new IOException("File di configurazione "+CONFIG_FILE+" non trovato");
			}
			p.load(input);
		} 
		
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(p.getProperty("jdbcUrl"));
		ds.setUsername(p.getProperty("jdbcUsername"));
		ds.setPassword(p.getProperty("jdbcPassword"));
		ds.setDriverClassName(p.getProperty("jdbcDriver"));
		
		
		// Connection pool properties
		
		
		ds.setMaxTotal(Integer.parseInt(p.getProperty("maxPoolSize")));
		ds.setMinIdle(Integer.parseInt(p.getProperty("minIdle")));
		ds.setMaxIdle(Integer.parseInt(p.getProperty("maxIdle")));
		ds.setInitialSize(Integer.parseInt(p.getProperty("initialSize")));
		
		
		ds.setMaxWaitMillis(Integer.parseInt(p.getProperty("maxWaitMillis")));
		
		//Validation settings
		
		ds.setTestOnCreate(Boolean.parseBoolean(p.getProperty("testOnCreate")));
		ds.setTestOnBorrow(Boolean.parseBoolean(p.getProperty("testOnBorrow")));
		ds.setTestWhileIdle(Boolean.parseBoolean(p.getProperty("testWhileIdle")));
		ds.setTestOnReturn(Boolean.parseBoolean(p.getProperty("testOnReturn")));
		
		// Abandoned connetions
		
		ds.setRemoveAbandonedOnBorrow(Boolean.parseBoolean(p.getProperty("removeAbandonedOnBorrow")));
		ds.setRemoveAbandonedOnMaintenance(Boolean.parseBoolean(p.getProperty("removeAbandonedOnMaintenance")));
		ds.setLogAbandoned(Boolean.parseBoolean(p.getProperty("logAbandoned")));
		
		
		dataSource = ds;
		
	}
	
	
	public synchronized static Connection getConnection() throws DAOException {
		
		try {
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			return conn;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	
	public static void closeConnection(Connection conn) throws DAOException  {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

}
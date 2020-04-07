package com.proHar.perfoMeasure.main.ssmsModules;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;

public class SSMSDataMigrationCredentials {

	public static Connection getSSMSConnection() {
		// Windows Authentication
		//		String connectorUrl = "jdbc:sqlserver://DESKTOP-LHLA0PA\\SQLEXPRESS;DatabaseName=testDBs;integratedSecurity=true";
		String ClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//String IPAddr = "DESKTOP-LHLA0PA\\MSSQLSERVER01";
		String IPAddr = "DESKTOP-LHLA0PA;";
		String DBName="Performance";
		String DB_URL = "jdbc:sqlserver://" + IPAddr + "DatabaseName=" + DBName + ";integratedSecurity=true" ;
		Connection cons = dbConnect (DB_URL,ClassName);
		return cons;
	}

	private static Connection dbConnect(String db_connect_string, String className)
	{
		java.sql.Connection connection = null;
		String libpath = System.getProperty("java.library.path");
		// Get the apth for AUTHENTICATION DLLs
		libpath = "C:\\Users\\soham\\Downloads\\sqljdbc_8.2\\enu\\auth\\x64\\;" +libpath;
		Field sysPathsField;
		try {
			sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			sysPathsField.setAccessible(true);
			sysPathsField.set(null, null);
		} catch (NoSuchFieldException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(className);
		} catch (UnsatisfiedLinkError | ClassNotFoundException e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
		try{
			connection = DriverManager.getConnection(db_connect_string);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}

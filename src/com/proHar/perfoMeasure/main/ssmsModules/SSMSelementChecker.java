package com.proHar.perfoMeasure.main.ssmsModules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proHar.perfoMeasure.main.ValueParser;

public class SSMSelementChecker {

	public static void SSMScheckIfEnded(int appId, int pageID){

		String getDataFromResources = "select * from Resources where Application_ID="+ appId +" and Page_ID = "+ pageID;
		List<String> resources = ValueParser.resHolder;
		Map<Integer, String> rowmap = new HashMap<Integer, String>();
		int ID;

		// Application Name Insertion

		try(Connection connection = SSMSDataMigrationCredentials.getSSMSConnection()){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(getDataFromResources);

			while (result.next()) {
				int Resource_ID = result.getInt("Resource_ID");
				String Res_ElementName = result.getString("Res_ElementName");

				rowmap.put(Resource_ID, Res_ElementName);
			}


		}catch (Exception e) {     }


	}

}

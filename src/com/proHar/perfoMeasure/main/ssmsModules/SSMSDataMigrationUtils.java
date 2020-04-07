package com.proHar.perfoMeasure.main.ssmsModules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.proHar.perfoMeasure.main.ValueParser;
import com.proHar.perfoMeasure.main.queries.QueriesLibrary;

public class SSMSDataMigrationUtils {

	public void SSMSDatabaseManagerAgent(Connection conn) {
//		String databaseURL = ConnectionString;
		String insertIntoNavigationTable = "insert into Navigation (TestScenarioID,Application_ID,Page_ID,Nav_UnloadEvent,Nav_RedirectEvent,Nav_AppCache,Nav_TTFB,Nav_Processing,Nav_DomInteractive,Nav_DomComplete,Nav_ContentLoad,Nav_PageLoad, Nav_DateTimes) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
		String insertIntoresourceTable = "insert into Resources (TestScenarioID,Application_ID,Page_ID,Res_ElementName,Res_Duration,Res_DateTimes,Res_StartTimes,Res_EndTimes) values (?,?,?,?,?,?,?,?)";
		//      String insertToHistory = "insert into Resource_History (TestScenarioID,Application_ID,Page_ID,Res_ElementName,Res_Duration,Res_DateTimes,Res_StartTimes,Res_EndTimes) values (?,?,?,?,?,?,?,?);";
		String res_TO_hist = QueriesLibrary.moveResToHistory1;
		String del_Resources = QueriesLibrary.deleteFromEntries;
		try (Connection connection = conn) {
			// get All of the Listed Values
			List<String> navigationValues = ValueParser.navHolder;
			List<String> resourceValues = ValueParser.resHolder;
			String[] navtemp;
			String[] restemp;
			// Insert some data into table.
			int nRowsInserted = 0;
			/* Navigation Values
			 * Navigation Table : 9 Major Fields
			 *
			 * */
			for (String nav : navigationValues) {
				PreparedStatement preparedStatement = connection.prepareStatement(insertIntoNavigationTable);
				navtemp = nav.split(",");
				preparedStatement.setInt(1, Integer.parseInt(navtemp[0])); // TestScenarioID
				preparedStatement.setInt(2, Integer.parseInt(navtemp[1])); // Application_ID
				preparedStatement.setInt(3, Integer.parseInt(navtemp[2])); // Page_ID
				preparedStatement.setString(4, navtemp[3]); // Nav_UnloadEvent
				preparedStatement.setString(5, navtemp[4]); // Nav_RedirectEvent
				preparedStatement.setString(6, navtemp[5]); // Nav_AppCache
				preparedStatement.setString(7, navtemp[6]); // Nav_TTFB
				preparedStatement.setString(8, navtemp[7]); // Nav_Processing
				preparedStatement.setString(9, navtemp[8]); // Nav_DomInteractive
				preparedStatement.setString(10, navtemp[9]); // Nav_DomComplete
				preparedStatement.setString(11, navtemp[10]); // Nav_ContentLoad
				preparedStatement.setString(12, navtemp[11]); // Nav_PageLoad
//				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
//				java.util.Date date = df.parse(navtemp[13]);
				preparedStatement.setTimestamp(13, Timestamp.valueOf(navtemp[13])); // Nav_DateTimes
				nRowsInserted += preparedStatement.executeUpdate();
			}
			/* Temporary Storage ::
			 * Resource Values : Current Table -> Resource History
			 *  [Insert -> Move to Resource_History -> clear Table]
			 */
			for (String nav : resourceValues) {
				PreparedStatement preparedStatement = connection.prepareStatement(insertIntoresourceTable);
				restemp = nav.split(",");
				preparedStatement.setInt(1, Integer.parseInt(restemp[0])); // TestScenarioID
				preparedStatement.setInt(2, Integer.parseInt(restemp[1])); // Application_ID
				preparedStatement.setInt(3,  Integer.parseInt(restemp[2])); // Page_ID
				preparedStatement.setString(4, restemp[3]); // Res_ElementName
				preparedStatement.setString(5, restemp[4]); // Res_Duration

//				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
//				java.util.Date date = df.parse(restemp[5]);
				preparedStatement.setTimestamp(6, Timestamp.valueOf(restemp[6])); // Res_DateTimes
				// Load System Date
//				Date dates = new Date();
//				DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//				String stringDate = sdf.format(dates);
//				java.util.Date dateLoad = df.parse(stringDate);
				Calendar cal = Calendar.getInstance(); 
				java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
				preparedStatement.setTimestamp(7, timestamp); // Res_StartTimes
				preparedStatement.setNull(8, java.sql.Types.DATE); // Res_EndTimes
				nRowsInserted += preparedStatement.executeUpdate();
			}
			// Move all current Entries to Resource_History Table
			PreparedStatement preparedStatements = connection.prepareStatement(res_TO_hist);
			nRowsInserted += preparedStatements.executeUpdate();
						
			// Delete all values from Current Table : Resources
			PreparedStatement preparedStatementes = connection.prepareStatement(del_Resources);
			nRowsInserted += preparedStatementes.executeUpdate();
			
			/* Permanent Storage :: Resource_History
			 *  Resource -> Resource_History
			 */
			/*for (String nav : resourceValues) {
                  PreparedStatement preparedStatement = connection.prepareStatement(insertToHistory);
                  restemp = nav.split(",");
                  preparedStatement.setInt(1, Integer.parseInt(restemp[0]));
                  preparedStatement.setInt(2, Integer.parseInt(restemp[1]));
                  preparedStatement.setString(3, restemp[2]);
                  preparedStatement.setString(4, restemp[3]);
                  SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                 java.util.Date date = df.parse(restemp[4]);
                  preparedStatement.setTimestamp(5, new java.sql.Timestamp(date.getTime()));
                  preparedStatement.setInt(6, Integer.parseInt(restemp[5]));
                  nRowsInserted += preparedStatement.executeUpdate();
             }*/
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();

		}
	}

}

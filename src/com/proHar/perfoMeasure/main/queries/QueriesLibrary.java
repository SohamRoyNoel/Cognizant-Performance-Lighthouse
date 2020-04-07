package com.proHar.perfoMeasure.main.queries;

public class QueriesLibrary {

	// AccessDatabaseManagerAgent

	public static String insertIntoNavigationTable = "insert into Navigation (TestScenarioID,Application_ID,Page_ID,Nav_UnloadEvent,Nav_RedirectEvent,Nav_AppCache,Nav_TTFB,Nav_Processing,Nav_DomInteractive,Nav_DomComplete,Nav_ContentLoad,Nav_PageLoad,Nav_DateTimes) values (?,?,?,?,?,?,?,?,?,?,?,?,?);";
	public static String insertIntoresourceTable = "insert into Resources (TestScenarioID,TestScenarioID,Page_ID,Res_ElementName,Res_Duration,Res_DateTimes,Res_StartTimes,Res_EndTimes) values (?,?,?,?,?,?,?,?)";
	public static String insertToHistory = "insert into Resource_History (TestScenarioID,TestScenarioID,Page_ID,Res_ElementName,Res_Duration,Res_DateTimes,Res_StartTimes,Res_EndTimes) values (?,?,?,?,?,?,?,?)";
	public static String moveResToHistory = "SELECT * INTO Resource_History FROM Resources;";
	public static String deleteFromEntries = "DELETE FROM Resources";
	public static String moveResToHistory1 = "INSERT INTO Resource_History(Resource_ID,TestScenarioID,Application_ID,Page_ID,Res_ElementName,Res_Duration,Res_DateTimes,Res_StartTimes,Res_EndTimes) SELECT * FROM Resources; ";
	public static String deleteReferencialConstrains = "ALTER TABLE Resource_History DROP CONSTRAINT ResourcesResource_History";

	/*
	 * Query To find and Drop the Referencial Integrity Constrains(Access):
	 *
	 * SELECT szRelationship FROM Msysrelationships WHERE szObject = 'Resource_History' and szReferencedObject = 'Resources' --> ResourcesResource_History
	 * ALTER TABLE Resource_History DROP CONSTRAINT ResourcesResource_History
	 *
	 * */

}

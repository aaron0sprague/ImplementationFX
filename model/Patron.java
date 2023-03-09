package model;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

//import userinterface.ViewFactory;

public class Patron extends EntityBase implements IView{
	private static final String myTableName = "Patron";
	protected Properties dependencies;
	//gui
	private String updateStatusMessage = "";
	//constructor
	public Patron(String patronId) throws InvalidPrimaryKeyException{
		super(myTableName);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		if (allDataRetrieved != null){
			int size = allDataRetrieved.size();
			if (size != 1){
				throw new InvalidPrimaryKeyException("Multiple patrons matching id : "+patronId+" found.");
			} else{
				Properties retrievedPatronData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();
				Enumeration allKeys = retrievedPatronData.propertyNames();
				while (allKeys.hasMoreElements() == true){
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedPatronData.getProperty(nextKey);
					// patronId = Integer.parseInt(retrievedPatronData.getProperty("patronId"));
					if (nextValue != null){
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		// If no patron found for Id, throw exception
		else{
			throw new InvalidPrimaryKeyException("No account matching id : "+patronId+" found.");
		}
	}
	public Patron(Properties patronInfo){
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = patronInfo.propertyNames();
		while (allKeys.hasMoreElements() == true){
			String nextKey = (String)allKeys.nextElement();
			String nextValue = patronInfo.getProperty(nextKey);
			if (nextValue != null){
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}
	
	public Patron() {
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
	}

	private void setDependencies()
	{
		dependencies = new Properties();

		myRegistry.setDependencies(dependencies);
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("UpdateStatusMessage") == true)
			return updateStatusMessage;

		return persistentState.getProperty(key);
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("InsertPatron")) {
			processNewPatron((Properties) value);
		}
		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}



	//-----------------------------------------------------------------------------------
	public void update()
	{
		updateStateInDatabase();
	}

	//-----------------------------------------------------------------------------------
	private void updateStateInDatabase()
	{
		try
		{
			if (persistentState.getProperty("patronId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("patronId",
						persistentState.getProperty("patronId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Patron data for patronId : "
						+ persistentState.getProperty("PatronId")
						+ " updated successfully in database!";
			}
			else
			{
				Integer patronId =
						insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("PatronId", "" + patronId);
				updateStatusMessage = "Patron data for new patron : "
						+  persistentState.getProperty("PatronId")
						+ "installed successfully in database!";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error inserting patron data in database!";
		}
	}

	protected void createAndShowPatronView() {
		// create our new view
		View newView = ViewFactory.createView("PatronView", this);
		Scene newScene = new Scene(newView);

		// make the view visible by installing it into the frame
		swapToView(newScene);
	}

	private void processNewPatron(Properties patronInfo) {
		persistentState = new Properties();
		Enumeration allKeys = patronInfo.propertyNames();
		while (allKeys.hasMoreElements()) {
			String nextKey = (String) allKeys.nextElement();
			String nextValue = patronInfo.getProperty(nextKey);

			if (nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
		updateStateInDatabase();
	}

	/**
	 * This method is needed solely to enable the Patron information to be displayable in a table
	 */
	//--------------------------------------------------------------------------
	public Vector<String> getEntryListView()
	{
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("patronId"));
		v.addElement(persistentState.getProperty("name"));
		v.addElement(persistentState.getProperty("address"));
		v.addElement(persistentState.getProperty("city"));
		v.addElement(persistentState.getProperty("stateCode"));
		v.addElement(persistentState.getProperty("zip"));
		v.addElement(persistentState.getProperty("email"));
		v.addElement(persistentState.getProperty("dateOfBirth"));
		v.addElement(persistentState.getProperty("status"));

		return v;
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
public void displayCollection(){
	System.out.println();
}
public String toString()
{
 	return "Title: " + persistentState.getProperty("bookTitle") + "; Author: " +
	  persistentState.getProperty("author")  + "; Year: " + 
	  persistentState.getProperty("pubYear") ;
} 
}
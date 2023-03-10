// specify the package
package model;

// system imports

import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;

import java.util.Properties;
import java.util.Vector;

// project imports
//import userinterface.ViewFactory;

/** The class containing the PatronCollection for the application */
//==============================================================
public class PatronCollection extends EntityBase implements IView
{
	private static final String myTableName = "Patron";

	private Vector<Patron> patronList;
	// GUI Components

	// constructor for this class
	//----------------------------------------------------------
	public PatronCollection() {
		super(myTableName);
		setDependencies();
		patronList = new Vector<>(); // new Vector<Patron>();
	}

	public Vector findPatronsOlderThan(String date) {
		String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < " + date + ") ORDER BY name ASC;";
		return doQuery(query);
	}

	public Vector findPatronsYoungerThan(String date) {
		String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth > " + date + ") ORDER BY name ASC;";
		return doQuery(query);
	}

	public Vector findPatronsAtZipCode(String zip) {
		String query = "SELECT * FROM " + myTableName + " WHERE (zip=" + zip + ") ORDER BY name ASC;";
		return doQuery(query);
	}

	public Vector findPatronsWithNameLike(String name) {

		String query = "SELECT * FROM " + myTableName + " WHERE (name LIKE '%" + name + "%') ORDER BY name ASC;";
		return doQuery(query);
	}

	public Vector findAllPatrons() {

		String query = "SELECT * FROM " + myTableName + " ORDER BY name ASC;";
		return doQuery(query);
	}
	
	private Vector doQuery(String query) {
		try {
			Vector allDataRetrieved = getSelectQueryResult(query);
			if (allDataRetrieved != null) {
//                patronList = new Vector<Patron>();
				for (int index = 0; index < allDataRetrieved.size(); index++) {
					Properties data = (Properties) allDataRetrieved.elementAt(index);
					Patron patron = new Patron(data);
					patronList.add(patron);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		System.out.println("In Bcview: doQuery - PatronCollection.java");
		System.out.println(patronList + "prints patronList");
		return patronList;
	}

	private void setDependencies() {
		Properties dependencies = new Properties();
		dependencies.setProperty("CancelSearchPatron", "SearchPatronView");

		myRegistry.setDependencies(dependencies);
	}
	
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("patrons"))
			return patronList;
		else
		if (key.equals("patronList"))
			return this;
		return null;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{

		myRegistry.updateSubscribers(key,this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	

	public void createAndShowPatronCollectionView() {
		Scene currentScene = myViews.get("PatronSearchView");

		if (currentScene == null) {
			// create our initial view
			View newView = ViewFactory.createView("PatronSearchView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("PatronSearchView", currentScene);
		}

		swapToView(currentScene);
	}

	public void ShowPatronCollectionView() {
		Scene currentScene = myViews.get("PatronCollectionView");
		System.out.println("PatronCollectionView - PatronCollection.java");
		if (currentScene == null) {
			// create our initial view
			View newView = ViewFactory.createView("PatronCollectionView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("PatronCollectionView", currentScene);
		}

		swapToView(currentScene);
	}
}
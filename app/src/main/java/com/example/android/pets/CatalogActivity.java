package com.example.android.pets;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

import static android.content.ContentUris.withAppendedId;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    // This is the Adapter being used to display the list's data

    PetCursorAdapter mCursorAdapter;
    // Identifies a particular Loader being used in this component
    private static final int PET_LOADER = 0;
    // These are the Pet rows that we will retrieve



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });



        //displayDatabaseInfo();
        // Find the ListView which will be populated with the pet data
        ListView petListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        // Create an empty adapter we will use to display the loaded data.
        mCursorAdapter= new PetCursorAdapter(this,null);
        petListView.setAdapter(mCursorAdapter);
         /*
         * Initializes the CursorLoader. The PET_LOADER value is eventually passed
         * to onCreateLoader().
         */
         petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //create new intent to go to Editor activity
                 Intent intent = new Intent(CatalogActivity.this,EditorActivity.class);
                 //form the content uri
                 Uri currentPetUri = ContentUris.withAppendedId (PetContract.CONTENT_URI,id);
                 // set the uri on the data of the intent
                 //setData() is used to point to the location of a data object (like a file for example)
                 intent.setData(currentPetUri);

                 // Send the intent to launch a new activity
                 startActivity(intent);

             }
         });
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }




    private void insertPet() {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);

        Uri newUri=getContentResolver().insert(PetContract.CONTENT_URI,values);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
        String[] projection =
                {   PetEntry._ID,
                        PetEntry.COLUMN_PET_NAME,
                        PetEntry.COLUMN_PET_BREED,
                        };

        /*
     * Takes action based on the ID of the Loader that's being created
     */
        switch (loaderID) {
            case PET_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,   // Parent activity context
                        PetContract.CONTENT_URI,        // Table to query
                        projection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }
    /*
     * Defines the callback that CursorLoader calls
     * when it's finished its query
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    /*
     * Moves the query results into the adapter, causing the
     * ListView fronting this adapter to re-display
     */
        mCursorAdapter.swapCursor(data);
    }
    /*
     * Invoked when the CursorLoader is being reset. For example, this is
     * called if the data in the provider changes and the Cursor becomes stale.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    /*
     * Clears out the adapter's reference to the Cursor.
     * This prevents memory leaks.
     */
        mCursorAdapter.swapCursor(null);
    }
}
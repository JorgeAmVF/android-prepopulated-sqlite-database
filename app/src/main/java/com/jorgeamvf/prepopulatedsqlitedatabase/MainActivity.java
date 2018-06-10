package com.jorgeamvf.prepopulatedsqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    Database mDatabase;

    /*
    "Called when the activity is starting."
    https://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        "Called when the database has been opened."
        https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html#onOpen(android.database.sqlite.SQLiteDatabase)
         */
        mDatabase = new Database(this);
        mDatabase.open();

        /*
        "This interface provides random read-write access to the result set returned by a database query."
        https://developer.android.com/reference/android/database/Cursor.html
         */
        Cursor cursor = mDatabase.getDatabase();
        startManagingCursor(cursor);

        /*
        "A list of column names that will be used to display the data for a group."
        "The group views (from the group layouts) that should display column in the "from" parameter."
        "A list of column names that will be used to display the data for a child."
        "The resource identifier of a layout file that defines the views for a child."
        https://developer.android.com/reference/android/widget/SimpleCursorTreeAdapter.html#SimpleCursorTreeAdapter(android.content.Context, android.database.Cursor, int, java.lang.String[], int[], int, java.lang.String[], int[])
         */
        String[] groupFrom = {
                Database.DATABASE_GROUP_1,
                Database.DATABASE_GROUP_2
        };
        int[] groupTo = {
                R.id.group1,
                R.id.group2
        };
        String[] childFrom = new String[]{
                Database.DATABASE_CHILD_1,
                Database.DATABASE_CHILD_2
        };
        int[] childTo = {
                R.id.child1,
                R.id.child2
        };

        /*
        "An easy adapter to map columns from a cursor to TextViews or ImageViews defined in an XML file."
        https://developer.android.com/reference/android/widget/SimpleCursorTreeAdapter.html
        */
        SimpleCursorTreeAdapter simplecursortreeAdapter = new ExpandableListViewAdapter(
                this,
                cursor,
                R.layout.list_group,
                groupFrom,
                groupTo,
                R.layout.list_child,
                childFrom,
                childTo
        );

        /*
        "Finds a view that was identified by the android:id XML attribute that was processed in onCreate(Bundle)."
        "Sets the adapter that provides data to this view."
        https://developer.android.com/reference/android/app/Activity.html#findViewById(int)
        https://developer.android.com/reference/android/widget/ExpandableListView.html#setAdapter(android.widget.ExpandableListAdapter)
         */
        expandableListView = findViewById(R.id.expandableListview);
        expandableListView.setAdapter(simplecursortreeAdapter);

    }

    /*
    "Closes the Cursor, releasing all of its resources and making it completely invalid."
    https://developer.android.com/reference/android/database/Cursor.html#close()
     */
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private class ExpandableListViewAdapter extends SimpleCursorTreeAdapter {
        private ExpandableListViewAdapter(
                Context context,
                Cursor cursor,
                int groupLayout,
                String[] groupFrom,
                int[] groupTo,
                int childLayout,
                String[] childFrom,
                int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
        }

        /*
        "Gets the Cursor for the children at the given group."
        https://developer.android.com/reference/android/widget/CursorTreeAdapter.html#getChildrenCursor(android.database.Cursor)
         */
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            return mDatabase.getID(groupCursor.getInt(groupCursor.getColumnIndex(Database.DATABASE_ID)));
        }

    }

}

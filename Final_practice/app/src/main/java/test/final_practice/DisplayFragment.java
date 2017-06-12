package test.final_practice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

// reference:
// https://stackoverflow.com/questions/11409028/android-fetch-data-from-database-into-listfragment
public class DisplayFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SQLiteDatabase mDb;
    private List<Stock> mResults;
    private Cursor mCursor;

    // Elements
    private ListView mListView;
    private SimpleCursorAdapter mListAdapter;

    private static final String DB_NAME = "price.db";
    private static final String TABLE_NAME = "entry";
    private static final String COL_TITLE = "title";
    private static final String COL_PRICE = "price";
    private static final String COL_TIMESTAMP = "timestamp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mLayoutInflater = inflater;
        File databasePath = mContext.getDatabasePath(DB_NAME);
        mDb = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
        /* must select id, or it will generate error */
        mCursor = mDb.rawQuery("select _id,* from " + TABLE_NAME, null);
        View view = mLayoutInflater.inflate(R.layout.fragment_display, container, false);
        init(view);
        return view;
    }

    public void init(View v) {
        mListAdapter = new SimpleCursorAdapter(
                mContext,
                R.layout.display_list,
                mCursor,
                new String[] { COL_TITLE, COL_PRICE, COL_TIMESTAMP },
                new int[] { R.id.tv_title, R.id.tv_price, R.id.tv_timestamp},
                1
        );
        mListView = (ListView) v.findViewById(android.R.id.list);
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "clicked on: " + Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

}

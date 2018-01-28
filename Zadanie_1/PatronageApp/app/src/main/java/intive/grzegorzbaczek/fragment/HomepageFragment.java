package intive.grzegorzbaczek.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import intive.grzegorzbaczek.db.DatabaseContentProvider;
import intive.grzegorzbaczek.db.ExpenseDataAdapter;
import intive.grzegorzbaczek.R;
import intive.grzegorzbaczek.entity.Expense;

public class HomepageFragment extends Fragment {

    private static final int LOADER_EXPANSES = 1;
    public static Context context;

    private RecyclerView mRecyclerView;
    private ExpenseDataAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public HomepageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View HomepageFragmentView = inflater.inflate(R.layout.fragment_home_page, container, false);

        mRecyclerView = HomepageFragmentView.findViewById(R.id.expense_recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ExpenseDataAdapter();
        mRecyclerView.setAdapter(mAdapter);

        context = getContext();

        getLoaderManager().initLoader(LOADER_EXPANSES, null, mLoaderCallbacks);

        return HomepageFragmentView;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case LOADER_EXPANSES:
                    return new CursorLoader(
                            getContext(),
                            DatabaseContentProvider.URI_EXPENSE,
                            new String[]{Expense.INDEX_NAME, Expense.DATE_NAME, Expense.NAME_NAME, Expense.VALUE_NAME, Expense.NAME_NAME},
                            null, null,null
                            );
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            switch (loader.getId()) {
                case LOADER_EXPANSES:
                    mAdapter.setExpenses(data);
                    break;
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            switch (loader.getId()) {
                case LOADER_EXPANSES:
                    mAdapter.setExpenses(null);
                    break;
            }
        }
    };
}

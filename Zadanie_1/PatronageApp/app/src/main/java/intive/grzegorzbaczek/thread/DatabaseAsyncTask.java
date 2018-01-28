package intive.grzegorzbaczek.thread;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;

import intive.grzegorzbaczek.db.DatabaseContentProvider;

public class DatabaseAsyncTask extends AsyncTask<String, Void, Void> {

    private DatabaseContentProvider provider;

    @Override
    protected Void doInBackground(String... args) {

        switch (args[0]) {
            case "insert":
                insertData(args);
                break;
            case "update":
                modifyData(args);
                break;
            case "delete":
                deleteData(args);
                break;
            default:
                break;
        }
        return null;
    }

    private void insertData(String... args) {

        provider = new DatabaseContentProvider();
        ContentValues values = new ContentValues(4);
        values.put("date", args[1]);
        values.put("type_id", args[2]);
        values.put("name", args[3]);
        values.put("value", args[4]);
        Uri uri = Uri.parse("content://intive.grzegorzbaczek.db.databasecontentprovider.provider/expenses");
        provider.insert(uri, values);
    }

    private void modifyData(String... args) {
        provider = new DatabaseContentProvider();
        ContentValues values = new ContentValues(4);
        values.put("date", args[2]);
        values.put("type_id", args[3]);
        values.put("name", args[4]);
        values.put("value", args[5]);
        Uri uri = Uri.parse("content://intive.grzegorzbaczek.db.databasecontentprovider.provider/expenses/" + args[1]);
        provider.update(uri, values, null, null);
    }

    private void deleteData(String... args) {

        provider = new DatabaseContentProvider();
        Uri uri = Uri.parse("content://intive.grzegorzbaczek.db.databasecontentprovider.provider/expenses/" + args[1]);
        provider.delete(uri, null, null);
    }
}

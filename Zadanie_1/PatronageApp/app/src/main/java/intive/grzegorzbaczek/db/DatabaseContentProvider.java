package intive.grzegorzbaczek.db;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import intive.grzegorzbaczek.dao.ExpenseDao;
import intive.grzegorzbaczek.entity.Expense;

public class DatabaseContentProvider extends ContentProvider {

    public static final String AUTHORITY = "intive.grzegorzbaczek.db.databasecontentprovider.provider";

    public static final Uri URI_EXPENSE = Uri.parse(
            "content://" + AUTHORITY + "/" + Expense.TABLE_NAME
    );

    private static final int CODE_DIR = 1;
    private static final int CODE_ITEM = 2;

    private static final UriMatcher MATCHER = new UriMatcher((UriMatcher.NO_MATCH));

    static {
        MATCHER.addURI(AUTHORITY, Expense.TABLE_NAME, CODE_DIR);
        MATCHER.addURI(AUTHORITY, Expense.TABLE_NAME + "/*", CODE_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        if (code == CODE_DIR || code == CODE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            ExpenseDao expenseDao = AppDatabase.getInstance(context).expenseDao();
            final Cursor cursor;
            if (code == CODE_DIR) {
                cursor = expenseDao.selectAll();
            } else {
                cursor = expenseDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + Expense.TABLE_NAME;
            case CODE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + Expense.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (MATCHER.match(uri)) {
            case CODE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = AppDatabase.getInstance(context).expenseDao().insert(Expense.fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_ITEM:
                throw new IllegalArgumentException("Invalid URI: " + uri); //TO IMPLEMENT
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CODE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID " + uri);
            case CODE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = AppDatabase.getInstance(context).expenseDao().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI, " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CODE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID " + uri);
            case CODE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Expense expense = Expense.fromContentValues(contentValues);
                expense.id = ContentUris.parseId(uri);
                final int count = AppDatabase.getInstance(context).expenseDao().update(expense);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI, " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final AppDatabase database = AppDatabase.getInstance(context);
        database.beginTransaction();
        try {
            final ContentProviderResult[] results = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return results;
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (MATCHER.match(uri)) {
            case CODE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final AppDatabase database = AppDatabase.getInstance(context);
                final Expense[] expenses = new Expense[values.length];
                for (int i = 0; i < values.length; i++) {
                    expenses[i] = Expense.fromContentValues(values[i]);
                }
                return database.expenseDao().insertAll(expenses).length;
            case CODE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI, " + uri);
        }
    }
}

package scott.com.workhard.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

public class WorkoutsProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    CompositeSubscription subscription = new CompositeSubscription();
    private static final int HISTORY = 100;
    private WorkhardDbHelper mOpenHelper;


    @Override
    public boolean onCreate() {
        mOpenHelper = new WorkhardDbHelper(getContext());
        mOpenHelper.getReadableDatabase().execSQL("PRAGMA foreign_keys=ON");
        return true;
    }

    @Override
    public Cursor query(@NonNull final Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor = null;
        switch (sUriMatcher.match(uri)) {
            case HISTORY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        WorkoutsContract.TABLE_ITEM_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return retCursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HISTORY:
                return WorkoutsContract.CONTENT_STORY_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case HISTORY: {
                Cursor exists = db.query(WorkoutsContract.TABLE_ITEM_NAME,
                        new String[]{WorkoutsContract.HistoryEntry.ITEM_ID},
                        WorkoutsContract.HistoryEntry.ITEM_ID + " = ?",
                        new String[]{values.getAsString(WorkoutsContract.HistoryEntry.ITEM_ID)},
                        null,
                        null,
                        null);
                if (exists.moveToLast()) {
                    long _id = db.update(WorkoutsContract.TABLE_ITEM_NAME, values, WorkoutsContract.HistoryEntry.ITEM_ID + " = ?",
                            new String[]{values.getAsString(WorkoutsContract.HistoryEntry.ITEM_ID)});
                    if (_id > 0) {
                        returnUri = WorkoutsContract.HistoryEntry.buildStoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(WorkoutsContract.TABLE_ITEM_NAME, null, values);
                    if (_id > 0) {
                        returnUri = WorkoutsContract.HistoryEntry.buildStoryUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case HISTORY:
                rowsDeleted = db.delete(
                        WorkoutsContract.TABLE_ITEM_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WorkoutsContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, WorkoutsContract.PATH_ITEM, HISTORY);
        return matcher;
    }
}

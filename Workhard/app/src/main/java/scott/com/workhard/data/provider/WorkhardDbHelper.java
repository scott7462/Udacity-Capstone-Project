package scott.com.workhard.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 12/18/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class WorkhardDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "workhard.db";
    private static final int DATABASE_VERSION = 1;

    public WorkhardDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + WorkoutsContract.TABLE_ITEM_NAME + " (" +
                WorkoutsContract.HistoryEntry._ID + " INTEGER PRIMARY KEY," +
                WorkoutsContract.HistoryEntry.ITEM_ID + " TEXT UNIQUE NOT NULL," +
                WorkoutsContract.HistoryEntry.NAME + " TEXT," +
                WorkoutsContract.HistoryEntry.ROUNDS + " TEXT" +
                " );";
        db.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkoutsContract.TABLE_ITEM_NAME);
        onCreate(db);
    }
}

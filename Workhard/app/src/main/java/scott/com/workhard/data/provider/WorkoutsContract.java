package scott.com.workhard.data.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class WorkoutsContract {

    public static final String CONTENT_AUTHORITY = "scott.com.workhard";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String TABLE_ITEM_NAME = "workout";
    public static final String PATH_ITEM = "workout";
    public static final String CONTENT_STORY_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ITEM;

    public static final class HistoryEntry implements BaseColumns {

        public static final Uri CONTENT_STORY_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM).build();
        public static final String ITEM_ID = "item_id";
        public static final String NAME = "name";
        public static final String ROUNDS = "rounds";

        public static final String[] STORY_COLUMNS = {
                HistoryEntry._ID,
                HistoryEntry.ITEM_ID,
                HistoryEntry.NAME,
                HistoryEntry.ROUNDS,
        };

        public static final int COLUMN_ID = 0;
        public static final int COLUMN_NAME = 1;
        public static final int COLUMN_ROUNDS = 2;

        public static Uri buildStoryUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_STORY_URI, id);
        }

        public static Uri buildStoriesUri() {
            return CONTENT_STORY_URI.buildUpon().build();
        }

    }


}

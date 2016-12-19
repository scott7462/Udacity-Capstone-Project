package scott.com.workhard.data.provider;

import android.content.ContentResolver;
import android.content.ContentValues;

import java.util.List;

import scott.com.workhard.entities.Workout;

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


public class DataPersister {

    private final ContentResolver contentResolver;

    public DataPersister(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public int removeAllHistory() {
        return contentResolver.delete(WorkoutsContract.HistoryEntry.CONTENT_STORY_URI, null, null);
    }

    public void addWorkout(List<Workout> workouts) {
        for (Workout workout : workouts) {
            ContentValues workoutsValues = new ContentValues();
            workoutsValues.put(WorkoutsContract.HistoryEntry.ITEM_ID, workout.getKey());
            workoutsValues.put(WorkoutsContract.HistoryEntry.NAME, workout.getName());
            workoutsValues.put(WorkoutsContract.HistoryEntry.ROUNDS, workout.getRounds());
            contentResolver.insert(WorkoutsContract.HistoryEntry.CONTENT_STORY_URI, workoutsValues);
        }
    }
}
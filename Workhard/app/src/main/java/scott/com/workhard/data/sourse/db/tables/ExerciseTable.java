package scott.com.workhard.data.sourse.db.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import scott.com.workhard.entities.Exercise;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/18/16.
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


public class ExerciseTable extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private int repetition;
    private String description;
    private String url;

    public ExerciseTable() {
    }

    public ExerciseTable(Exercise exercise) {
        setId(exercise.getId());
        setName(exercise.getName());
        setRepetition(exercise.getRepetition());
        setDescription(exercise.getDescription());
        setUrl(exercise.getUrl());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Exercise transformToExercise() {
        return new Exercise()
                .withId(getId())
                .withName(getName())
                .withDescription(getDescription())
                .withUrl(getUrl())
                .withRepetitions(getRepetition());
    }
}

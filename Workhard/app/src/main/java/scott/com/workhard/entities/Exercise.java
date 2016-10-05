package scott.com.workhard.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import scott.com.workhard.data.sourse.db.tables.ExerciseTable;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/4/16.
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


public class Exercise implements Parcelable {

    public static final String EXERCISE_ARG = Exercise.class.getName();
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("repetition")
    @Expose
    private int repetition;
    @SerializedName("is_checked")
    @Expose
    private boolean isChecked;

    public Exercise() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Exercise withId(String id) {
        setId(id);
        return this;
    }
    public Exercise withName(String name) {
        setName(name);
        return this;
    }

    public Exercise withDescription(String description) {
        setDescription(description);
        return this;
    }

    public Exercise withUrl(String url) {
        setUrl(url);
        return this;
    }

    public Exercise withRepetitions(int repetitions) {
        setRepetition(repetitions);
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeInt(this.repetition);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected Exercise(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.repetition = in.readInt();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel source) {
            return new Exercise(source);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}

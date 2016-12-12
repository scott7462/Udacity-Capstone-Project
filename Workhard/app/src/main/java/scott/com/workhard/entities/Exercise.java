package scott.com.workhard.entities;

import android.os.Parcel;
import android.os.Parcelable;

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
    public static final String NEXT_EXERCISE_ARG = Exercise.class.getName();
    public static final String EXERCISE_TABLE = "exercises";
    public static final String NAME = "name";
    private String key;
    private String name;
    private String description;
    private String url;
    private int repetitions = 1;
    private boolean isChecked;
    private int position;

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

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public Exercise withKey(String key){
        setKey(key);
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
        setRepetitions(repetitions);
        return this;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeInt(this.repetitions);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.position);
    }

    protected Exercise(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.repetitions = in.readInt();
        this.isChecked = in.readByte() != 0;
        this.position = in.readInt();
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

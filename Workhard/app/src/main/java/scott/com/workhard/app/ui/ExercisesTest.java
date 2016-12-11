package scott.com.workhard.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.entities.ExerciseTest;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 12/3/16.
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


public class ExercisesTest extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.url)
    EditText url;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_test);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        databaseReference = firebaseDatabase.getReference().child("exercises");


    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, ExercisesTest.class));
    }


    @OnClick(R.id.send)
    public void onClick() {
        ExerciseTest exercise = new ExerciseTest();
        exercise.setDescription(description.getText().toString());
        exercise.setName(name.getText().toString());
        exercise.setUrl(url.getText().toString());
        databaseReference.push().setValue(exercise);

    }
}

package scott.com.workhard.data.exercise.sourse.local;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import scott.com.workhard.data.exercise.ExerciseRepository;
import scott.com.workhard.data.sourse.db.realm_utils.RealmObservable;
import scott.com.workhard.data.sourse.db.tables.ExerciseTable;
import scott.com.workhard.entities.Exercise;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/17/16.
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


public class ExerciseLocalData implements ExerciseRepository {

    private static ExerciseLocalData INSTANCE;

    @NonNull
    public static ExerciseLocalData newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExerciseLocalData();
        }
        return INSTANCE;
    }

    @Override
    public Observable<Exercise> add(final Exercise exercise) {
        return RealmObservable.object(new Func1<Realm, ExerciseTable>() {
            @Override
            public ExerciseTable call(Realm realm) {
                ExerciseTable row = new ExerciseTable(exercise);
                return realm.copyToRealmOrUpdate(row);
            }
        }).flatMap(new Func1<ExerciseTable, Observable<Exercise>>() {
            @Override
            public Observable<Exercise> call(ExerciseTable exerciseTable) {
                return Observable.just(new Exercise(exerciseTable));
            }
        });
    }

    @Override
    public Observable<Boolean> delete(Exercise object) {
        return null;
    }

    @Override
    public Observable<Exercise> update(Exercise object) {
        return null;
    }

    @Override
    public Observable<List<Exercise>> findAll() {
        return RealmObservable
                .results(new Func1<Realm, RealmResults<ExerciseTable>>() {
                    @Override
                    public RealmResults<ExerciseTable> call(Realm realm) {
                        return realm.where(ExerciseTable.class).findAll();
                    }
                })
                .flatMap(new Func1<RealmResults<ExerciseTable>, Observable<List<Exercise>>>() {
                    @Override
                    public Observable<List<Exercise>> call(RealmResults<ExerciseTable> realmResults) {
                        List<Exercise> exercises = new ArrayList<>();
                        for (ExerciseTable realmItem : realmResults) {
                            exercises.add(new Exercise(realmItem));
                        }
                        return Observable.just(exercises);
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Observable.error(throwable);
                    }
                });
    }
}

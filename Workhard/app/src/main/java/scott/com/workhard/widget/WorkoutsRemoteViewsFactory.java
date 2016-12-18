package scott.com.workhard.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.init.launch.LaunchActivity;
import scott.com.workhard.data.Injection;
import scott.com.workhard.entities.Workout;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/10/16.
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
public class WorkoutsRemoteViewsFactory implements RemoteViewsFactory {
    private static final String TAG = WorkoutsRemoteViewsFactory.class.getSimpleName();
    private List<Workout> items = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private CompositeSubscription subscription = new CompositeSubscription();

    public WorkoutsRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private void updateData() {
        subscription.add(Injection.provideWorkoutsRepository().findHistoriesWorkouts()
                .subscribe(new Subscriber<List<Workout>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Workout> workouts) {
                        items.clear();
                        items.addAll(workouts);
                        WorkoutsRemoteViewsFactory.this.getCount();
                    }
                }));
    }

    public void onDestroy() {
        items.clear();
    }

    public int getCount() {
        return items.size();
    }

    public RemoteViews getViewAt(int position) {
        Workout workout = items.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_collection_item);
        rv.setTextViewText(R.id.tVWidgetWorkoutName, workout.getName());
        String srounds = "Rounds " + String.valueOf(workout.getRounds());
        rv.setTextViewText(R.id.tVWidgetWorkoutRounds, srounds);

        Intent clickIntentTemplate = new Intent(mContext, LaunchActivity.class);
        rv.setOnClickFillInIntent(R.id.lLWidget, clickIntentTemplate);
        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        updateData();
    }

    @Override
    public void onDataSetChanged() {

    }

}
package scott.com.workhard.data.models.workout.remote;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.data.models.workout.WorkoutRepository;
import scott.com.workhard.data.sourse.rest.api.RestClient;
import scott.com.workhard.data.sourse.rest.response.ResponseWorkout;
import scott.com.workhard.entities.Workout;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class WorkoutRemote implements WorkoutRepository {

    private static WorkoutRemote instance;
    private RestClient restClientPublic;

    public static WorkoutRemote newInstance() {
        if (instance == null) {
            instance = new WorkoutRemote();
        }
        return instance;
    }

    public WorkoutRemote() {
        restClientPublic = new RestClient(App.getGlobalContext().getString(R.string.base_url));
    }

    public RestClient getRestClientPublic() {
        return restClientPublic;
    }

    @Override
    public Observable<Workout> add(Workout object) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(Workout object) {
        return null;
    }

    @Override
    public Observable<Workout> update(Workout object) {
        return null;
    }


    @Override
    public Observable<List<Workout>> findAll() {
        return getRestClientPublic().getPublicService().getWorkouts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseWorkout, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(ResponseWorkout responseWorkout) {
                        return Observable.just(responseWorkout.getWorkouts());
                    }
                });
    }

    @Override
    public Observable<List<Workout>> findMyWorkouts() {
        return getRestClientPublic().getPublicService().getMyWorkouts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseWorkout, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(ResponseWorkout responseWorkout) {
                        return Observable.just(responseWorkout.getWorkouts());
                    }
                });
    }

    @Override
    public Observable<List<Workout>> findHistoriesWorkouts() {
        return getRestClientPublic().getPublicService().getHistoriesWorkouts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseWorkout, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(ResponseWorkout responseWorkout) {
                        return Observable.just(responseWorkout.getWorkouts());
                    }
                });
    }
}

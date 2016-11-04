package scott.com.workhard.data.sourse.rest.api;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;
import scott.com.workhard.data.sourse.rest.request.RequestLogin;
import scott.com.workhard.data.sourse.rest.response.ResponseExercises;
import scott.com.workhard.data.sourse.rest.response.ResponseLogin;
import scott.com.workhard.data.sourse.rest.response.ResponseWorkout;
import scott.com.workhard.entities.User;
import scott.com.workhard.entities.Workout;

/**
 * @author pedroscott
 * @version: 7/14/16.
 * <p>
 * Copyright (C) 2015 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface ApiPrivateClient {

    @GET("exercise")
    Observable<ResponseExercises> exercises();

    @PUT("user")
    Observable<ResponseLogin> updateProfile(@Body User body);

    @GET("workouts")
    Observable<ResponseWorkout> workouts();

    @GET("workout/histories")
    Observable<ResponseWorkout> historiesWorkouts();

    @GET("workout/my")
    Observable<ResponseWorkout> myWorkouts();

    @POST("workout/save")
    Observable<Workout> createWorkouts(@Body Workout body);
}

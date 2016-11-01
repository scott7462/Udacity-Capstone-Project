package scott.com.workhard.data.sourse.rest.api;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import scott.com.workhard.data.sourse.rest.request.RequestLogin;
import scott.com.workhard.data.sourse.rest.response.ResponseExercises;
import scott.com.workhard.data.sourse.rest.response.ResponseLogin;
import scott.com.workhard.data.sourse.rest.response.ResponseWorkout;
import scott.com.workhard.entities.User;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/17/16.
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

public class PublicService {

    private final ApiClient apiService;

    public PublicService(ApiClient apiService) {
        this.apiService = apiService;
    }

    public Observable<ResponseLogin> login(RequestLogin requestLogin) {
        return apiService.login(requestLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResponseLogin> register(User user) {
        return apiService.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

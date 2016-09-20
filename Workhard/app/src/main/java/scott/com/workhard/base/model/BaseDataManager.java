package scott.com.workhard.base.model;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

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


public class BaseDataManager<T, H extends Repository<T>> implements Repository<T> {

    private H restRepository;
    private H dbRepository;

    private static BaseDataManager INSTANCE = null;

    public BaseDataManager(@NonNull H restRepository, @NonNull H dbRepository) {
        setRestRepository(restRepository);
        setDbRepository(dbRepository);
    }

    public H getRestRepository() {
        return restRepository;
    }

    public void setRestRepository(H restRepository) {
        this.restRepository = restRepository;
    }

    public H getDbRepository() {
        return dbRepository;
    }

    public void setDbRepository(H dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public Observable<T> add(T object) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(T object) {
        return null;
    }

    @Override
    public Observable<T> update(T object) {
        return null;
    }

    @Override
    public Observable<List<T>> findAll() {
        return null;
    }
}
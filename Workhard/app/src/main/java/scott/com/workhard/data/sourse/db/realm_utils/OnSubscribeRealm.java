package scott.com.workhard.data.sourse.db.realm_utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;
import scott.com.workhard.app.App;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/18/16.
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

abstract class OnSubscribeRealm<T> implements Observable.OnSubscribe<T> {


    private final List<Subscriber<? super T>> subscribers = new ArrayList<>();
    private final AtomicBoolean canceled = new AtomicBoolean();

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        boolean canceled = this.canceled.get();
        if (!canceled && !subscribers.isEmpty()) {
            subscriber.add(newUnSubscribeAction(subscriber));
            subscribers.add(subscriber);
            return;
        } else if (canceled) {
            return;
        }

        subscriber.add(newUnSubscribeAction(subscriber));
        subscribers.add(subscriber);

        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(App.getGlobalContext());
        Realm realm = Realm.getInstance(builder.build());
        boolean withError = false;

        T object = null;
        try {
            if (!this.canceled.get()) {
                realm.beginTransaction();
                object = get(realm);
                if (object != null && !this.canceled.get()) {
                    realm.commitTransaction();
                } else {
                    realm.cancelTransaction();
                }
            }
        } catch (RuntimeException e) {
            realm.cancelTransaction();
            sendOnError(new RealmException("Error during transaction.", e));
            withError = true;
        } catch (Error e) {
            realm.cancelTransaction();
            sendOnError(e);
            withError = true;
        }
        if (object != null && !this.canceled.get() && !withError) {
            sendOnNext(object);
        }

        try {
            realm.close();
        } catch (RealmException ex) {
            sendOnError(ex);
            withError = true;
        }
        if (!withError) {
            sendOnCompleted();
        }
        this.canceled.set(false);
    }

    private void sendOnNext(T object) {
        for (int i = 0; i < subscribers.size(); i++) {
            Subscriber<? super T> subscriber = subscribers.get(i);
            subscriber.onNext(object);
        }
    }

    private void sendOnError(Throwable e) {
        for (int i = 0; i < subscribers.size(); i++) {
            Subscriber<? super T> subscriber = subscribers.get(i);
            subscriber.onError(e);
        }
    }

    private void sendOnCompleted() {
        for (int i = 0; i < subscribers.size(); i++) {
            Subscriber<? super T> subscriber = subscribers.get(i);
            subscriber.onCompleted();
        }
    }

    @NonNull
    private Subscription newUnSubscribeAction(final Subscriber<? super T> subscriber) {
        return Subscriptions.create(new Action0() {
            @Override
            public void call() {
                subscribers.remove(subscriber);
                if (subscribers.isEmpty()) {
                    canceled.set(true);
                }

            }
        });
    }

    public abstract T get(Realm realm);
}

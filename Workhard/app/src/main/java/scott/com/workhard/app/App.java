package scott.com.workhard.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.stetho.Stetho;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import scott.com.workhard.R;

/**
 * @author pedroscott ${EMAIL}
 * @version 7/14/16.
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

public class App extends MultiDexApplication {

    private static Context globalContext;

    public static Context getGlobalContext() {
        return globalContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        globalContext = this.getApplicationContext();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitter_key),getString(R.string.twitter_secret));
        Fabric.with(this, new Twitter(authConfig));
        facebookInit();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        Dexter.initialize(this);
    }


    private void facebookInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}

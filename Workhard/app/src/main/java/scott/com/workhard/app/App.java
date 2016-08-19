package scott.com.workhard.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import scott.com.workhard.R;
import scott.com.workhard.repository.rest.RestClient;

/**
 * @author pedroscott ${EMAIL}
 * @version 7/14/16.
 * <p>
 * Copyright (C) 2015 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 *  @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class App extends MultiDexApplication {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MDBSpgrxgVsUMTK3obdGghOHx";
    private static final String TWITTER_SECRET = "Bk81Tv1x4PyOb4D1Ak2ZkPn0VMMaIwiJDmwpsWpnGgfiRQhAVA";


    private static RestClient restClientPublic;
    private static Context globalContext;

    public static Context getGlobalContext() {
        return globalContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        facebookInit();
        globalContext = this.getApplicationContext();

        restClientPublic = new RestClient(getString(R.string.base_url));
    }

    private void facebookInit() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static RestClient getRestClientPublic() {
        return restClientPublic;
    }
}

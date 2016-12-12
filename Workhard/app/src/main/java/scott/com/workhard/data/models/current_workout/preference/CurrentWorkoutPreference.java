package scott.com.workhard.data.models.current_workout.preference;

import scott.com.workhard.utils.preferences.PreferenceUtils;

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
public class CurrentWorkoutPreference extends PreferenceUtils {

    private static final String PREFERENCE_CURRENT_WORK_OUT = "preference_current_workout";

    /**
     * This method enable or disable the current workout
     *
     * @param enabled to active the current workout
     */
    public static void setPreferenceCurrentWorkOut(boolean enabled) {
        getEditor().putBoolean(PREFERENCE_CURRENT_WORK_OUT, enabled).apply();
    }

    /**
     * Check if the user have active current workout
     */
    public static boolean getPreferenceCurrentWorkOut() {
        return getPreferences().getBoolean(PREFERENCE_CURRENT_WORK_OUT, false);
    }

}

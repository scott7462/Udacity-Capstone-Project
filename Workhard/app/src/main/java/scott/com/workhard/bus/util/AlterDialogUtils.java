package scott.com.workhard.bus.util;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import scott.com.workhard.R;
import scott.com.workhard.bus.event.EventAlterDialog;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/8/16.
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

public class AlterDialogUtils {
    public static AlertDialog makeAlterDialog(EventAlterDialog eventAlterDialog, Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.myDialog));
        if (eventAlterDialog.getMessage() != null) {
            builder.setMessage(eventAlterDialog.getMessage());
        }

        if (eventAlterDialog.getTitle() != null) {
            builder.setTitle(eventAlterDialog.getTitle());
        }

        if (eventAlterDialog.getPositiveAction() != null) {
            builder.setPositiveButton(eventAlterDialog.getPositiveButtonText(), eventAlterDialog.getPositiveAction());
        }

        if (eventAlterDialog.getNegativeAction() != null) {
            builder.setNegativeButton(eventAlterDialog.getNegativeButtonText(), eventAlterDialog.getNegativeAction());
        }

        return builder.create();
    }
}

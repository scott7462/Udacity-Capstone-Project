package scott.com.workhard.bus.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import scott.com.workhard.bus.event.EventSnackBar;

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
public class SnackBarUtils {
    public static void makeSnackBar(EventSnackBar eventSnackBar) {
        Snackbar snackbar = Snackbar
                .make(eventSnackBar.getViewParent(), eventSnackBar.getMessage(), Snackbar.LENGTH_LONG);

        if (eventSnackBar.getAction() != null) {
            snackbar.setAction(eventSnackBar.getTextAction(), eventSnackBar.getAction());
        }

        if (eventSnackBar.getTextColor() != 0) {
            snackbar.setActionTextColor(eventSnackBar.getTextColor());
        }

        if (eventSnackBar.getTextActionColor() != 0) {
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(eventSnackBar.getTextActionColor());
        }

        snackbar.show();
    }
}

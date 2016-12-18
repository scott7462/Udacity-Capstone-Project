package scott.com.workhard.bus.event;

import android.view.View;

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
public class EventSnackBar {

    private String message;
    private int textColor;
    private View viewParent;
    private String textAction = "";
    private View.OnClickListener action;
    private int textActionColor;


    public int getTextColor() {
        return textColor;
    }

    public View getViewParent() {
        return viewParent;
    }

    public String getTextAction() {
        return textAction;
    }

    public String getMessage() {
        return message;
    }

    public View.OnClickListener getAction() {
        return action;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setViewParent(View viewParent) {
        this.viewParent = viewParent;
    }

    public void setTextAction(String textAction) {
        this.textAction = textAction;
    }


    public void setAction(View.OnClickListener action) {
        this.action = action;
    }

    public int getTextActionColor() {
        return textActionColor;
    }

    public void setTextActionColor(int textActionColor) {
        this.textActionColor = textActionColor;
    }

    public EventSnackBar withAction(String actionText, View.OnClickListener action) {
        setTextAction(actionText);
        setAction(action);
        return this;
    }

    public EventSnackBar withActionTextColor(String actionText, View.OnClickListener action, int actionTextColor) {
        setTextActionColor(actionTextColor);
        setTextAction(actionText);
        setAction(action);
        return this;
    }

    public EventSnackBar withMessage(String message) {
        setMessage(message);
        return this;
    }

    public EventSnackBar withTextColor(String message, int color) {
        setMessage(message);
        setTextActionColor(color);
        return this;
    }


}

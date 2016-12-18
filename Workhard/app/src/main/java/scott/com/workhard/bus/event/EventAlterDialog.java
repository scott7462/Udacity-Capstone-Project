package scott.com.workhard.bus.event;

import android.content.DialogInterface;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/11/16.
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
public class EventAlterDialog {

    private String message;
    private String title;
    private DialogInterface.OnClickListener positiveAction;
    private String positiveButtonText;
    private DialogInterface.OnClickListener negativeAction;
    private String negativeButtonText;

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public DialogInterface.OnClickListener getPositiveAction() {
        return positiveAction;
    }

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    public DialogInterface.OnClickListener getNegativeAction() {
        return negativeAction;
    }

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPositiveAction(DialogInterface.OnClickListener positiveAction) {
        this.positiveAction = positiveAction;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public void setNegativeAction(DialogInterface.OnClickListener negativeAction) {
        this.negativeAction = negativeAction;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    public EventAlterDialog withTitle(String message) {
        setTitle(message);
        return this;
    }

    public EventAlterDialog withMessage(String message) {
        setMessage(message);
        return this;
    }

    public EventAlterDialog withPositveButton(String text, DialogInterface.OnClickListener positiveAction) {
        setPositiveButtonText(text);
        setPositiveAction(positiveAction);
        return this;
    }

    public EventAlterDialog withNegativeButton(String text, DialogInterface.OnClickListener negativeAction) {
        setNegativeButtonText(text);
        setNegativeAction(negativeAction);
        return this;
    }
}

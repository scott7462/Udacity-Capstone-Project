package scott.com.workhard.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.EditText;

import org.joda.time.DateTime;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 8/21/16.
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
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private EditText toShowResult;

    public void setToShowResult(EditText toShowResult) {
        this.toShowResult = toShowResult;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        int year = DateTime.now().getYear();
        int month = DateTime.now().getMonthOfYear();
        int day = DateTime.now().getDayOfMonth();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        toShowResult.setText(year + "-" + month + "-" + day);
    }

    public static void showDatePickerDialog(FragmentManager f, EditText editText) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setToShowResult(editText);
        newFragment.show(f, "datePicker");
    }
}
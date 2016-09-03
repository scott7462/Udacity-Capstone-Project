package scott.com.workhard.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import scott.com.workhard.R;

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
    private TextView toShowResult;
    private MutableDateTime mutableDateTimeDate;

    public void setToShowResult(TextView toShowResult) {
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
        mutableDateTimeDate = new MutableDateTime();
        mutableDateTimeDate.setYear(year);
        mutableDateTimeDate.setMonthOfYear(month + 1);
        mutableDateTimeDate.setDayOfMonth(day);
        toShowResult.setText(DateTimeUtils.getStringPatternFromDateTime(toShowResult.getContext().getString(R.string.date_register_formatter), mutableDateTimeDate));
    }

    public static void showDatePickerDialog(FragmentManager f, TextView textView) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setToShowResult(textView);
        newFragment.show(f, "datePicker");
    }
}
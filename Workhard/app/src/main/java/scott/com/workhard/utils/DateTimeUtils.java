package scott.com.workhard.utils;

import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/3/16.
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
public class DateTimeUtils {

    public static DateTimeFormatter presenter;
    public static DateTimeFormatter formatter;

    public static String convertToPatternFromPattern(String fromPattern, String date, String toPattern) {
        return getStringPatternFromDateTime(toPattern, getDateTimeFromPattern(fromPattern, date));
    }

    public static MutableDateTime getDateTimeFromPattern(String inputPattern, String date) {
        formatter = DateTimeFormat.forPattern(inputPattern);
        return formatter.parseMutableDateTime(date);
    }

    public static String getStringPatternFromDateTime(String pattern, MutableDateTime date) {
        presenter = DateTimeFormat.forPattern(pattern);
        return presenter.print(date);
    }

}

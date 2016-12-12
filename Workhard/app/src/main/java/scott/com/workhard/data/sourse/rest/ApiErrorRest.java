package scott.com.workhard.data.sourse.rest;

import android.support.annotation.IntDef;

import com.google.firebase.FirebaseException;
import com.google.gson.Gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import scott.com.workhard.R;
import scott.com.workhard.app.App;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/23/16.
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
public class ApiErrorRest {

    public static final int NETWORK = 1234;
    public static final int UNKNOWN_HOST = 4321;
    public static final int SOCKET_TIME_OUT = 1987;
    public static final int GENERAL = 3439208;

    @IntDef({NETWORK, UNKNOWN_HOST, SOCKET_TIME_OUT, GENERAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
    }


    public static String handelError(Throwable error) {
        if (isRelatedToNetwork(error)) {
            return App.getGlobalContext().getString(R.string.error_network_no_internet_connection);
        } else if (error instanceof HttpException) {
            try {
                HttpException retroError = (HttpException) error;
                ApiError errorObj = new Gson().fromJson(retroError.response().errorBody().charStream(), ApiError.class);
                return ErrorFactory.getErrorMessage(errorObj.getCode());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else if (error instanceof FirebaseException) {
            return error.getMessage();
        }

        return ErrorFactory.getErrorMessage(ErrorFactory.GENERAL_DEFAULT_ERROR);
    }

    @ErrorType
    public static int getNetworkErrorType(Throwable error) {
        if (isNetworkError(error)) {
            return NETWORK;
        } else if (isUnknownHostException(error)) {
            return UNKNOWN_HOST;
        } else if (isSocketTimeoutException(error)) {
            return SOCKET_TIME_OUT;
        }
        return GENERAL;
    }

    private static boolean isNetworkError(Throwable error) {
        return error instanceof java.net.ConnectException;
    }

    private static boolean isUnknownHostException(Throwable error) {
        return error instanceof UnknownHostException;
    }

    private static boolean isSocketTimeoutException(Throwable error) {
        return error instanceof SocketTimeoutException;
    }

    public static boolean isRelatedToNetwork(Throwable error) {
        return isNetworkError(error) || isSocketTimeoutException(error) || isUnknownHostException(error);
    }

}

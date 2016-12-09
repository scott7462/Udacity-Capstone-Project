package scott.com.workhard.data.models.session.sourse.firebase;

import android.support.annotation.NonNull;

import com.facebook.login.LoginResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.kelvinapps.rxfirebase.RxFirebaseAuth;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.data.models.session.SessionFireBase;
import scott.com.workhard.entities.User;
import scott.com.workhard.utils.Md5;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/17/16.
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
public class SessionFireBaseData implements SessionFireBase {

    private static SessionFireBaseData instance = null;
    /**
     * New Instance method init the FireBase Instance
     * @return SessionFireBaseData
     */
    @NonNull
    public static SessionFireBaseData newInstance() {
        if (instance == null) {
            instance = new SessionFireBaseData();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            instance.getFireBaseUserReference().keepSynced(true);
        }
        return instance;
    }

    /**
     * Method get the Rx function to login with FireBase and call the getRxFireBaseUserByUid to get profile of the user.
     *
     * @param email    The email to sing in with regular login
     * @param password The password to sing in with regular login
     * @return Observable<User>
     */
    @Override
    public Observable<User> login(String email, String password) {
        return RxFirebaseAuth.signInWithEmailAndPassword(FirebaseAuth.getInstance(), email, Md5.generateMD5(password))
                .flatMap(new Func1<AuthResult, Observable<DataSnapshot>>() {
                    @Override
                    public Observable<DataSnapshot> call(AuthResult authResult) {
                        return getRxFireBaseUserByUid(authResult.getUser().getUid());
                    }
                }).flatMap(getCurrentUserData());
    }

    /**
     * Remove the session to FireBase and assume that FireBase close the session
     */
    @Override
    public Observable<Boolean> logout() {
        FirebaseAuth.getInstance().signOut();
        return Observable.just(true);
    }

    /**
     * Validate with FireBase session and get the current user in base of the auth object
     *
     * @return Observable<User>
     */
    @Override
    public Observable<User> getSessionUser() {
        return RxFirebaseAuth.observeAuthState(FirebaseAuth.getInstance())
                .flatMap(getCurrentUserAuth())
                .flatMap(getCurrentUserData());
    }

    /**
     * This method register in FireBase auth the user and save the profile on real time data base.
     *
     * @param user The user to register by email and password on FireBase Auth
     * @return Observable<User>
     */
    @Override
    public Observable<User> register(User user) {
        return add(user);
    }

    /**
     * With the FireBase uid do the query of the data form the user in real time data base
     *
     * @param uid the id of the user on the auth FireBase
     * @return Observable<DataSnapshot>
     */
    private Observable<DataSnapshot> getRxFireBaseUserByUid(String uid) {
        return RxFirebaseDatabase
                .observeSingleValueEvent(getFireBaseUserReference().orderByChild(User.UID).equalTo(uid));
    }

    @Override
    public Observable<User> add(final User user) {
        return  RxFirebaseAuth
                .createUserWithEmailAndPassword(FirebaseAuth.getInstance(), user.getEmail(), Md5.generateMD5(user.getPassword()))
                .flatMap(new Func1<AuthResult, Observable<AuthResult>>() {
                    @Override
                    public Observable<AuthResult> call(AuthResult authResult) {
                        return RxFirebaseAuth.signInWithEmailAndPassword(FirebaseAuth.getInstance(),
                                user.getEmail(), Md5.generateMD5(user.getPassword()));
                    }
                })
                .flatMap(new Func1<AuthResult, Observable<DataSnapshot>>() {
                    @Override
                    public Observable<DataSnapshot> call(AuthResult authResult) {
                        String uid = authResult.getUser().getUid();
                        user.setUid(uid);
                        user.setPassword(null);
                        getFireBaseUserReference().push().setValue(user);
                        return getRxFireBaseUserByUid(uid);
                    }
                })
                .flatMap(getCurrentUserData());
    }

    @Override
    public Observable<Boolean> delete(User user) {
        return null;
    }
    /**
     * This method update in FireBase the user information on the database.
     *
     * @param user The user to update
     * @return Observable<User>
     */
    @Override
    public Observable<User> update(final User user) {
        return RxFirebaseAuth.observeAuthState(FirebaseAuth.getInstance())
                .flatMap(new Func1<FirebaseUser, Observable<User>>() {
                    @Override
                    public Observable<User> call(FirebaseUser firebaseUser) {
                        getFireBaseUserReference().child(firebaseUser.getUid()).child(User.NAME).setValue(user.getName());
                        getFireBaseUserReference().child(firebaseUser.getUid()).child(User.LAST_NAME).setValue(user.getLastName());
                        getFireBaseUserReference().child(firebaseUser.getUid()).child(User.BIRTHDAY).setValue(user.getBirthday());
                        return Observable.just(user);
                    }
                });
    }

    @Override
    public Observable<List<User>> findAll() {
        return null;
    }

    /**
     * Method get the Rx function to generate credential form FireBase
     *
     * @param token Facebook credentials to generate the credential to fireBase
     * @return Observable<User>
     */
    @Override
    public Observable<User> loginFacebook(LoginResult token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getAccessToken().getToken());
        return loginWithCredentials(credential);
    }

    /**
     * Method get the Rx function to generate credential form FireBase
     *
     * @param token,secret Twitter credentials to generate the credential to fireBase
     * @return Observable<User>
     */
    @Override
    public Observable<User> loginTwitter(String token, String secret) {
        AuthCredential credential = TwitterAuthProvider.getCredential(token, secret);
        return loginWithCredentials(credential);
    }

    /**
     * Method get the Rx function to generate credential form FireBase
     *
     * @param idToken Google credentials to generate the credential to fireBase
     * @return Observable<User>
     */
    @Override
    public Observable<User> loginGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        return loginWithCredentials(credential);
    }

    /**
     * Method get the Rx function to transform the FireBase data base
     *
     * @param credential General credentials to FireBase to login an user with social networks
     * @return Observable<User>
     */
    private Observable<User> loginWithCredentials(AuthCredential credential) {
        return RxFirebaseAuth.signInWithCredential(FirebaseAuth.getInstance(), credential)
                .flatMap(new Func1<AuthResult, Observable<User>>() {
                    @Override
                    public Observable<User> call(AuthResult authResult) {
                        getFireBaseUserReference().child(authResult.getUser().getUid()).child(User.UID)
                                .setValue(authResult.getUser().getUid());
                        getFireBaseUserReference().child(authResult.getUser().getUid()).child(User.EMAIL)
                                .setValue(authResult.getUser().getEmail());
                        return Observable.just(new User());
                    }
                });
    }

    /**
     * Method get the Rx function to transform the FireBase data base
     *
     * @return Func1 DataSnapshot, Observable<User>
     */
    private Func1<DataSnapshot, Observable<User>> getCurrentUserData() {
        return new Func1<DataSnapshot, Observable<User>>() {
            @Override
            public Observable<User> call(DataSnapshot dataSnapshot) {
                return Observable.just(dataSnapshot.getChildren().iterator().next().getValue(User.class));
            }
        };
    }

    /**
     * Method get the Rx function to manager the user by UID
     *
     * @return Func1 FirebaseUser, Observable<DataSnapshot>
     */
    private Func1<FirebaseUser, Observable<DataSnapshot>> getCurrentUserAuth() {
        return new Func1<FirebaseUser, Observable<DataSnapshot>>() {
            @Override
            public Observable<DataSnapshot> call(FirebaseUser firebaseUser) {
                return RxFirebaseDatabase
                        .observeSingleValueEvent(getFireBaseUserReference().orderByChild(User.UID)
                                .equalTo(firebaseUser.getUid()));
            }
        };
    }

    /**
     * Method get the preference to user on FireBase
     *
     * @return DatabaseReference the preference to user child on User in FireBase
     */
    private DatabaseReference getFireBaseUserReference() {
        return FirebaseDatabase.getInstance().getReference().child(User.USERS_TABLE);
    }
}

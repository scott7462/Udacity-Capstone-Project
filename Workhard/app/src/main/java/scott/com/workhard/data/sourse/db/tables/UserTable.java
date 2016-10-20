package scott.com.workhard.data.sourse.db.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rx.Observable;
import scott.com.workhard.entities.Token;
import scott.com.workhard.entities.User;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/4/16.
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


public class UserTable extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String lastName;
    private String email;
    private long birthday;
    private TokenTable token;

    public UserTable() {
    }

    public UserTable(User user) {
        setId(user.getId());
        setBirthday(user.getBirthday());
        setEmail(user.getEmail());
        setName(user.getName());
        setToken(new TokenTable(user.getToken().getAccessToken()));
        setLastName(user.getLastName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public TokenTable getToken() {
        return token;
    }

    public void setToken(TokenTable token) {
        this.token = token;
    }

    public Observable<User> transformToUser() {
        return Observable.just(new User().withId(getId())
                .withName(getName())
                .withLastName(getLastName())
                .withEmail(getEmail())
                .withBirthday(getBirthday())
                .withToken(new Token(getToken().getAccessToken())));
    }
}
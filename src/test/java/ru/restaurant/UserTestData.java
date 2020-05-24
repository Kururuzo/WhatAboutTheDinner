package ru.restaurant;

import ru.restaurant.model.Role;
import ru.restaurant.model.User;
import ru.restaurant.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "password", "votes");
//    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "roles", "registered", "password", "votes");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password",  Role.USER );
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin",  Role.USER, Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER)
                , Collections.singleton(VoteTestData.VOTE_1)
        );
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
//        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}

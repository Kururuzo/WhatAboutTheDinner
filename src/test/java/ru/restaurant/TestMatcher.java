package ru.restaurant;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.restaurant.web.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class TestMatcher<T> {
    private final Class<T> clazz;
    private final boolean usingEquals;
    private final String[] fieldsToIgnore;

    private TestMatcher(Class<T> clazz, boolean usingEquals, String...fieldsToIgnore) {
        this.clazz = clazz;
        this.usingEquals = usingEquals;
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public static <T> TestMatcher<T> usingEquals(Class<T> clazz) {
        return new TestMatcher<>(clazz, true);
    }

    public static <T> TestMatcher<T> usingFieldsComparator(Class<T> clazz, String... fieldsToIgnore) {
        return new TestMatcher<>(clazz, false, fieldsToIgnore);
    }

    public void assertMatch(T actual, T expected) {
        if (usingEquals) {
            assertThat(actual).isEqualTo(expected);
        } else {
            assertThat(actual).isEqualToIgnoringGivenFields(expected, fieldsToIgnore);
        }
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        if (usingEquals) {
            assertThat(actual).isEqualTo(expected);
        } else {
            assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }
    }

    //JSON
    public ResultMatcher contentJson(T expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, clazz), expected);
    }

    @SafeVarargs
    public final ResultMatcher contentJson(T... expected) {
        return contentJson(List.of(expected));
    }

    public ResultMatcher contentJson(Iterable<T> expected) {
        return new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                assertMatch(TestUtil.readListFromJsonMvcResult(result, clazz), expected);
            }
        };
    }

}

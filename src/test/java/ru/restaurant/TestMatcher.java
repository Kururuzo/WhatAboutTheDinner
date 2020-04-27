package ru.restaurant;

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

}

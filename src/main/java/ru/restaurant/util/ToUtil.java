package ru.restaurant.util;

import ru.restaurant.model.AbstractBaseEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ToUtil {
    private ToUtil() {
    }

//    public static List<DishTo> tosFromDishes (List<Dish> dishes) {
//        return dishes.stream().map(DishTo::new).collect(Collectors.toList());
//    }
//
//    public static List<DishToForOffer> tosForOfferFromDishes (List<Dish> dishes) {
//        return dishes.stream().map(DishToForOffer::new).collect(Collectors.toList());
//    }

    public static <T> List<T> tosFromModel(List<? extends AbstractBaseEntity> list, Class<T> clazz) {
        return list.stream().map(o -> {
            try {
                Constructor<T> ctr = clazz.getDeclaredConstructor(list.get(0).getClass());
                return (T) ctr.newInstance(o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                //IGNORE
            }
            return null;
        })
                .collect(Collectors.toList());
    }

    public static <T> Set<T> tosFromModelSet(Set<? extends AbstractBaseEntity> set, Class<T> clazz) {
        return set.stream().map(o -> {
            try {
                Constructor<T> ctr = clazz.getDeclaredConstructor(set.iterator().next().getClass());
                return (T) ctr.newInstance(o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                //IGNORE
            }
            return null;
        })
                .collect(Collectors.toSet());
    }

}

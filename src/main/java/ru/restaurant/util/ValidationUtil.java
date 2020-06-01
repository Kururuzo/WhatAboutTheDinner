package ru.restaurant.util;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.restaurant.HasId;
import ru.restaurant.util.exception.DateTimeExpiredException;
import ru.restaurant.util.exception.IllegalRequestDataException;
import ru.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static void checkIsEmpty(Collection<?> collection, String arg) {
        if (collection.isEmpty()) {
            throw new NotFoundException(arg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void assureIdConsistentBriefly(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }


    //Check date and time for Vote
    public static void checkIsTimeExpired(LocalTime actualTime, LocalTime expectTime) {
        if (actualTime.isAfter(expectTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            throw new DateTimeExpiredException(actualTime.format(formatter) +
                    " is too late, you should vote before " + expectTime.format(formatter));
        }
    }

    public static void checkIsDateExpired(LocalDate actualDate, LocalDate expectDate) {
        if (!actualDate.equals(expectDate)) {
            throw new DateTimeExpiredException(actualDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                    " is not today, you can't revote");
//            throw new DateTimeExpiredException("Voting date is expired, you can't revote");
        }
    }
}

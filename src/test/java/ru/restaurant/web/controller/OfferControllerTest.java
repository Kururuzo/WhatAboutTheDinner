package ru.restaurant.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant.to.OfferTo;
import ru.restaurant.to.RestaurantTo;
import ru.restaurant.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.restaurant.OfferTestData.OFFER_1;
import static ru.restaurant.OfferTestData.OFFER_MATCHER;
import static ru.restaurant.RestaurantTestData.REST_TO_MATCHER;
import static ru.restaurant.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.restaurant.web.TestUtil.readFromJson;

class OfferControllerTest extends AbstractControllerTest {

    private static final String REST_URL = OfferController.REST_URL + '/';

    @Test
    void getOfferByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "?date=2020-04-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(OFFER_MATCHER.contentJson(OFFER_1))
                .andDo(print());
    }

    @Test
    void getOfferWithEmptyDate() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        OfferTo offerTo = readFromJson(action, OfferTo.class);
        OFFER_MATCHER.assertMatch(offerTo, new OfferTo(LocalDate.now(), List.of(new RestaurantTo())));

    }

    @Test
    void getOfferWithNullDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "?date=null"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(errorType(VALIDATION_ERROR));
    }

}
package com.uno.getinline.repository;

import com.querydsl.core.BooleanBuilder;
import com.uno.getinline.constant.EventStatus;
import com.uno.getinline.constant.PlaceType;
import com.uno.getinline.domain.Event;
import com.uno.getinline.domain.Place;
import com.uno.getinline.dto.EventViewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("DB - 이벤트")
@DataJpaTest
class EventRepositoryTest {

    private final EventRepository eventRepository;

    public EventRepositoryTest(@Autowired EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Test
    void givenSearchParams_whenFindingViewResponse_thenReturnsEventViewResponsePage(){
        //Given


        //When
        Page<EventViewResponse> eventPage = eventRepository.findEventViewPageSearchParams(
                "배드민턴",
                "운동1",
                EventStatus.OPENED,
                LocalDateTime.of(2021,1,1,0,0,0),
                LocalDateTime.of(2021,1,2,0,0,0),
                PageRequest.of(0,5)

        );

        //Then
        assertThat(eventPage.getTotalPages()).isEqualTo(1);
        assertThat(eventPage.getNumberOfElements()).isEqualTo(1);
        assertThat(eventPage.getContent().get(0))
                .hasFieldOrPropertyWithValue("placeName","서울 배드민턴장")
                .hasFieldOrPropertyWithValue("eventName","운동1")
                .hasFieldOrPropertyWithValue("eventStatus",EventStatus.OPENED)
                .hasFieldOrPropertyWithValue("eventStartDatetime",LocalDateTime.of(2021,1,1,9,0,0))
                .hasFieldOrPropertyWithValue("eventEndDatetime",LocalDateTime.of(2021,1,2,12,0,0));


    }


}
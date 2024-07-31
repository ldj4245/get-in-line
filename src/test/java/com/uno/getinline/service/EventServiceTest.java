//package com.uno.getinline.service;
//
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.types.Predicate;
//import com.uno.getinline.constant.ErrorCode;
//import com.uno.getinline.constant.EventStatus;
//import com.uno.getinline.constant.PlaceType;
//import com.uno.getinline.domain.Event;
//import com.uno.getinline.domain.Place;
//import com.uno.getinline.dto.EventDto;
//import com.uno.getinline.dto.EventViewResponse;
//import com.uno.getinline.exception.GeneralException;
//import com.uno.getinline.repository.EventRepository;
//import com.uno.getinline.repository.PlaceRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import javax.persistence.EntityNotFoundException;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.*;
//
//@DisplayName("비즈니스 로직 - 이벤트")
//@ExtendWith(MockitoExtension.class)
//class EventServiceTest {
//
//    @InjectMocks private EventService sut;
//    @Mock private EventRepository eventRepository;
//    @Mock private PlaceRepository placeRepository;
//
//
//    @DisplayName("이벤트를 검색하면, 결과를 출력하여 보여준다.")
//    @Test
//    void givenNothing_whenSearchingEvents_thenReturnsEntireEventList(){
//        //Given
//        given(eventRepository.findAll(any(Predicate.class)))
//                .willReturn(List.of(
//                        createEvent("오전 운동",true),
//                        createEvent("오후 운동",false)
//                ));
//        //When
//        List<EventDto> list = sut.getEvents(new BooleanBuilder());
//
//
//        //Then
//        assertThat(list).hasSize(2);
//        then(eventRepository).should().findAll(any(Predicate.class));
//
//    }
//
//
//    @DisplayName("이벤트를 검색하는데 에러가 발생한 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
//    @Test
//    void givenDataRelatedException_whenSearchingEvents_thenThrowsGeneralException(){
//        //Given
//        RuntimeException e = new RuntimeException("This is test.");
//        given(eventRepository.findAll(any(Predicate.class))).willThrow(e);
//
//        //When
//        Throwable thrown = catchThrowable(() -> sut.getEvents(new BooleanBuilder()));
//
//        //Then
//        assertThat(thrown)
//                .isInstanceOf(GeneralException.class)
//                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
//        then(eventRepository).should().findAll(any(Predicate.class));
//    }
//
//    @DisplayName("이벤트 뷰 데이터를 검색하면, 페이징된 결과를 출력하여 보여준다.")
//    @Test
//    void givenNothing_whenSearchingEventViewResponse_thenReturnsEventViewResponsePage(){
//        //Given
//        given(eventRepository.findEventViewPageSearchParams(null,null,null,null,null, PageRequest.ofSize(10)))
//                .willReturn(new PageImpl<>(List.of(
//                        EventViewResponse.from(EventDto.of(createEvent("오전 운동",true))),
//                        EventViewResponse.from(EventDto.of(createEvent("오후 운동",false)))
//                )));
//
//
//        //When
//        Page<EventViewResponse> list = sut.getEventViewResponse(null,null,null,null,null,PageRequest.ofSize(10));
//
//
//        //Then
//        assertThat(list).hasSize(2);
//        then(eventRepository).should().findEventViewPageSearchParams(null,null,null,null,null,PageRequest.ofSize(10));
//    }
//
//    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
//    @Test
//    void givenEventId_whenSearcingExistingEvent_thenReturnsEvent(){
//        //Given
//        long eventId = 1L;
//        Event event = createEvent("오전 운동",true);
//        given(eventRepository.findById(eventId)).willReturn(Optional.of(event));
//
//
//        //WHen
//        Optional<EventDto> result = sut.getEvent(eventId);
//
//        //Then
//        assertThat(result).hasValue(EventDto.of(event));
//        then(eventRepository).should().findById(eventId);
//
//    }
//
//    @DisplayName("이벤트 ID로 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
//    @Test
//    void givenEventId_whenSearchingNonexistentEvent_thenReturnsEmptyOptional(){
//        //Given
//        long eventId = 2L;
//        given(eventRepository.findById(eventId)).willReturn(Optional.empty());
//
//        //when
//        Optional<EventDto> result = sut.getEvent(eventId);
//
//        //Then
//        assertThat(result).isEmpty();
//        then(eventRepository).should().findById(eventId);
//    }
//
//
//
//    @DisplayName("이벤트 ID로 이벤트를 조회하는데 데이터 관련 에러가 발생한 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
//    @Test
//    void givenDataRelatedException_whenSearchingEvent_thenThrowsGeneralException(){
//        //Given
//        RuntimeException e = new RuntimeException("This is test.");
//        given(eventRepository.findById(any())).willThrow(e);
//
//        Throwable thrown = catchThrowable(() -> sut.getEvent(null));
//
//        //Then
//        assertThat(thrown)
//                .isInstanceOf(GeneralException.class)
//                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
//        then(eventRepository).should().findById(any());
//
//    }
//    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true로 보여준다.")
//    @Test
//    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue(){
//        //Given
//        EventDto eventDto = EventDto.of(createEvent("오후 운동",false));
//        given(placeRepository.getById(eventDto.placeDto().id())).willReturn(createPlace());
//        given(eventRepository.save(any(Event.class))).willReturn(any());
//
//
//        //when
//        boolean result = sut.createEvent(eventDto);
//
//        //Then
//        assertThat(result).isTrue();
//        then(placeRepository).should().getById(eventDto.placeDto().id());
//        then(eventRepository).should().save(any(Event.class));
//
//    }
//
//    @DisplayName("이벤트 정보를 주지 않으면, 생성 중단하고 결고를 false 로 보여준다")
//    @Test
//    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse(){
//        //Given
//
//        //when
//        boolean result = sut.createEvent(null);
//
//        //Then
//        assertThat(result).isFalse();
//        then(placeRepository).shouldHaveNoInteractions();
//        then(eventRepository).shouldHaveNoInteractions();
//    }
//
//    @DisplayName("이벤트 생성 중 장소 정보가 틀리거나 없으면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
//    @Test
//    void givenWrongPlaceId_whenCreating_thenThrowsGeneralException(){
//        //Given
//        EventDto eventDto = EventDto.of(createEvent("오후 운동",false));
//        given(placeRepository.getById(eventDto.placeDto().id())).willReturn(null);
//        given(eventRepository.save(any(Event.class))).willThrow(EntityNotFoundException.class);
//
//        //When
//        Throwable thrown = catchThrowable(() -> sut.createEvent(eventDto));
//
//
//        //Then
//        assertThat(thrown)
//                .isInstanceOf(GeneralException.class)
//                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
//        then(placeRepository).should().getById(eventDto.placeDto().id());
//        then(eventRepository).should().save(any(Event.class));
//    }
//
//    @DisplayName("이벤트 생성 중 데이터 예외가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
//    @Test
//    void givenRelatedException_whenCreatingEvent_thenReturnsGeneralException(){
//
//        //Given
//        EventDto eventDto = EventDto.of(ceateEvent("오후 운동",false));
//        RuntimeException e = new RuntimeException("This is test.");
//        given(placeRepository.getById(eventDto.placeDto().id())).willReturn(null);
//        given(eventRepository.save(any(Event.class))).willThrow(e);
//
//
//        //when
//        Throwable thrown = catchThrowable(() -> sut.createEvent(eventDto));
//
//        //Then
//        assertThat(thrown)
//                .isInstanceOf(GeneralException.class)
//                .hasMessage(ErrorCode.DATA_ACCESS_ERROR.getMessage(e));
//        then(placeRepository).should().getById(eventDto.placeDto().id());
//        then(eventRepository).should().save(any(Event.class));
//
//    }
//
//    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
//    @Test
//    void givenEventIdAndItsINfo_whenModifying_thenModifiesEventAndReturnsTrue(){
//        //Given
//        long eventId = 1L;
//        Event originalEvent = createEvent("오후 운동",false);
//        Event changedEvent = createEvent("오전 운동",true);
//        given(eventRepository.findById(eventId)).willReturn(Optional.of(originalEvent));
//        given(eventRepository.save(changedEvent)).willReturn(changedEvent);
//
//
//        //when
//        boolean result = sut.modifyEvent(eventId,EventDto.of(changedEvent));
//
//        //Then
//        assertThat(result).isTrue();
//        assertThat(originalEvent.getEventName()).isEqu
//    }
//
//
//
//
//
//
//
//
//
//
//    private Event createEvent(String eventName, boolean isMorning){
//        return createEvent(1L,eventName,isMorning);
//    }
//
//    private Event ceateEvent(Long eventId, String eventName, boolean isMorning){
//        String hourStart = isMorning ? "09":"13";
//        String hourEnd = isMorning ? "12" : "16";
//
//
//        return createEvent(
//                eventId,
//                1L,
//                eventName,
//                EventStatus.OPENED,
//                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
//                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
//        );
//    }
//
//
//    private Event createEvent(
//            Long id,
//            Long placeId,
//            String eventName,
//            EventStatus eventStatus,
//            LocalDateTime eventStartDateTime,
//            LocalDateTime eventEndDateTime
//    ){
//        Event event = Event.of(
//                createPlace(placeId),
//                eventName,
//                eventStatus,
//                eventStartDateTime,
//                eventEndDateTime,
//                0,
//                24,
//                "마스크 꼭 착용하세요"
//        );
//        ReflectionTestUtils.setField(event,"id",id);
//        return event;
//    }
//
//
//    private Place createPlace(Long placeId){
//        return
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//}
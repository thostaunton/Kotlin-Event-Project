package services

import beans.Event
import beans.Note
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not


class EventServiceTest {

	@Test
	fun resolveEventsTest_CorrectValues() {
		// given
		val eventService = EventService()
		val localNote = Note(1, "A")
		val remoteNote = Note(2, "B")
		val localEvent = Event("Name 1", arrayListOf(localNote))
		val remoteEvent = Event("Name 1", arrayListOf(remoteNote))

		// when
		val joinedEvent = eventService.resolveEvents(remoteEvent, localEvent)

		// then
		assertThat(joinedEvent.getName(), equalTo("Name 1"))
		assertThat(joinedEvent.getNotes()!!.get(0).getId(), equalTo(1))
		assertThat(joinedEvent.getNotes()!!.get(0).getText(), equalTo("A"))
		assertThat(joinedEvent.getNotes()!!.get(1).getId(), equalTo(2))
		assertThat(joinedEvent.getNotes()!!.get(1).getText(), equalTo("B"))
		assertThat(joinedEvent.getNotes()!!.size, equalTo(2))
	}

	@Test // To test the id sort
	fun resolveEventsTest_CorrectValues_DifferentOrder() {
		// given
		val eventService = EventService()
		val remoteNote = Note(1, "A")
		val localNote = Note(2, "B")
		val localEvent = Event("Name 1", arrayListOf(localNote))
		val remoteEvent = Event("Name 1", arrayListOf(remoteNote))

		// when
		val joinedEvent = eventService.resolveEvents(remoteEvent, localEvent)

		// then
		assertThat(joinedEvent.getName(), equalTo("Name 1"))
		assertThat(joinedEvent.getNotes()!!.get(0).getId(), equalTo(1))
		assertThat(joinedEvent.getNotes()!!.get(0).getText(), equalTo("A"))
		assertThat(joinedEvent.getNotes()!!.get(1).getId(), equalTo(2))
		assertThat(joinedEvent.getNotes()!!.get(1).getText(), equalTo("B"))
		assertThat(joinedEvent.getNotes()!!.size, equalTo(2))
	}

	@Test
	fun resolveEventsTest_MergeTwoDifferentEvents() {
		// given
		val eventService = EventService()
		val localNote = Note(1, "A")
		val remoteNote = Note(1, "B")
		val localEvent = Event("Name 1", arrayListOf(localNote))
		val remoteEvent = Event("Name 2", arrayListOf(remoteNote))

		// when
		val joinedEvent = eventService.resolveEvents(remoteEvent, localEvent)

		// then
		assertThat(joinedEvent.getName(), equalTo("Name 1 / Name 2"))
		assertThat(joinedEvent.getNotes()!!.get(0).getId(), equalTo(1))
		assertThat(joinedEvent.getNotes()!!.get(0).getText(), equalTo("A / B"))
		assertThat(joinedEvent.getNotes()!!.size, equalTo(1))
	}

	@Test
	fun resolveEventsTest_MergeTwoNotes_NoDuplication() {
		// given
		val eventService = EventService()
		val localNote = Note(1, "A")
		val remoteNote = Note(1, "A")
		val localEvent = Event("Name 1", arrayListOf(localNote))
		val remoteEvent = Event("Name 1", arrayListOf(remoteNote))

		// when
		val joinedEvent = eventService.resolveEvents(remoteEvent, localEvent)

		// then
		assertThat(joinedEvent.getName(), equalTo("Name 1"))
		assertThat(joinedEvent.getNotes()!!.get(0).getId(), equalTo(1))
		assertThat(joinedEvent.getNotes()!!.get(0).getText(), equalTo("A"))
		assertThat(joinedEvent.getNotes()!!.size, equalTo(1))
	}

	@Test
	fun resolveEventsTest_WrongValues() {
		// given
		val eventService = EventService()
		val localNote = Note(1, "A")
		val remoteNote = Note(2, "B")
		val localEvent = Event("Name 1", arrayListOf(localNote))
		val remoteEvent = Event("Name 2", arrayListOf(remoteNote))

		// when
		val joinedEvent = eventService.resolveEvents(remoteEvent, localEvent)

		// then
		assertThat(joinedEvent.getName(), not(equalTo("Name 2 / Name 1")))
		assertThat(joinedEvent.getNotes()!!.get(0).getId(), not(equalTo(2)))
		assertThat(joinedEvent.getNotes()!!.get(0).getText(), not(equalTo("B")))
		assertThat(joinedEvent.getNotes()!!.get(1).getId(), not(equalTo(1)))
		assertThat(joinedEvent.getNotes()!!.get(1).getText(), not(equalTo("A")))
		assertThat(joinedEvent.getNotes()!!.size, not(equalTo(1)))
	}
}

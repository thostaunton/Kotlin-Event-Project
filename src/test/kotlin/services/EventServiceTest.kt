package services

import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
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
		assertThat(joinedEvent.name, equalTo("Name 1"))
		assertThat(joinedEvent.notes[0].id, equalTo(1))
		assertThat(joinedEvent.notes[0].text, equalTo("A"))
		assertThat(joinedEvent.notes[1].id, equalTo(2))
		assertThat(joinedEvent.notes[1].text, equalTo("B"))
		assertThat(joinedEvent.notes.size, equalTo(2))
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
		assertThat(joinedEvent.name, equalTo("Name 1"))
		assertThat(joinedEvent.notes[0].id, equalTo(1))
		assertThat(joinedEvent.notes[0].text, equalTo("A"))
		assertThat(joinedEvent.notes[1].id, equalTo(2))
		assertThat(joinedEvent.notes[1].text, equalTo("B"))
		assertThat(joinedEvent.notes.size, equalTo(2))
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
		assertThat(joinedEvent.name, equalTo("Name 1 / Name 2"))
		assertThat(joinedEvent.notes[0].id, equalTo(1))
		assertThat(joinedEvent.notes[0].text, equalTo("A / B"))
		assertThat(joinedEvent.notes.size, equalTo(1))
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
		assertThat(joinedEvent.name, equalTo("Name 1"))
		assertThat(joinedEvent.notes[0].id, equalTo(1))
		assertThat(joinedEvent.notes[0].text, equalTo("A"))
		assertThat(joinedEvent.notes.size, equalTo(1))
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
		assertThat(joinedEvent.name, not(equalTo("Name 2 / Name 1")))
		assertThat(joinedEvent.notes[0].id, not(equalTo(2)))
		assertThat(joinedEvent.notes[0].text, not(equalTo("B")))
		assertThat(joinedEvent.notes[1].id, not(equalTo(1)))
		assertThat(joinedEvent.notes[1].text, not(equalTo("A")))
		assertThat(joinedEvent.notes.size, not(equalTo(1)))
	}
}

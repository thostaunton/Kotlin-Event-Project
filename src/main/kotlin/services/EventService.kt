package services

import beans.Event
import beans.Note
import java.util.*
import java.util.stream.Collectors


class EventService() {

    fun resolveEvents(remote: Event, local: Event): Event {

        val remoteNotes = remote.getNotes()
        val localNotes = local.getNotes()
        val joinedEvent = Event("", arrayListOf())

        // join event names if they are different
        if(local.getName() != remote.getName()){
            joinedEvent.setName(local.getName().plus(" / ").plus(remote.getName()))
        }else{
            joinedEvent.setName(local.getName())
        }
        // loop through both sets of notes
        for (localNote in localNotes) {
            for (remoteNote in remoteNotes) {
                if (localNote.getId() == remoteNote.getId()) {
                    // if they share the same id and same text add the local note
                    if (localNote.getText() == remoteNote.getText()) {
                        joinedEvent.getNotes().add(localNote)
                    } else  // if they just share the same id merge text with a slash
                    {
                        val joinedNote =
                            Note(localNote.getId(), localNote.getText().plus(" / ").plus(remoteNote.getText()))
                        joinedEvent.getNotes().add(joinedNote)
                    }
                }else{
                    // add remaining notes to the joined event
                    joinedEvent.getNotes().add(remoteNote)
                    joinedEvent.getNotes().add(localNote)
                }
            }
        }

        // sort notes by id
        val sortedNotes: List<Note> = joinedEvent.getNotes().stream()
           .sorted(Comparator.comparingLong(Note::getId)).collect(Collectors.toList())

        joinedEvent.setNotes(ArrayList(sortedNotes))

        return joinedEvent;
    }
}
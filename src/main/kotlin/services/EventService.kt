package services

import java.util.*

data class Event(val name: String, val notes: ArrayList<Note>)

data class Note(val id: Long, val text: String)
class EventService() {

    fun resolveEvents(remote: Event, local: Event): Event {

        val remoteNotes = remote.notes
        val localNotes = local.notes
        var joinedName = local.name

        // join event names if they are different
        if(local.name != remote.name){
            joinedName = local.name.plus(" / ").plus(remote.name)
        }

        val joinedNotes = ArrayList<Note>()
        // loop through both sets of notes
        for (localNote in localNotes) {
            for (remoteNote in remoteNotes) {
                if (localNote.id == remoteNote.id) {
                    // if they share the same id and same text add the local note
                    if (localNote.text == remoteNote.text) {
                        joinedNotes.add(localNote)
                    } else { // if they just share the same id merge text with a slash
                        val joinedNote =
                            Note(localNote.id, localNote.text.plus(" / ").plus(remoteNote.text))
                        joinedNotes.add(joinedNote)
                    }
                }else{
                    // add remaining notes to the joined event
                    joinedNotes.add(remoteNote)
                    joinedNotes.add(localNote)
                }
            }
        }

        // sort notes by id
        var sortedNotes = joinedNotes.sortedWith(compareBy(Note::id))

        return Event(joinedName, ArrayList(sortedNotes))
    }
}
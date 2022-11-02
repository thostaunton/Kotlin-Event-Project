package beans


class Event(name: String, notes: ArrayList<Note>) {

    private var name: String = name

    private var notes = arrayListOf<Note>()

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getNotes(): ArrayList<Note> {
        return notes
    }

    fun setNotes(notes: ArrayList<Note>) {
        this.notes = notes
    }
}
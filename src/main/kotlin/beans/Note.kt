package beans

class Note(id: Long, text: String) {
    private var id: Long = id

    private var text: String? = text

    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }
}
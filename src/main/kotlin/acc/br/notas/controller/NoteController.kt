package acc.br.notas.controller

import acc.br.notas.model.Note
import acc.br.notas.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("notes")
class NoteController {

    @Autowired
    lateinit var noteRepository: NoteRepository

    @GetMapping
    fun list(): List<Note> {
        return noteRepository.findAll().toList()
    }

    @PostMapping
    fun add(@RequestBody note: Note): Any {
        try {
            return noteRepository.save(note)
        } catch (e: CustomException) {
            return CustomException("This note is not present in database")
        }

    }

    @PostMapping("/manyNotes")
    fun batchAdd(@RequestBody note: List<Note>): Any {
        try {
            val addedNotes = ArrayList<Note>()
            note.forEach { n ->
                addedNotes.add(noteRepository.save(n))
            }
            return addedNotes
        } catch (e: CustomException) {
            return CustomException("This note is not present in database")
        }

    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody note: Note
    ): Any {
        try {
            var searchForNote = noteRepository.findById(id)
            return noteRepository.save(searchForNote.get().copy(title = note.title, description = note.description))
        } catch (e: CustomException) {
            return CustomException("This note is not present in database")
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Any {

        val searchElement = noteRepository.findById(id)

        try {
            return noteRepository.delete(searchElement.get())
        } catch (e: CustomException) {
            return CustomException("Insira o id")
        }
    }
}

class CustomException : Throwable {
    constructor() : super()
    constructor(message: String) : super("$message")
    constructor(message: String, cause: Throwable) : super("$message", cause)
    constructor(cause: Throwable) : super(cause)
}

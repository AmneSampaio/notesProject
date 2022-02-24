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
    fun add(@RequestBody note: Note): Note {
        return noteRepository.save(note)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody note: Note): Note {
        var searchForNote = noteRepository.findById(id)

        println("search ${searchForNote}, note ${note}")
        return when {
            !searchForNote.isPresent -> throw Exception("This note is not present in database")
            searchForNote.isPresent && searchForNote.equals(note) -> throw Exception("There are no new inputs")
            else -> noteRepository.save(searchForNote.get().copy(title = note.title, description = note.description))
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        var searchForNote = noteRepository.findById(id)

        return when{
            !searchForNote.isPresent -> throw Exception("This note is not present in database")
            else -> noteRepository.delete(searchForNote.get())
        }
    }
}

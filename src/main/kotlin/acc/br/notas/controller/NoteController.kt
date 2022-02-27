package acc.br.notas.controller

import acc.br.notas.model.Note
import acc.br.notas.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.http.HttpResponse
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

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

    @PostMapping("/manyNotes")
    fun batchAdd(@RequestBody note: List<Note>): List<Note> {

        val addedNotes = ArrayList<Note>()
        note.forEach { n ->
            addedNotes.add(noteRepository.save(n))
        }
        return addedNotes
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody note: Note
    ): Note {
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

        val resposta = "Elemento não encontrado"
        var deletingItem: Unit
        try {
            val searchForNote = noteRepository.findById(id).get()
            println("o que foi lido: $searchForNote")

           return if searchForNote.equals(null) ? noteRepository.delete(searchForNote) : resposta


                   (!) {
                   println("passou aqui: ${searchForNote}")
                   deletingItem =
           } else {
            println("passou aqui no catch: ${resposta}")
                ResponseEntity(resposta,HttpStatus.NOT_FOUND)
            }
    }
}


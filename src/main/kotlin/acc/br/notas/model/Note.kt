package acc.br.notas.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Note(
           @Id
           @GeneratedValue
           val id: Long = 0L,
           val title: String = "",
           val description: String = "")

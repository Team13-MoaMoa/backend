package sku.moamoa.domain.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sku.moamoa.domain.note.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}

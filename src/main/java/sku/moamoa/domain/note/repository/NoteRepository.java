package sku.moamoa.domain.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sku.moamoa.domain.note.entity.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select DISTINCT n from Note n where n.noteRoom like %:noteRoom%")
    List<Note> findNotesByNoteRoom(@Param("noteRoom") String noteRoom);

    List<Note> findAllByNoteRoom(String noteRoom);

}

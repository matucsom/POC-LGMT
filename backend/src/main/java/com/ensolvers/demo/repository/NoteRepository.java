package com.ensolvers.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensolvers.demo.model.Note;
import com.ensolvers.demo.model.User;

public interface NoteRepository extends JpaRepository<Note,Long> {

	List<Note> findByArchived(boolean archived);

	List<Note> findByUserId(Long userId);

	Optional<Note> findByTitle(String title);

	Optional<List<Note>> findByArchivedAndUser(boolean archived, User user);
}

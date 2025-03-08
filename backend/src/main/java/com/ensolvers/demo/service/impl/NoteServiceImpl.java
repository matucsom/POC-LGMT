package com.ensolvers.demo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ensolvers.demo.dto.NoteDTO;
import com.ensolvers.demo.exception.ResourceNotFoundException;
import com.ensolvers.demo.mapper.NoteMapper;
import com.ensolvers.demo.model.Note;
import com.ensolvers.demo.model.User;
import com.ensolvers.demo.repository.NoteRepository;
import com.ensolvers.demo.repository.UserRepository;
import com.ensolvers.demo.service.NoteService;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;

public class NoteServiceImpl implements NoteService {
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	public NoteDTO createNote(@NonNull NoteDTO noteDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteNote(@NonNull @PositiveOrZero Long id) {
       Note note= getNoteByIdOrThrowException(id);
       noteRepository.delete(note);
	}

	@Override
	public NoteDTO updateNote(@NonNull NoteDTO noteDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NoteDTO> findAllArchivedNotes(@NonNull @PositiveOrZero Long userid) {
		User user = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException());
		List<Note> archivedNotes = noteRepository.findByArchivedAndUser(true, user).orElse(Collections.emptyList());
		
		
		return archivedNotes.stream()
				.map(NoteMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<NoteDTO> findAllUnArchivedNotes(@NonNull @PositiveOrZero Long userid) {
		User user = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException());
		List<Note> unArchivedNotes = noteRepository.findByArchivedAndUser(false, user).orElse(Collections.emptyList());
		
		
		return unArchivedNotes.stream()
				.map(NoteMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<NoteDTO> findAllNotes(@NonNull @PositiveOrZero Long userid) {
		return null;
	}

	@Override
	public NoteDTO archiveNote(@NonNull @PositiveOrZero Long noteId) {
		Note note = getNoteByIdOrThrowException(noteId);
		note.setArchived(true);
		Note savedNote= noteRepository.save(note);
		return NoteMapper.toDTO(savedNote);
	}

	@Override
	public NoteDTO unArchivedNote(@NonNull @PositiveOrZero Long noteId) {
		Note note = getNoteByIdOrThrowException(noteId);
		note.setArchived(false);
		Note savedNote= noteRepository.save(note);
		return NoteMapper.toDTO(savedNote);
	}
	
	private Note getNoteByIdOrThrowException(Long noteId) {
		return noteRepository.findById(noteId).orElseThrow(()-> new ResourceNotFoundException());
	}

}

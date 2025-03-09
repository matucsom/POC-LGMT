package com.ensolvers.demo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensolvers.demo.dto.NoteDTO;
import com.ensolvers.demo.exception.ResourceNotFoundException;
import com.ensolvers.demo.mapper.NoteMapper;
import com.ensolvers.demo.model.Category;
import com.ensolvers.demo.model.Note;
import com.ensolvers.demo.model.User;
import com.ensolvers.demo.repository.CategoryRepository;
import com.ensolvers.demo.repository.NoteRepository;
import com.ensolvers.demo.repository.UserRepository;
import com.ensolvers.demo.service.NoteService;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public NoteDTO createNote(@NonNull NoteDTO noteDTO) {
		
		User user= userRepository.findById(noteDTO.getUserid()).get();
		Category category = categoryRepository.findById(noteDTO.getCategoryid()).get();
		Note note=  NoteMapper.toNewEntity(noteDTO, user, category);
		Note savedNote= noteRepository.save(note);
		
		return NoteMapper.toDTO(savedNote);
	}

	@Override
	public void deleteNote(@NonNull @PositiveOrZero Long id) {
       Note note= getNoteByIdOrThrowException(id);
       noteRepository.delete(note);
	}

	@Override
	public NoteDTO updateNote(@NonNull NoteDTO noteDTO) {
		Note note = getNoteByIdOrThrowException(noteDTO.getId()); 
		Category newPossiblecategory = categoryRepository.findById(noteDTO.getCategoryid()).orElseThrow(()-> new ResourceNotFoundException());

		note.setCategory(newPossiblecategory);
		if (noteDTO.getTitle() != null) note.setTitle(noteDTO.getTitle());
		if (noteDTO.getText() != null) note.setText(noteDTO.getText());
		 
		Note savedNote =  noteRepository.save(note);
		
		return NoteMapper.toDTO(savedNote);
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

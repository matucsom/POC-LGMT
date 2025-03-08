package com.ensolvers.demo.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.ensolvers.demo.dto.NoteDTO;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;

@Validated
public interface NoteService {
	
	NoteDTO createNote(@NonNull NoteDTO noteDTO);
	
	void deleteNote(@NonNull @PositiveOrZero Long id);
	
	NoteDTO updateNote(@NonNull NoteDTO noteDTO);
	
	List<NoteDTO> findAllArchivedNotes(@NonNull @PositiveOrZero Long id);
	
	List<NoteDTO> findAllUnArchivedNotes(@NonNull @PositiveOrZero Long id);
	
	List<NoteDTO> findAllNotes(@NonNull @PositiveOrZero Long id);
	
    NoteDTO archiveNote(@NonNull @PositiveOrZero Long id);
    
    NoteDTO unArchivedNote(@NonNull @PositiveOrZero Long id);

}

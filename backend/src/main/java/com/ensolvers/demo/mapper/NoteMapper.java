package com.ensolvers.demo.mapper;

import com.ensolvers.demo.dto.NoteDTO;
import com.ensolvers.demo.model.Category;
import com.ensolvers.demo.model.Note;
import com.ensolvers.demo.model.User;

public class NoteMapper {

	public static NoteDTO toDTO(Note note) {
		return NoteDTO.builder()
				.id(note.getId())
				.title(note.getTitle())
				.text(note.getText())
				.userid(note.getUser().getId())
				.categoryid(note.getCategory().getId())
				.build();
	}
	
	public static Note toNewEntity(NoteDTO noteDTO,User user, Category category) {
		return Note.builder()
				//.id(noteDTO.getId())
				.title(noteDTO.getTitle())
				.text(noteDTO.getText())
				.user(user)
				.category(category)
				.archived(false)
				.build();
	}
	
	public static Note toEntity(NoteDTO noteDTO,User user, Category category) {
		return Note.builder()
				.id(noteDTO.getId())
				.title(noteDTO.getTitle())
				.text(noteDTO.getText())
				.user(user)
				.category(category)
			  //.archived(noteDTO.getArchived) TODO
				.build();
	}
	
}

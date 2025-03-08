package com.ensolvers.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensolvers.demo.dto.NoteDTO;
import com.ensolvers.demo.service.NoteService;


@RestController
@RequestMapping("/notes")
public class NoteController {
	
	@Autowired 
	NoteService noteservice;
	
	@GetMapping("/{userid}/all/archived")
	public ResponseEntity<List<NoteDTO>> findAllArchivedNotes(@PathVariable Long userid) {

		List<NoteDTO> list = noteservice.findAllArchivedNotes(userid);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{userid}/all/unarchived")
	public ResponseEntity<List<NoteDTO>> findAllUnarchivedNotes(@PathVariable Long userid) {

		List<NoteDTO> list = noteservice.findAllUnArchivedNotes(userid);
		return ResponseEntity.ok().body(list);
	}
	
	@DeleteMapping("/{noteid}")
	public ResponseEntity<String> deleteNote (@PathVariable Long noteid){
		noteservice.deleteNote(noteid);
		return ResponseEntity.noContent().build();
	}

	
	@PatchMapping("/{noteid}/archive")
	public ResponseEntity<NoteDTO> archiveNote (@PathVariable Long noteid){
		NoteDTO noteDTO =noteservice.archiveNote(noteid);
		return ResponseEntity.ok().body(noteDTO);
	}
	
	@PatchMapping("/{noteid}/unarchive")
	public ResponseEntity<NoteDTO> unarchiveNote (@PathVariable Long noteid){
		NoteDTO noteDTO =noteservice.unArchivedNote(noteid);
		return ResponseEntity.ok().body(noteDTO);
	}
}

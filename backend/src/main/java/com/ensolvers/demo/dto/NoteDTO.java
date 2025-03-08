package com.ensolvers.demo.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {
	
	@NotNull
	private Long id;
	
	@NotNull(message= "title cannot be null")
	private String title;
	
	@NotNull(message= "text cannot be null")
	private String text;
	
	@NotNull
	private Long userid;
	
	private Long categoryid;

}

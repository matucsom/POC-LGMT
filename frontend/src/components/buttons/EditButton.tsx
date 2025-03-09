import React, { useState } from "react";
import { IconButton } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import {  iNote, iNoteComplete } from "../../type";
import NoteMenu from "../NoteMenu";
import { useNoteContext } from "../../context/noteContext";
import { useUserContext } from "../../context/userContext";
import { mapToNoteComplete } from "../../mappers/noteMapper";

interface EditButtonProps {
    note: iNote;
  }
  
  const EditButton: React.FC<EditButtonProps> = ({ note }) => {
    const [open, setOpen] = useState(false);
    const { updateExistingNote } = useNoteContext(); 
    const { user } = useUserContext(); 
  
    const completeNote: iNoteComplete = mapToNoteComplete(note, user.id);
  
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
  
    return (
      <>
        <IconButton onClick={handleOpen}>
          <EditIcon />
        </IconButton>
        <NoteMenu open={open} handleClose={handleClose} note={completeNote} onSave={updateExistingNote} />
      </>
    );
  };
  
  export default EditButton;

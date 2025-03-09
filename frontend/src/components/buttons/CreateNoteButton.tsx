import React, { useState } from "react";
import { Button } from "@mui/material";
import {  iNote, iNoteComplete } from "../../type";
import NoteMenu from "../NoteMenu";
import { useNoteContext } from "../../context/noteContext";
import { useUserContext } from "../../context/userContext";
import { mapToNoteComplete } from "../../mappers/noteMapper";

  const CreateButton: React.FC = () => {
    const [open, setOpen] = useState(false);
    const { createNewNote } = useNoteContext(); 
    const { user } = useUserContext(); 
  
    const emptyNote: iNote = {
        id: 22222, 
        title: "",
        text: "",
        archived: false,
        categoryid: 1
      };

    const completeNote: iNoteComplete = mapToNoteComplete(emptyNote, user.id);
  
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
  
    return (
        <>
          <Button variant="contained" color="primary" onClick={handleOpen}>
          Crear Nota
          </Button>
          <NoteMenu open={open} handleClose={handleClose} note={completeNote} onSave={createNewNote} />
        </>
      );
    };
  
  
  export default CreateButton;

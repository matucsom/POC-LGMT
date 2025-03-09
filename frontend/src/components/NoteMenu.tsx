import React, { useState,useEffect } from "react";
import { Dialog, DialogTitle, DialogContent, TextField, Button } from "@mui/material";
import {  iNoteComplete } from "../type";
import SaveButton from "./buttons/SaveButton";


interface NoteMenuProps {
  open: boolean;
  handleClose: () => void;
  note: iNoteComplete;
  onSave: (updatedNote: iNoteComplete) => void;
}

const NoteMenu: React.FC<NoteMenuProps> = ({ open, handleClose, note, onSave }) => {
  const [title, setTitle] = useState(note.title);
  const [text, setText] = useState(note.text);

  useEffect(() => {
    if (open) {
      setTitle(note.title);
      setText(note.text);
    }
  }, [open, note]);

  const handleSave = async () => {
    const updatedNote: iNoteComplete = { ...note, title, text }; 
    await onSave(updatedNote);
    handleClose();
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Editar Nota</DialogTitle>
      <DialogContent>
        <TextField
          fullWidth
          label="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          margin="dense"
        />
        <TextField
          fullWidth
          label="Texto"
          value={text}
          onChange={(e) => setText(e.target.value)}
          margin="dense"
          multiline
          rows={4}
        />
        <SaveButton onSave={handleSave} />
        <Button onClick={handleClose} color="secondary">
          Cancelar
        </Button>
      </DialogContent>
    </Dialog>
  );
};

export default NoteMenu;
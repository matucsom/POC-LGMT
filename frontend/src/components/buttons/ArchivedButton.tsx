import React from "react";
import { IconButton } from "@mui/material";
import ArchiveIcon from "@mui/icons-material/Archive";
//import { useNoteContext } from "../../context/noteContext";
//import { useNoteArchivedContext } from "../../context/noteArchivedContext";
import { iNote } from "../../type";

import { useNoteContext } from "../../context/noteContext";

interface ArchivedButtonProps {
  note: iNote;
}

const ArchivedButton: React.FC<ArchivedButtonProps> = ({ note }) => {
  //const { deleteNoteById } = useNoteContext();
  //const { addNote } = useNoteArchivedContext();
  const { archiveNoteById } = useNoteContext();


  const handleArchive = async () => {
    try {
      await archiveNoteById(note.id);
      //deleteNoteById(note.id);
      //addNote(note);
    } catch (error) {
      console.error("Error al archivar la nota:", error);
    }
  };

  return (
    <IconButton onClick={handleArchive} color="default" aria-label="Archivar nota">
      <ArchiveIcon />
    </IconButton>
  );
};

export default ArchivedButton;
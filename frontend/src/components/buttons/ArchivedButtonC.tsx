import React from "react";
import { IconButton } from "@mui/material";
import ArchiveIcon from "@mui/icons-material/Archive";

interface ArchivedButtonCProps {
  noteId: number;
  onArchive: (noteId: number) => Promise<void>; 
}

const ArchivedButtonC: React.FC<ArchivedButtonCProps> = ({ noteId, onArchive }) => {
  const handleArchive = async () => {
    try {
      await onArchive(noteId);
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

export default ArchivedButtonC;
import React from 'react';
import { IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { useNoteContext } from '../../context/noteContext'; 

interface RemoveButtonProps {
  noteId: number;
}

const RemoveButton: React.FC<RemoveButtonProps> = ({ noteId }) => {
  
  const { deleteNoteById } = useNoteContext();

  const handleRemove = async () => {
    try {
        await deleteNoteById(noteId);
      } catch (error) {
        console.error("Error al eliminar la nota:", error);
      }
  };

  return (
    <IconButton onClick={handleRemove} color="error">
      <DeleteIcon />
    </IconButton>
  );
};

export default RemoveButton;

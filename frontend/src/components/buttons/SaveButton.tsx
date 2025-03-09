import React from "react";
import { Button } from "@mui/material";

interface SaveButtonProps {
  onSave: () => void;
}

const SaveButton: React.FC<SaveButtonProps> = ({ onSave }) => {
  return (
    <Button onClick={onSave} color="primary">
      Guardar
    </Button>
  );
};

export default SaveButton;

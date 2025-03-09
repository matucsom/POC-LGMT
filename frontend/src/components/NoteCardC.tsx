import React from "react";
import { Card, CardContent, Typography } from "@mui/material";
import { iNote } from "../type";
import RemoveButton from "./buttons/RemoveButton";
import ArchivedButtonC from "./buttons/ArchivedButtonC";

interface NoteCardCProps {
  note: iNote;
  onArchive: (noteId: number) => Promise<void>; 
}

const NoteCardC: React.FC<NoteCardCProps> = ({ note, onArchive }) => {
  return (
    <Card>
      <CardContent style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <Typography>{note.title}</Typography>
        <div>
          <ArchivedButtonC noteId={note.id} onArchive={onArchive} />
          <RemoveButton noteId={note.id} />
        </div>
      </CardContent>
    </Card>
  );
};

export default NoteCardC;
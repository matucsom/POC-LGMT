import React, { useEffect } from "react";
import { useArchivedNoteContext } from "../context/archivedNoteContext";
import RedirectionButtonHomePage from "../components/buttons/RedirectionButtoHomePage";
import NoteCardC from "../components/NoteCardC";

export const ArchivedNotesPage: React.FC = () => {
  const { archivedNotes, fetchArchivedNotes,unArchiveNoteById } = useArchivedNoteContext();

  useEffect(() => {
    fetchArchivedNotes();
  }, []);

  if (!archivedNotes.length) return <div>
    <h1>No hay notas archivadas</h1>
    <RedirectionButtonHomePage/>
    </div>;

  return (
    <div>
      <h1>Notas Archivadas</h1>
      <RedirectionButtonHomePage/>
      {archivedNotes.map((note) => (
        <NoteCardC 
        key={note.id} 
        note={note} 
        onArchive={unArchiveNoteById}  
      />
      ))}
    </div>
  );
};
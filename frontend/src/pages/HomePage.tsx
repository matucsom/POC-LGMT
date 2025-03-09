import React, { useEffect } from "react";
import { useNoteContext } from "../context/noteContext";
import NoteCard from "../components/NoteCard";
import RedirectionButtonArchived from "../components/buttons/RedirectionButtonArchived";
import CreateButton from "../components/buttons/CreateNoteButton";

export const HomePage: React.FC = () => {
    const { notes, fetchUnarchivedNotes } = useNoteContext();
  
    useEffect(() => {
      fetchUnarchivedNotes();
    }, []);
  
    if (!notes.length) return <div>Cargando notas...</div>;
  
    return (
      <div>
        <h1>Lista de Notas</h1>
        <RedirectionButtonArchived/>
        <CreateButton/>
        {notes.map((note) => (
          <NoteCard key={note.id} note={note} />
        ))}
      </div>
    );
  };





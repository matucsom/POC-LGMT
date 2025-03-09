import { createContext, useContext, useState, ReactNode, useEffect } from "react";
import { iNote, iNoteComplete } from "../type";
import { archiveNote, createNote, deleteNote, getAllUnarchivedNotes, updateNote } from "../api/note-api";
import { useUserContext } from "./userContext";


interface NoteContextType {
  note: iNote | null;
  notes: iNote[];
  fetchUnarchivedNotes: () => Promise<void>;
  archiveNoteById: (id: number) => Promise<void>;
  deleteNoteById: (id: number) => Promise<void>;
  createNewNote:(note:iNoteComplete)=> Promise<void>;
  updateExistingNote:(note:iNoteComplete)=> Promise<void>;
}

const NoteContext = createContext<NoteContextType | undefined>(undefined);

export const NoteProvider = ({ children }: { children: ReactNode }) => {
  const [note, setNote] = useState<iNote | null>(null);
  const [notes, setNotes] = useState<iNote[]>([]);
  const { user } = useUserContext(); 

  useEffect(() => {
    fetchUnarchivedNotes(); 
  }, []);

  
  const fetchUnarchivedNotes = async () => {
    try {
      const data = await getAllUnarchivedNotes(user.id); 
      setNotes(data);
    } catch (error) {
      console.error("Error al obtener las notas no archivadas", error);
    }
  };

  const archiveNoteById = async (id: number) => {
    const updatedNote = await archiveNote(id);
    setNote(updatedNote);
    fetchUnarchivedNotes(); 
  };

  const deleteNoteById = async (id: number) => {
    await deleteNote(id);
    setNote(null);
    fetchUnarchivedNotes(); 
  };

  const createNewNote = async (note:iNoteComplete) => {
    try {
      const newNote  = await createNote(note); 
      setNotes((prevNotes) => [...prevNotes, newNote]);
    } catch (error) {
      console.error("Error creando la nota", error);
    }
  };

  const updateExistingNote = async (note:iNoteComplete) => {
    try {
      const updatedNote  = await updateNote(note); 
      setNotes((prevNotes) => 
        prevNotes.map(n => n.id === updatedNote.id ? updatedNote : n) 
      );
    } catch (error) {
      console.error("Error actualizando la nota", error);
    }
  };


  return (
    <NoteContext.Provider value={{ note, notes,updateExistingNote,createNewNote, fetchUnarchivedNotes, archiveNoteById, deleteNoteById }}>
      {children}
    </NoteContext.Provider>
  );
};

export const useNoteContext = () => {
  const context = useContext(NoteContext);
  if (!context) {
    throw new Error("useNoteContext debe usarse dentro de un NoteProvider");
  }
  return context;
};
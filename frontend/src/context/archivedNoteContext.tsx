import { createContext, useContext, useState, ReactNode, useEffect } from "react";
import { iNote } from "../type";
import { getArchivedNotes, unarchiveNote } from "../api/note-api";
import { useUserContext } from "./userContext";

interface ArchivedNoteContextType {
  archivedNotes: iNote[];
  note: iNote | null;
  fetchArchivedNotes: () => Promise<void>;
  unArchiveNoteById:(id:number) => Promise<void>
}

const ArchivedNoteContext = createContext<ArchivedNoteContextType | undefined>(undefined);

export const ArchivedNoteProvider = ({ children }: { children: ReactNode }) => {
  const [note, setNote] = useState<iNote | null>(null);
  const [archivedNotes, setArchivedNotes] = useState<iNote[]>([]);
  const { user } = useUserContext();

  useEffect(() => {
    fetchArchivedNotes();
  }, []);

  const fetchArchivedNotes = async () => {
    try {
      const data = await getArchivedNotes(user.id);
      setArchivedNotes(data);
    } catch (error) {
      console.error("Error al obtener las notas archivadas", error);
    }
  };

  const unArchiveNoteById = async (id: number) => {
      const updatedNote = await unarchiveNote(id);
      setNote(updatedNote);
      fetchArchivedNotes(); 
    };
 

  return (
    <ArchivedNoteContext.Provider value={{note ,archivedNotes, fetchArchivedNotes ,unArchiveNoteById}}>
      {children}
    </ArchivedNoteContext.Provider>
  );
};

export const useArchivedNoteContext = () => {
  const context = useContext(ArchivedNoteContext);
  if (!context) {
    throw new Error("useArchivedNoteContext debe usarse dentro de un ArchivedNoteProvider");
  }
  return context;
};
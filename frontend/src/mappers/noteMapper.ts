import { iNote, iNoteComplete } from "../type";

export const mapToNoteComplete = (note: iNote, userId: number): iNoteComplete => ({
    ...note,
    userid: userId,
  });
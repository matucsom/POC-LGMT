import axios from "axios";
import { iNoteComplete } from "../type";

const BASE_URL = "http://localhost:8080/notes";

export const getArchivedNotes = async (userId: number) => {
  const response = await axios.get(`${BASE_URL}/${userId}/all/archived`);
  return response.data;
};

export const getAllUnarchivedNotes = async (userId: number) => {
  const response = await axios.get(`${BASE_URL}/${userId}/all/unarchived`);
  return response.data;
};

export const deleteNote = async (noteId: number) => {
  await axios.delete(`${BASE_URL}/${noteId}`);
};

export const archiveNote = async (noteId: number) => {
  const response = await axios.patch(`${BASE_URL}/${noteId}/archive`);
  return response.data;
};

export const unarchiveNote = async (noteId: number) => {
  const response = await axios.patch(`${BASE_URL}/${noteId}/unarchive`);
  return response.data;
};

export const createNote = async (note: iNoteComplete) => {
  const response = await axios.post(`${BASE_URL}/create`, note);
  return response.data;
};

export const updateNote = async (note: iNoteComplete) => {
  const response = await axios.put(`${BASE_URL}/update`, note);
  return response.data;
};
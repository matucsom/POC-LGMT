import React from 'react';
import { Card, CardContent, Typography } from '@mui/material';
import { iNote } from '../type';
import RemoveButton from './buttons/RemoveButton';
import ArchivedButton from './buttons/ArchivedButton';
import EditButton from './buttons/EditButton';

interface NoteProps {
    note: iNote;
  }
  
  const NoteCard: React.FC<NoteProps> = ({ note }) => {
    return (
      <Card sx={{ mt: 2 }}>
        <CardContent style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Typography>{note.title}</Typography>
          <div>
            <EditButton note ={note} />
            <ArchivedButton note={note} /> 
            <RemoveButton noteId={note.id} />
          </div>
        </CardContent>
      </Card>
    );
  };
  
  export default NoteCard;

 
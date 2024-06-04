import { useEffect, useState } from "react";
import Note from "./Note";

interface Note {
    date: Date;
    noteId: number;
    title: string;
    text: string;
    userId: number;
  }

export default function TodaysNotes() {
  const [notes, setNotes] = useState<Note[]>([]);
  const [userToken, setUserToken] = useState("");
  const [userId, setUserId] = useState(0);

  useEffect(() => {
    const id = JSON.parse(localStorage.getItem("userId")!);
    const token = JSON.parse(localStorage.getItem("jwt")!);
    setUserToken(token);
    setUserId(id);
  }, []);

  useEffect(() => {
    if (userId) {
      fetchNotes();
    }
  }, [userId]);

  async function fetchNotes() {
    try {
      const response = await fetch(`/api/note/${userId}/today`, {
        headers: {
          'Authorization': `Bearer ${userToken}`
        },
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      setNotes(data);
    } catch (error) {
      console.error('Error fetching notes:', error);
    }
  }

  async function handleDeleteNote(noteId: number) {
    const response = await fetch(`/api/note/${userId}/${noteId}`, {
      method: "DELETE",
      headers: {
        'Authorization': `Bearer ${userToken}`
      },
    });

    if (response.ok) {
      fetchNotes();
    } else {
      console.error("Failed to delete the note.");
    }
  }





  return (
    <div id="todays-notes-container">
      {notes.length > 0 ? (
        <div id="todays-notes-grid">
          {notes.map((note, index: number) => (
            <Note
              key={note.noteId}
              title={note.title}
              text={note.text}
              noteId={note.noteId}
              index={index}
              del={handleDeleteNote}
            />
          ))}
        </div>
      ) : (
        <p id="no-notes">No notes for today.</p>
      )}
    </div>
  );
} 

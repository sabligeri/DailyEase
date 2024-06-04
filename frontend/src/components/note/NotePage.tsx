import NoteHeadBar from "./NoteHeadBar";
import NoteList from "./NoteList";
import "./Note.css";
import { useEffect, useState } from "react";

interface note {
  noteId: number;
  title: string;
  text: string;
  isFavorite: boolean;
}

export default function NotePage() {
  const [notes, setNotes] = useState([]);
  const [userId, setUserId] = useState(0);
  const [userToken, setUserToken] = useState("");

  function createEmptyNote(noteId: number): note {
    const emptyNote = {
      noteId: noteId,
      text: "",
      title: "",
      isFavorite: false,
    };
    return emptyNote;
  }

  useEffect(() => {
    const id = JSON.parse(localStorage.getItem("userId")!);
    const token = JSON.parse(localStorage.getItem("jwt")!);
    if (id > 0) {
      setUserId(id);
      setUserToken(token);
    }
  }, []);

  useEffect(() => {
    if(userId > 0){
      fetchNotes();
    }
  }, [userId]);

  async function fetchNotes(): Promise<void> {
    console.log(`Bearer ${userToken}`)
    try {
      const response = await fetch(`/api/note/${userId}`, {
        headers: {
          'Authorization': `Bearer ${userToken}`
        }
      });
      const data = await response.json();
      if (data.length >= 3) {
        data.splice(3, 0, createEmptyNote(-1));
      }

      setNotes(data);
      //console.log(notes);
    } catch (error) {
      console.error("Failed to load notes:", error);
    }
  }

  async function handleDeleteNote(noteId: number) {
    const response = await fetch(`/api/note/${userId}/${noteId}`, {
      method: "DELETE",
      headers: {
        'Authorization': `Bearer ${userToken}`
      }
    });

    if (response.ok) {
      fetchNotes();
    } else {
      console.error("Failed to delete the note.");
    }
  }

  return (
    <div id="note-page">
      <NoteHeadBar reFetchNotes={fetchNotes} userId={userId} />
      <NoteList del={handleDeleteNote} notes={notes} />
    </div>
  );
}

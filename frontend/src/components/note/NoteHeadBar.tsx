import { useEffect, useState } from "react";
import AddNote from "./AddNote";

interface NoteHeadBarProps {
  reFetchNotes: () => void;
  userId: number;
}

export default function NoteHeadBar({ reFetchNotes, userId }: NoteHeadBarProps) {
  const [username, setUsername] = useState("");

  useEffect(() => {
    const username = JSON.parse(localStorage.getItem("username")!);
    setUsername(username);
  }, []);

  return (
    <div id="note-headbar">
      <h2 id="note-user-name">
        <p id="note-user-name-spacer">{username}</p>
      </h2>

      <div>
        <AddNote reFetchNotes={reFetchNotes} userId={userId} />
      </div>
    </div>
  );
}

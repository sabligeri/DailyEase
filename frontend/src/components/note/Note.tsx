import { useState } from "react";
import { FaHeart } from "react-icons/fa";

interface NoteProps {
  title: string;
  index: number;
  del: (noteId: number) => void;
  text: string; // Define the type of 'text' prop
  noteId: number;
}

export default function Note({ title, index, del, text, noteId }: NoteProps) {
  const [fav, setFav] = useState(false);
  return (
    <div className="note" key={index}>
      <div className="note-title-container">
        <h3>{title}</h3>
        <FaHeart
          id="heart-img"
          key={index}
          className={fav ? "haert-active" : "heart-inactive"}
          onClick={() => (fav ? setFav(false) : setFav(true))}
          size={20}
        />
      </div>
      <h3 className="note-text">{text}</h3>
      <button className="button"  onClick={() => del(noteId)}>
        <svg viewBox="0 0 448 512" className="svgIcon">
          <path d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"></path>
        </svg>
      </button>
      {/* <button onClick={() => del(noteId)}>Delete</button> */}
    </div>
  );
}

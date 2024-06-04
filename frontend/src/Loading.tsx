import { helix } from "ldrs";
import { useEffect, useState } from "react";

export default function Loading() {
  helix.register();

  const [RGBcolor, setRGBcolor] = useState([255, 0, 0]);

  useEffect(() => {
    setTimeout(() => {
      setRGBcolor([
        Math.floor(Math.random() * 255),
        Math.floor(Math.random() * 255),
        Math.floor(Math.random() * 255),
      ]);
    }, 450);
  }, [RGBcolor]);

  return (
    <div id="loader">
      <l-helix size="400" speed="2.5" color={`rgb(${RGBcolor})`}></l-helix>
    </div>
  );
}

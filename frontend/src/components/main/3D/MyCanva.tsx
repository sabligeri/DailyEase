import { Canvas } from '@react-three/fiber'
import { Cappuccino } from './Cappuccino'
import {OrbitControls } from "@react-three/drei";
import { Suspense } from "react";
import './myCanva.css'

export default function MyCanva(){
 

  return (
    <div id='canva-container'>

    <Canvas camera={{position:[0,3,4]}}>
        <Suspense fallback={null}>
          <directionalLight position={[-6,2,2]}/>
          <directionalLight position={[6,2,2]}/>
          <ambientLight intensity={1.5}/>
          <Cappuccino/>
          <OrbitControls />
          {/* <Environment preset="apartment" background /> */}
        </Suspense>
      </Canvas>
    </div>
  )
}

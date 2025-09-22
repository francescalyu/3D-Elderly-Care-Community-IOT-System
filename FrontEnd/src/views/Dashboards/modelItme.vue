<script setup lang="ts">
import { shallowRef } from "vue";
import * as THREE from "three";
import { OrbitControls, GLTFModel } from "@tresjs/cientos";
import { TresCanvas, useRenderLoop, useTexture } from "@tresjs/core";
import { BasicShadowMap, SRGBColorSpace, NoToneMapping } from "three";
import { gettwinpoint } from "./api/index";
import eventBus from "@/utils/event-bus.js";
const { onLoop } = useRenderLoop();
const scene = new THREE.Scene();
// const light = new THREE.DirectionalLight(0xffffff);
// scene.add(light);
const gl = {
  clearColor: "none",
  shadows: true,
  alpha: false,
  shadowMapType: BasicShadowMap,
  outputColorSpace: SRGBColorSpace,
  toneMapping: NoToneMapping,
};
const twinpointData={}
const nameData = [
  {text:"Text_014",uuid:'2d3e6635-eea3-4fae-a878-568ff61754da',name:'门磁感应器'},
  {text:"Text_010",uuid:'bf0ecb45-f6d7-4554-b595-38aa7df0dcc1',name:'主卧厨房灯开关'},
  {text:"Text_011",uuid:'53e7e0ca-9f18-4793-82b7-aa9870d575b3',name:'客厅计量插座'},
  {text:"Text_019",uuid:'bed51601-8a8e-4a4b-99e0-e9266c70dc5b',name:'应急对讲器'},
  {text:"Text_013",uuid:'8172a1b4-e8c3-4ea3-8201-f8b6714c082c',name:'空气质量传感器'},
  {text:"Text_012",uuid:'4c2f5f33-6f49-4c0e-bcff-a8c3b8ab1afa',name:'卧室计量插座'},
  {text:"Text_009",uuid:'78090afb-83c2-46b0-88b6-354f03f3727d',name:'次卧灯开关'},
  {text:"Text_008",uuid:'0a960b29-9474-4f9d-aef0-c2e30fbfb4f6',name:'空调插座02'},
  {text:"Text_007",uuid:'5825bb4e-c50f-44a4-bb06-e7b740fbe5b3',name:'次卧壁挂空调'},
  {text:"Text_006",uuid:'0aac086b-c425-4d6a-8c5d-a0c7f1d4f4d4',name:'卫生间灯开关'},
  {text:"Text_005",uuid:'04560384-8dec-47a4-ab86-c875fad5a32c',name:'主卧灯开关'},
  {text:"Text_015",uuid:'fd870318-8ea2-43fa-8f11-aa65e5e43789',name:'厨房烟雾/燃气传感器'},
  {text:"Text_016",uuid:'14b7b011-d52a-43d7-802a-85e6091352b4',name:'冰箱计量插座'},
  {text:"Text_002",uuid:'0dee1a40-7cde-46fa-ac5f-a46c7a3a43ab',name:'户外光照/温湿度传感器'},
  {text:"Text_003",uuid:'4093ac13-4d97-486c-bd4f-9dc1b4f8cbf7',name:'主卧壁挂空调'},
  {text:"Text_004",uuid:'6be555e5-7674-42d5-bfe1-70d0c6983266',name:'空调插座01'},
];
const boxRef: ShallowRef<TresInstance | null> = shallowRef(null);
const boxRef1: ShallowRef<TresInstance | null> = shallowRef(null);
const click1 = (event: any) => {
  console.log("uuid", event);
  for (let i = 0; i < nameData.length; i++) {
    if (event.object.name == nameData[i].text) {
      gettwinpoint(nameData[i].uuid).then((res)=>{
        twinpointData.value=res
        eventBus.$emit("twinpointData", twinpointData.value);
        console.log("res", twinpointData.value);
      })
    }
  }
  
  // if (event) {
  //   event.object.material.color.set("red");
  //   event.object.name='123';
  // }
  // onLoop(({ delta, elapsed }) => {
  //   if (boxRef.value) {
  //     boxRef.value.rotation.y += -delta + 0.001;
  //     // boxRef.value.rotation.z = elapsed * 0.02
  //   }
  // });
};
const click2 = (event: any) => {
  console.log(boxRef1.value, event);
  if (event) {
    event.object.material.color.set("#008080");
  }
  onLoop(({ delta, elapsed }) => {
    if (boxRef1.value) {
      boxRef1.value.rotation.y += delta + 0.005;
      // boxRef.value.rotation.z = elapsed * 0.02
    }
  });
};
defineExpose({
  twinpointData,
})
//  const helper = new THREE.DirectionalLightHelper(light, 15);
//  scene.add(helper);
</script>
<template>
  <TresCanvas v-bind="gl">
    <OrbitControls />
    <TresPerspectiveCamera
      :position="[75, 70, 100]"
      :fov="3"
      :aspect="1"
      :near="0.1"
      :far="999"
    />
    <TresMesh ref="boxRef" :position="[1.5, 0.7, 1]" cast-shadow>
      <Suspense>
        <GLTFModel path="/models/znjj.glb" draco @click="click1($event)" />
        <!-- <FBXModel path="/src/model/bed.fbx" /> -->
      </Suspense>
    </TresMesh>
    <TresAmbientLight :intensity="0" />
    <TresDirectionalLight cast-shadow :position="[1, 1, 1]" :intensity="15" />
    <!-- <TresMesh ref="boxRef1" :position="[5, 8, 5]">
      <Suspense>
        <GLTFModel path="/src/model/bed.glb" draco @click="click2($event)" />
        <FBXModel path="/src/model/bed.fbx" />
      </Suspense>
    </TresMesh> -->
  </TresCanvas>
</template>
<style scoped></style>

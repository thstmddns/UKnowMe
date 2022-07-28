<template>
  <div>
    <div id="nowAvatar"></div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";

import * as THREE from "three";
import * as GLTF from "three/examples/jsm/loaders/GLTFLoader";
import * as OrbitControls from "three/examples/jsm/controls/OrbitControls";
import * as VRMUtils from "@pixiv/three-vrm";

onMounted(() => {
  //three
  const scene = new THREE.Scene();
  const renderer = new THREE.WebGLRenderer({ alpha: true });
  renderer.setSize(window.innerWidth, window.innerHeight, false);
  renderer.setPixelRatio(window.devicePixelRatio);
  renderer.domElement.id = "nowAvatar";

  document.getElementById("nowAvatar").append(renderer.domElement);

  // camera
  const orbitCamera = new THREE.PerspectiveCamera(
    75,
    window.innerWidth / window.innerHeight,
    0.1,
    1000
  );
  orbitCamera.position.set(0.0, 1.4, 0.7);

  // controls
  const orbitControls = new OrbitControls.OrbitControls(
    orbitCamera,
    renderer.domElement
  );
  orbitControls.screenSpacePanning = true;
  orbitControls.target.set(0.0, 1.4, 0.0);
  orbitControls.update();

  // light
  const light = new THREE.DirectionalLight(0xffffff);
  light.position.set(1.0, 1.0, 1.0).normalize();
  scene.add(light);

  /* THREEJS WORLD SETUP */
  let currentVrm;

  // Main Render Loop
  const clock = new THREE.Clock();

  function animate() {
    requestAnimationFrame(animate);

    if (currentVrm) {
      // Update model to render physics
      currentVrm.update(clock.getDelta());
    }
    renderer.render(scene, orbitCamera);
  }
  animate();

  // Import Character VRM
  const loader = new GLTF.GLTFLoader();
  loader.crossOrigin = "anonymous";

  var ary = ["test.vrm", "test2.vrm", "동민.vrm", "진경.vrm"];
  var rand = Math.floor(Math.random() * 101);

  rand %= 4;

  // Import model from URL, add your own model here
  loader.load(
    // "https://cdn.glitch.com/29e07830-2317-4b15-a044-135e73c7f840%2FAshtra.vrm?v=1630342336981",
    ary[rand],

    (gltf) => {
      VRMUtils.VRMUtils.removeUnnecessaryJoints(gltf.scene);

      VRMUtils.VRM.from(gltf).then((vrm) => {
        scene.add(vrm.scene);
        currentVrm = vrm;
        currentVrm.scene.rotation.y = Math.PI; // Rotate model 180deg to face camera
      });
    },

    (progress) =>
      console.log(
        "Loading model...",
        100.0 * (progress.loaded / progress.total),
        "%"
      ),
    (error) => console.error(error)
  );
});
</script>

<style>
#nowAvatar {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 50%;
  transform: translate(-50%, 0%);
}
</style>

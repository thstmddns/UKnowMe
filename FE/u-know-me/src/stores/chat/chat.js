import { defineStore } from 'pinia'
import * as THREE from "three";
import * as GLTF from "three/examples/jsm/loaders/GLTFLoader";
import * as OrbitControls from "three/examples/jsm/controls/OrbitControls";
import * as VRMUtils from "@pixiv/three-vrm";
import * as Kalidokit from "kalidokit";
import * as Holistic from "@mediapipe/holistic";
import * as DrawConnectors from "@mediapipe/drawing_utils";
import * as Camera from "@mediapipe/camera_utils";

let currentVrm;

export const useChatStore = defineStore('chat', {
  state: () => ({
    accuseBtn: 0,
    gameBtn: 0,
    test: 0,
    webSocket: null,
    OV: undefined,
    session: undefined,
    mainStreamManager: undefined,
    publisher: undefined,
    subscribers: [],
    camera: null,
    camera2: null,
    videoDevices: null,
    jsonData: null,
    SessionName : "SessionA",
    otherPeople : [],
  }),
  getters: {

  },
  actions: {
    avatarLoad(id) {
      //three
      const scene = new THREE.Scene();
      const renderer = new THREE.WebGLRenderer({ alpha: true });
      renderer.setSize(640, 480);
      renderer.setPixelRatio(window.devicePixelRatio);
      renderer.domElement.id = "avatarCanvas";

      document.getElementById("my-video").prepend(renderer.domElement);

      // camera
      const orbitCamera = new THREE.PerspectiveCamera(
        75,
        640 / 480,
        0.1,
        1000
      );
      orbitCamera.position.set(0.0, 1.4, 0.7);

      // controls
      const orbitControls = new OrbitControls.OrbitControls(
        orbitCamera,
        renderer.domElement
      );
      orbitControls.enableDamping = true;
      orbitControls.target.set(0.0, 1.4, 0.0);
      orbitControls.enablePan = false;
      orbitControls.maxDistance = 1;
      orbitControls.minDistance = 0.5;
      orbitControls.minPolarAngle = 1;
      orbitControls.maxPolarAngle = 2.5;
      orbitControls.minAzimuthAngle = -1;
      orbitControls.maxAzimuthAngle = 1;

      // light
      const light = new THREE.DirectionalLight(0xffffff);
      light.position.set(1.0, 1.0, 1.0).normalize();
      scene.add(light);

      // Main Render Loop
      const clock = new THREE.Clock();

      function animate() {
        requestAnimationFrame(animate);
        orbitControls.update();

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

      // Import model from URL, add your own model here
      loader.load(
        // "https://cdn.glitch.com/29e07830-2317-4b15-a044-135e73c7f840%2FAshtra.vrm?v=1630342336981",
        "vrm/" + id + ".vrm",

        (gltf) => {
          VRMUtils.VRMUtils.removeUnnecessaryJoints(gltf.scene);

          VRMUtils.VRM.from(gltf).then((vrm) => {

            vrm.humanoid.setPose({
              [VRMUtils.VRMSchema.HumanoidBoneName.LeftShoulder]: {
                rotation: new THREE.Quaternion().setFromEuler(new THREE.Euler(0.0, 0.0, 0.2)).toArray()
              },
              [VRMUtils.VRMSchema.HumanoidBoneName.RightShoulder]: {
                rotation: new THREE.Quaternion().setFromEuler(new THREE.Euler(0.0, 0.0, -0.2)).toArray()
              },
              [VRMUtils.VRMSchema.HumanoidBoneName.LeftUpperArm]: {
                rotation: new THREE.Quaternion().setFromEuler(new THREE.Euler(0.0, 0.15, 1.1)).toArray()
              },
              [VRMUtils.VRMSchema.HumanoidBoneName.RightUpperArm]: {
                rotation: new THREE.Quaternion().setFromEuler(new THREE.Euler(0.0, -0.15, -1.1)).toArray()
              },
              [VRMUtils.VRMSchema.HumanoidBoneName.LeftLowerArm]: {
                rotation: new THREE.Quaternion().setFromEuler(new THREE.Euler(-0.3, 0.3, 0.7)).toArray()
              },
              [VRMUtils.VRMSchema.HumanoidBoneName.RightLowerArm]: {
                rotation: new THREE.Quaternion().setFromEuler(new THREE.Euler(-0.3, -0.3, -0.7)).toArray()
              },
            });

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

      // capture
      const avatarCanvas = document.getElementById("avatarCanvas");
      avatarCanvas.style.display = 'inline-block'
      // var avatarVideo = avatarCanvas.captureStream(30).getVideoTracks()[0];

      const testVideo = document.getElementById("test-video");
      testVideo.srcObject = avatarCanvas.captureStream();

      return testVideo.srcObject.getVideoTracks()[0];
    },

    startHolistic() {
      const clamp = Kalidokit.Utils.clamp;
      const lerp = Kalidokit.Vector.lerp;

      ////////////////////////
      // Animate Rotation Helper function
      const rigRotation = (name, rotation = { x: 0, y: 0, z: 0 }, dampener = 1, lerpAmount = 0.3) => {
        if (!currentVrm) {
          return;
        }
        const Part = currentVrm.humanoid.getBoneNode(VRMUtils.VRMSchema.HumanoidBoneName[name]);
        if (!Part) {
          return;
        }

        let euler = new THREE.Euler(
          rotation.x * dampener,
          rotation.y * dampener,
          rotation.z * dampener,
          rotation.rotationOrder || "XYZ"
        );
        let quaternion = new THREE.Quaternion().setFromEuler(euler);
        Part.quaternion.slerp(quaternion, lerpAmount); // interpolate
      };

      // Animate Position Helper Function
      const rigPosition = (name, position = { x: 0, y: 0, z: 0 }, dampener = 1, lerpAmount = 0.3) => {
        if (!currentVrm) {
          return;
        }
        const Part = currentVrm.humanoid.getBoneNode(VRMUtils.VRMSchema.HumanoidBoneName[name]);
        if (!Part) {
          return;
        }
        let vector = new THREE.Vector3(position.x * dampener, position.y * dampener, position.z * dampener);
        Part.position.lerp(vector, lerpAmount); // interpolate
      };

      let oldLookTarget = new THREE.Euler();
      const rigFace = (riggedFace) => {
        if (!currentVrm) {
          return;
        }
        rigRotation("Neck", riggedFace.head, 0.7);

        // Blendshapes and Preset Name Schema
        const Blendshape = currentVrm.blendShapeProxy;
        const PresetName = VRMUtils.VRMSchema.BlendShapePresetName;

        // Simple example without winking. Interpolate based on old blendshape, then stabilize blink with `Kalidokit` helper function.
        // for VRM, 1 is closed, 0 is open.
        riggedFace.eye.l = lerp(clamp(1 - riggedFace.eye.l, 0, 1), Blendshape.getValue(PresetName.Blink), 0.5);
        riggedFace.eye.r = lerp(clamp(1 - riggedFace.eye.r, 0, 1), Blendshape.getValue(PresetName.Blink), 0.5);
        riggedFace.eye = Kalidokit.Face.stabilizeBlink(riggedFace.eye, riggedFace.head.y);
        Blendshape.setValue(PresetName.Blink, riggedFace.eye.l);

        // Interpolate and set mouth blendshapes
        Blendshape.setValue(PresetName.I, lerp(riggedFace.mouth.shape.I, Blendshape.getValue(PresetName.I), 0.5));
        Blendshape.setValue(PresetName.A, lerp(riggedFace.mouth.shape.A, Blendshape.getValue(PresetName.A), 0.5));
        Blendshape.setValue(PresetName.E, lerp(riggedFace.mouth.shape.E, Blendshape.getValue(PresetName.E), 0.5));
        Blendshape.setValue(PresetName.O, lerp(riggedFace.mouth.shape.O, Blendshape.getValue(PresetName.O), 0.5));
        Blendshape.setValue(PresetName.U, lerp(riggedFace.mouth.shape.U, Blendshape.getValue(PresetName.U), 0.5));

        //PUPILS
        //interpolate pupil and keep a copy of the value
        let lookTarget = new THREE.Euler(
          lerp(oldLookTarget.x, riggedFace.pupil.y, 0.4),
          lerp(oldLookTarget.y, riggedFace.pupil.x, 0.4),
          0,
          "XYZ"
        );
        oldLookTarget.copy(lookTarget);
        currentVrm.lookAt.applyer.lookAt(lookTarget);
      };

      /* VRM Character Animator */
      const animateVRM = (vrm, results) => {
        if (!vrm) {
          return;
        }
        // Take the results from `Holistic` and animate character based on its Face, Pose, and Hand Keypoints.
        let riggedPose, riggedLeftHand, riggedRightHand, riggedFace;

        const faceLandmarks = results.faceLandmarks;
        // Pose 3D Landmarks are with respect to Hip distance in meters
        const pose3DLandmarks = results.ea;
        // Pose 2D landmarks are with respect to videoWidth and videoHeight
        const pose2DLandmarks = results.poseLandmarks;
        // Be careful, hand landmarks may be reversed
        const leftHandLandmarks = results.rightHandLandmarks;
        const rightHandLandmarks = results.leftHandLandmarks;

        // Animate Face
        if (faceLandmarks) {
          riggedFace = Kalidokit.Face.solve(faceLandmarks, {
            runtime: "mediapipe",
            video: videoElement,
          });
          rigFace(riggedFace);
        }

        // Animate Pose
        if (pose2DLandmarks && pose3DLandmarks) {
          riggedPose = Kalidokit.Pose.solve(pose3DLandmarks, pose2DLandmarks, {
            runtime: "mediapipe",
            video: videoElement,
          });
          rigRotation("Hips", riggedPose.Hips.rotation, 0.7);
          rigPosition(
            "Hips",
            {
              x: riggedPose.Hips.position.x, // Reverse direction
              y: riggedPose.Hips.position.y + 1, // Add a bit of height
              z: -riggedPose.Hips.position.z, // Reverse direction
            },
            1,
            0.07
          );

          rigRotation("Chest", riggedPose.Spine, 0.25, 0.3);
          rigRotation("Spine", riggedPose.Spine, 0.45, 0.3);

          rigRotation("RightUpperArm", riggedPose.RightUpperArm, 1, 0.3);
          rigRotation("RightLowerArm", riggedPose.RightLowerArm, 1, 0.3);
          rigRotation("LeftUpperArm", riggedPose.LeftUpperArm, 1, 0.3);
          rigRotation("LeftLowerArm", riggedPose.LeftLowerArm, 1, 0.3);

          rigRotation("LeftUpperLeg", riggedPose.LeftUpperLeg, 1, 0.3);
          rigRotation("LeftLowerLeg", riggedPose.LeftLowerLeg, 1, 0.3);
          rigRotation("RightUpperLeg", riggedPose.RightUpperLeg, 1, 0.3);
          rigRotation("RightLowerLeg", riggedPose.RightLowerLeg, 1, 0.3);
        }

        // Animate Hands
        if (leftHandLandmarks) {
          riggedLeftHand = Kalidokit.Hand.solve(leftHandLandmarks, "Left");
          rigRotation("LeftHand", {
            // Combine pose rotation Z and hand rotation X Y
            z: riggedPose.LeftHand.z,
            y: riggedLeftHand.LeftWrist.y,
            x: riggedLeftHand.LeftWrist.x,
          });
          rigRotation("LeftRingProximal", riggedLeftHand.LeftRingProximal);
          rigRotation("LeftRingIntermediate", riggedLeftHand.LeftRingIntermediate);
          rigRotation("LeftRingDistal", riggedLeftHand.LeftRingDistal);
          rigRotation("LeftIndexProximal", riggedLeftHand.LeftIndexProximal);
          rigRotation("LeftIndexIntermediate", riggedLeftHand.LeftIndexIntermediate);
          rigRotation("LeftIndexDistal", riggedLeftHand.LeftIndexDistal);
          rigRotation("LeftMiddleProximal", riggedLeftHand.LeftMiddleProximal);
          rigRotation("LeftMiddleIntermediate", riggedLeftHand.LeftMiddleIntermediate);
          rigRotation("LeftMiddleDistal", riggedLeftHand.LeftMiddleDistal);
          rigRotation("LeftThumbProximal", riggedLeftHand.LeftThumbProximal);
          rigRotation("LeftThumbIntermediate", riggedLeftHand.LeftThumbIntermediate);
          rigRotation("LeftThumbDistal", riggedLeftHand.LeftThumbDistal);
          rigRotation("LeftLittleProximal", riggedLeftHand.LeftLittleProximal);
          rigRotation("LeftLittleIntermediate", riggedLeftHand.LeftLittleIntermediate);
          rigRotation("LeftLittleDistal", riggedLeftHand.LeftLittleDistal);
        }
        if (rightHandLandmarks) {
          riggedRightHand = Kalidokit.Hand.solve(rightHandLandmarks, "Right");
          rigRotation("RightHand", {
            // Combine Z axis from pose hand and X/Y axis from hand wrist rotation
            z: riggedPose.RightHand.z,
            y: riggedRightHand.RightWrist.y,
            x: riggedRightHand.RightWrist.x,
          });
          rigRotation("RightRingProximal", riggedRightHand.RightRingProximal);
          rigRotation("RightRingIntermediate", riggedRightHand.RightRingIntermediate);
          rigRotation("RightRingDistal", riggedRightHand.RightRingDistal);
          rigRotation("RightIndexProximal", riggedRightHand.RightIndexProximal);
          rigRotation("RightIndexIntermediate", riggedRightHand.RightIndexIntermediate);
          rigRotation("RightIndexDistal", riggedRightHand.RightIndexDistal);
          rigRotation("RightMiddleProximal", riggedRightHand.RightMiddleProximal);
          rigRotation("RightMiddleIntermediate", riggedRightHand.RightMiddleIntermediate);
          rigRotation("RightMiddleDistal", riggedRightHand.RightMiddleDistal);
          rigRotation("RightThumbProximal", riggedRightHand.RightThumbProximal);
          rigRotation("RightThumbIntermediate", riggedRightHand.RightThumbIntermediate);
          rigRotation("RightThumbDistal", riggedRightHand.RightThumbDistal);
          rigRotation("RightLittleProximal", riggedRightHand.RightLittleProximal);
          rigRotation("RightLittleIntermediate", riggedRightHand.RightLittleIntermediate);
          rigRotation("RightLittleDistal", riggedRightHand.RightLittleDistal);
        }
      };

      /* SETUP MEDIAPIPE HOLISTIC INSTANCE */
      let videoElement = document.querySelector(".input_video"),
        guideCanvas = document.querySelector("canvas.guides");

      const onResults = (results) => {
        // Draw landmark guides
        drawResults(results);
        // Animate model
        animateVRM(currentVrm, results);
      };

      const holistic = new Holistic.Holistic({
        locateFile: (file) => {
          console.log("test :" + file);
          return './holistic/' + file;
          // return `https://cdn.jsdelivr.net/npm/@mediapipe/holistic@0.5.1635989137/${file}`;
        },
      });

      holistic.setOptions({
        modelComplexity: 1,
        smoothLandmarks: true,
        minDetectionConfidence: 0.7,
        minTrackingConfidence: 0.7,
        refineFaceLandmarks: true,
      });
      // Pass holistic a callback function
      holistic.onResults(onResults);

      const drawResults = (results) => {
        guideCanvas.width = videoElement.videoWidth;
        guideCanvas.height = videoElement.videoHeight;
        let canvasCtx = guideCanvas.getContext("2d");
        canvasCtx.save();
        canvasCtx.clearRect(0, 0, guideCanvas.width, guideCanvas.height);
        // Use `Mediapipe` drawing functions
        DrawConnectors.drawLandmarks(canvasCtx, results.poseLandmarks, {
          color: "#ff0364",
          lineWidth: 2,
        });
        if (results.faceLandmarks && results.faceLandmarks.length === 478) {
          //draw pupils
          DrawConnectors.drawLandmarks(canvasCtx, [results.faceLandmarks[468], results.faceLandmarks[468 + 5]], {
            color: "#ffe603",
            lineWidth: 2,
          });
        }
        DrawConnectors.drawLandmarks(canvasCtx, results.leftHandLandmarks, {
          color: "#00cff7",
          lineWidth: 2,
        });
        DrawConnectors.drawLandmarks(canvasCtx, results.rightHandLandmarks, {
          color: "#ff0364",
          lineWidth: 2,
        });
      };

      // Use `Mediapipe` utils to get camera - lower resolution = higher fps
      this.camera = new Camera.Camera(videoElement, {
        onFrame: async () => {
          await holistic.send({ image: videoElement });
        },
        width: 640,
        height: 480,
      });
      this.camera.start();
      ////////////////////////
    },

    leaveSession() {
      document.getElementById("avatarCanvas").remove();
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);

      if(this.camera){
        this.camera.stop();
      }
      if(this.camera2){
        this.camera2.stop();
      }
    },

    socketConnect(seq) {
      //socket test
      console.log("socket test");
      // 1. 웹소켓 클라이언트 객체 생성
      const webSocket = new WebSocket("wss://uknowme.mooo.com:8443/ws/chat");

      this.webSocket = webSocket;
      let toCam = this.toCam;

      // 2. 웹소켓 이벤트 처리
      // 2-1) 연결 이벤트 처리
      webSocket.onopen = () => {
        console.log("웹소켓서버와 연결 성공");
        webSocket.send(`{
          "key" : "chat_start_1",
          "room_seq" : "${this.SessionName}",
          "user_seq" : "${seq}"
        }`);
      };

      // 2-2) 메세지 수신 이벤트 처리
      webSocket.onmessage = function (event) {
        console.log(`서버 웹소켓에게 받은 데이터: ${event.data}`);
        const test = event.data.replace(/,\s*}$/, '}');
        this.jsonData = JSON.parse(test);
        console.log(this.jsonData);
        if (this.jsonData.key == "uknowme") {
          toCam();
        }
      };

      // 2-3) 연결 종료 이벤트 처리
      webSocket.onclose = function () {
        console.log("서버 웹소켓 연결 종료");
      };

      // 2-4) 에러 발생 이벤트 처리
      webSocket.onerror = function (event) {
        console.log(event);
      };
    },

    heartClick() {
      this.webSocket.send(`{
        "key" : "heart_1",
        "room" : "cavavsdv-sadvas-asdvas"
      }`);
    },

    toCam() {
      this.camera.stop();
      
      document.querySelector(".preview").remove();
      document.getElementById("avatarCanvas").remove();

      let videoElement = document.querySelector(".my-real-video");

      videoElement.style.display = "block";

      this.camera2 = new Camera.Camera(videoElement, {
        width: 640,
        height: 480,
      });
      this.camera2.start();

      console.log("디바이스 카메라 리스트 : "+this.videoDevices);

      let newPublisher = this.OV.initPublisher('html-element-id', {
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true, // Whether you want to start publishing with your video enabled or not
      });

      this.session.unpublish(this.publisher).then(() => {
        console.log('Old publisher unpublished!');

        // Assigning the new publisher to our global variable 'publisher'
        this.publisher = newPublisher;

        // Publishing the new publisher
        this.session.publish(this.publisher).then(() => {
          console.log('New publisher published!');
        });
      });
    }
  },
})
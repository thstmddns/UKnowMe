<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <div id="img-div">
        <img src="resources/images/openvidu_grey_bg_transp_cropped.png" />
      </div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div>
        <div class="preview">
          <video class="input_video" width="1280px" height="720px"></video>
          <canvas class="guides" style="display:none"></canvas>
        </div>
      </div>
      <!-- <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager" />
      </div> -->
      <div id="video-container" class="col-md-6">
        <!-- <user-video
          :stream-manager="publisher"
          @click="updateMainVideoStreamManager(publisher)"
        /> -->
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
    </div>

    <chat-something/>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/chat/UserVideo";
import * as THREE from "three";
import * as GLTF from "three/examples/jsm/loaders/GLTFLoader";
import * as OrbitControls from "three/examples/jsm/controls/OrbitControls";
import * as VRMUtils from "@pixiv/three-vrm";
import * as Kalidokit from "kalidokit";
import * as Holistic from "@mediapipe/holistic";
import * as DrawConnectors from "@mediapipe/drawing_utils";
import * as Camera from "@mediapipe/camera_utils";

import ChatSomething from "@/components/chat/ChatSomething";

const clamp = Kalidokit.Utils.clamp;
const lerp = Kalidokit.Vector.lerp;

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_URL = "https://uknowme.mooo.com";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",

  components: {
    UserVideo, ChatSomething
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
    };
  },

  methods: {
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            //three
            const scene = new THREE.Scene();
            const renderer = new THREE.WebGLRenderer({ alpha: true });
            renderer.setSize(640, 480);
            renderer.setPixelRatio(window.devicePixelRatio);
            renderer.domElement.id = "test";
            document.body.appendChild(renderer.domElement);

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
                    return './holistic/'+file;
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
            const camera = new Camera.Camera(videoElement, {
                onFrame: async () => {
                    await holistic.send({ image: videoElement });
                },
                width: 640,
                height: 480,
            });
            camera.start();
            ////////////////////////

            // capture
            const canvas = document.getElementById("test");
            var testVideo = canvas.captureStream(30).getVideoTracks()[0];

            // --- Get your own camera stream with the desired properties ---
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: false, // The source of audio. If undefined default microphone
              videoSource: testVideo, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: true, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>

<style>
#session {
  background: radial-gradient(61.17% 61.17% at 50% 50%, #EBDCFE 56.77%, #FFFFFF 100%);;
}
</style>
<script setup>
import { decodeCredential } from "vue3-google-login";
const callback = (response) => {
  // This callback will be triggered when the user selects or login to
  // his Google account from the popup
  const userData = decodeCredential(response.credential);
  console.log("Handle the response", response);
  console.log("DecodeCredential", userData);
};

const buttonConfig = {
  type: "icon",
  // theme: "outline",
  // size: "large",
  // text: "signup_with",
  // shape: "",
  // logo_alignment: "",
  // width: "",
  // locale: "",
};

</script>

<template>
  <vue3-google-login
    :callback="callback"
    :buttonConfig="buttonConfig"/>
</template>
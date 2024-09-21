<template>
  <div style="text-align: left">
    <div v-for="item in list" :key="item">
      <h1 style="margin-left: 50px" @click="go(item)">{{ item.replace('/','') }}</h1>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";
import router from "@/router";
let __PATH__ = window.sessionStorage.getItem("__PATH__")
let list = ref([])
let go = (item) => {
  console.log(item);
  router.push('/ListPage?name=' + item.replace('/',''));
}
onMounted(async () => {
  let html = await axios.get(__PATH__)
  html = html.data
  const parser = new DOMParser();
  const doc = parser.parseFromString(html, "text/html");
  console.log(doc);
  const divElement = doc.querySelector("pre");
  const a = divElement.querySelectorAll("a");
  let temp=[]
  a.forEach(i => temp.push(i.textContent));
  temp.shift()
  temp.reverse()
  list.value = temp
  console.log(list.value)
})
</script>

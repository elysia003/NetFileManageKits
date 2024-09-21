<template>
  <div style="width: 375px">
    <div v-for="item in list" :key="item.url">
      <img :src="item.url" :alt="item.title" />
    </div>
  </div>
</template>
<style scoped>
</style>
<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";

let __PATH__ = window.sessionStorage.getItem("__PATH__")
let route = useRoute()
let list = ref([])
let title = ''
onMounted(async () => {
  title = route.query.url
  let html = await axios.get(__PATH__ + "/" + title)
  html = html.data
  const parser = new DOMParser();
  const doc = parser.parseFromString(html, "text/html");
  const divElement = doc.querySelector("pre");
  const a = divElement.querySelectorAll("a");
  let temp = []
  a.forEach(i => temp.push({
    url: __PATH__ + "/" + title + '/' + i.textContent,
    title: i.textContent.substring(0, i.textContent.length - 1)
  }));
  temp.shift()
  list.value = temp
  console.log(list.value)
})
</script>

<template>
  <span style="text-align:left;" class="container">
    <ul>
      <li v-for="item in list" :key="item" @click="go(item)">
        <a>
          <div class="titlepic"> <img :src="item.url" alt=""></div>
          <h2>{{ item.title }}</h2>
        </a>
      </li>
    </ul>
  </span>
</template>
<style scoped>
ul {
  font: 12px/1.5 Microsoft YaHei;
  color: #444;
  font-family: "Microsoft Yahei UI", "Microsoft Yahei", "Helvetica Neue", Helvetica, "Nimbus Sans L", Arial, "Liberation Sans", "Hiragino Sans GB", "Microsoft YaHei", "Wenquanyi Micro Hei", "WenQuanYi Zen Hei", "ST Heiti", SimHei, "WenQuanYi Zen Hei Sharp", sans-serif;
  text-align: left;
  word-wrap: break-word;
  margin: 0;
  padding: 0;
  font-size: 0;
  margin-bottom: 15px;
  margin-right: -2%;
  min-height: 600px;
}

li {
  font: 12px/1.5 Microsoft YaHei;
  color: #444;
  font-family: "Microsoft Yahei UI", "Microsoft Yahei", "Helvetica Neue", Helvetica, "Nimbus Sans L", Arial, "Liberation Sans", "Hiragino Sans GB", "Microsoft YaHei", "Wenquanyi Micro Hei", "WenQuanYi Zen Hei", "ST Heiti", SimHei, "WenQuanYi Zen Hei Sharp", sans-serif;
  font-size: 0;
  word-wrap: break-word;
  margin: 0;
  padding: 0;
  list-style: none;
  position: relative;
  display: inline-block;
  margin-right: 2%;
  margin-bottom: 2.5%;
  vertical-align: top;
  width: 18%;
}

a {
  font: 12px/1.5 Microsoft YaHei;
  font-family: "Microsoft Yahei UI", "Microsoft Yahei", "Helvetica Neue", Helvetica, "Nimbus Sans L", Arial, "Liberation Sans", "Hiragino Sans GB", "Microsoft YaHei", "Wenquanyi Micro Hei", "WenQuanYi Zen Hei", "ST Heiti", SimHei, "WenQuanYi Zen Hei Sharp", sans-serif;
  font-size: 0;
  list-style: none;
  word-wrap: break-word;
  color: #333;
  text-decoration: none;
  display: block;
}

.titlepic {
  font-family: "Microsoft Yahei UI", "Microsoft Yahei", "Helvetica Neue", Helvetica, "Nimbus Sans L", Arial, "Liberation Sans", "Hiragino Sans GB", "Microsoft YaHei", "Wenquanyi Micro Hei", "WenQuanYi Zen Hei", "ST Heiti", SimHei, "WenQuanYi Zen Hei Sharp", sans-serif;
  list-style: none;
  color: #333;
  word-wrap: break-word;
  position: relative;
  margin-bottom: 5px;
  display: block;
  overflow: hidden;
  padding-top: 150%;
}

h2 {
  font-weight: 500;
  list-style: none;
  color: #333;
  word-wrap: break-word;
  padding: 0;
  font-size: 10px;
  margin: 0 0 10px;
  height: 30px;
  overflow: hidden;
}

img {
  font: 12px/1.5 Microsoft YaHei;
  font-family: "Microsoft Yahei UI", "Microsoft Yahei", "Helvetica Neue", Helvetica, "Nimbus Sans L", Arial, "Liberation Sans", "Hiragino Sans GB", "Microsoft YaHei", "Wenquanyi Micro Hei", "WenQuanYi Zen Hei", "ST Heiti", SimHei, "WenQuanYi Zen Hei Sharp", sans-serif;
  font-size: 0;
  list-style: none;
  color: #333;
  word-wrap: break-word;
  border: none;
  width: 100%;
  position: absolute;
  height: 100%;
  object-fit: cover;
  top: 0px;
}

.container {
  display: grid;
}
</style>
<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";
import router from "@/router";

let __PATH__ = window.sessionStorage.getItem("__PATH__")
let route = useRoute()
let list = ref([])
let title = ''
let go = (item) => {
  router.push('/ImagePage?url=' + title + '/' + item.title)
}
onMounted(async () => {
  title = route.query.name
  let html = await axios.get(__PATH__ + "/" + title)
  html = html.data
  const parser = new DOMParser();
  const doc = parser.parseFromString(html, "text/html");
  const divElement = doc.querySelector("pre");
  const a = divElement.querySelectorAll("a");
  let temp = []
  a.forEach(i => temp.push({
    url: __PATH__ + "/" + title + '/' + i.textContent + '/001.jpg',
    title: i.textContent.substring(0, i.textContent.length - 1)
  }));
  temp.shift()
  temp.reverse()
  list.value = temp
})
</script>

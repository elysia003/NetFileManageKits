<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component" v-if="route.meta.keepAlive === true" :key="route.meta.key"/>
    </keep-alive>
    <component :is="Component" v-if="route.meta.keepAlive !== true" :key="route.meta.key"/>
  </router-view>
</template>

<script setup>
import {useRoute} from "vue-router";

let route=useRoute()
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

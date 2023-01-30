<template>
  <div>
    <van-card
        v-for="item in list" :key="item"
        desc="描述信息"
        title="商品标题"
        thumb="{{ imageURL }}"
    />
  </div>
</template>
<script>

import {getFileList} from "@/api/file";
export default {
  name: 'file',
  data() {
    return {
      files:[]
    }
  },
  created() {
    getFileList({basePath:'C:/PCxi尔薇3.0版本解压码1024'}).then((response) => {
      this.files = response.data
    })
  },
}
</script>

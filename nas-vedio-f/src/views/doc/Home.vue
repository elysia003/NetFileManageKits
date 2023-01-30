<template>
  <div id="doc">
    <van-search
        v-model="searchBean.key"
        show-action="true"
        placeholder="请输入搜索关键词"
        @search="onSearch"
    >
      <template #action>
        <div @click="onSearch">搜索</div>
      </template>
    </van-search>
    <van-list
    >
      <van-cell v-for="item in list" :key="item" style="background-color: #E6E6E6;">
        <div style="background-color: #FFFFFF;padding: 10px;margin: -5px">
          <router-link :to="'/read/'+item.id" v-html="item.highlightFields.title[0]"></router-link>
          <br>
          <p v-html="item.highlightFields.context"></p>
        </div>
      </van-cell>
    </van-list>
    <van-pagination v-model="searchBean.pageNum" :total-items="total" :items-per-page="searchBean.pageSize"
                    @change="onSearch" force-ellipses="true"/>
  </div>
</template>
<script>
import {DOC_API} from "@/components/RES_BASE_URL";
import {find} from "@/api/doc_api";

export default {
  name: 'doc',
  data() {
    return {
      list: [],
      total: 0,
      searchBean: {
        key: "",
        pageNum: 1,
        pageSize: 20
      }
    };
  },
  created() {
    console.log(DOC_API)
  },
  methods: {
    onSearch() {
      find(this.searchBean).then((response) => {
        this.list = response.data.searchHits
        this.total = response.data.totalHits
        console.log(this.list)
      })
    },
  }
}
</script>

<template>
  <div>
    <h1 v-html="this.bean.title"></h1>
    <h4 align="left" v-html="this.bean.keywords" ></h4>
    <p align="left" v-html="this.bean.context"></p>
  </div>
</template>
<script>
import {getById} from "@/api/doc_api";
export default {
  name:'read',
  data(){
    return {
      bean:{},
      id:""
    }
  },
  created() {
    this.id = this.$route.params.id
    getById({id:this.id}).then((response)=>{
      this.bean=response.data
      this.bean.context=this.bean.context.replace(/\n/g,'').replace(/\t/g,'<br>')
      console.log(this.bean.context)
    })
  },
  methods: {
    backkk(){
      console.log(this.$router.back())
    }
  }
}
</script>

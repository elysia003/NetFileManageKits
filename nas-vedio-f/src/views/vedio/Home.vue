<template>
  <div class="home">
    <van-popup v-model:show="vEdit" :style="{ width: '90%'}">
      <br>
      <br>
      <van-field v-model="editTitleValue" placeholder="请输入标题"/>
      <br>
      <van-button @click="saveTitle" type="primary">添加</van-button>
      <br><br>
      <van-button @click="reGen" :loading="genning" type="primary" loading-text="生成中...">重新生成</van-button>
      <br><br>
    </van-popup>
    <van-popup v-model:show="vTag" :style="{ width: '90%'}">
      <br>
      <van-tag style="margin-right:1%" closeable v-for="tag in item.tags" :key="tag" color="#CCCCCC" size="medium"
               type="success" @close="close">
        {{ tag }}
      </van-tag>
      <van-field v-model="addTagValue" placeholder="请输入Tag"/>
      <br>
      <van-button @click="saveTag" type="primary">保存</van-button>
      <br><br>
    </van-popup>
    <van-popup v-model:show="vSetting" :style="{ width: '50%'}">
      <van-radio-group v-model="config.IMG" direction="horizontal">
        <br>
        <br>
        <van-radio name="99.jpg">9图</van-radio>
        <van-radio name="1616.jpg">16图</van-radio>
        <van-field v-model="pageSize" label="pageSize:" placeholder="pageSize"/>
      </van-radio-group>
      <van-button @click="saveSetting" type="primary">确定</van-button>
      <br><br>
    </van-popup>
    <van-popup v-model:show="vNew" :style="{ width: '90%'}">
      <van-loading v-if="vl" size="24px">加载中...</van-loading>
      <van-checkbox-group v-model="checked" ref="checkboxGroup">
        <van-checkbox v-for="item in newList" :key="item" :name="item">{{ item.filePath }}</van-checkbox>
      </van-checkbox-group>
      <van-button type="primary" @click="checkAll">全选</van-button>
      <van-button @click="saveNews" type="primary" :loading="genning">全部入库</van-button>
      <van-button type="primary" @click="toggleAll">反选</van-button>
      <br><br>
    </van-popup>
    <van-popup v-model:show="vGen" :style="{ width: '90%'}">
      <van-loading v-if="vGl" size="24px">加载中...</van-loading>
      <van-checkbox-group v-model="checked" ref="checkboxGroup">
        <van-checkbox v-for="item in newList" :key="item" :name="item">{{ item.imagePath }}{{
            item.filePath
          }}
        </van-checkbox>
      </van-checkbox-group>
      <van-button type="primary" @click="checkAll">全选</van-button>
      <van-button @click="r_gens" type="primary" :loading="genning">全部生成</van-button>
      <van-button type="primary" @click="toggleAll">反选</van-button>
      <br><br>
    </van-popup>
    <van-tabs v-model:active="activeName" animated @change="change" sticky>
      <van-tab title="All" name="/all">
      </van-tab>
      <van-tab v-for="item in prefixs" :key="item" :title="item" :name="item">
      </van-tab>
    </van-tabs>
    <van-list
    >
      <van-cell v-for="item in list" :key="item" style="background-color: #E6E6E6;">
        <div style="background-color: #FFFFFF;padding: 10px;margin: -5px">
          <a :href="RES_BASE_URL+item.filePath"
             style="color:#6E6E6E;font-size: 16px ">{{ item.title }}
          </a>
          <van-icon @click="editTitle(item)" name="weapp-nav" size="1rem"/>
          <van-image width="100%" :src="IMG_BASE_URL+item.imagePath+'/'+config.IMG"></van-image>
          <van-tag style="margin-right:1%" closeable v-for="tag in item.tags" :key="tag" color="#CCCCCC" size="medium"
                   type="success" @close="deletetag(item,tag)">
            {{ tag }}
          </van-tag>
          <van-tag @click="addTag(item)" style="margin-right:1%" color="#CCCCCC" size="medium" type="success"
                   @close="close">
            {{ item.filePath }}
          </van-tag>
          <van-tag @click="addTag(item)" style="margin-right:1%" color="#CCCCCC" size="medium" type="success"
                   @close="close">
            新增
          </van-tag>
        </div>
      </van-cell>
    </van-list>
    <van-tabbar>
    <van-pagination v-model="pageNum" :total-items="total" :items-per-page="pageSize" @change="onLoad"
                    force-ellipses="true"/>
    </van-tabbar>
    <van-sticky :offset-bottom="100" style="float: right;margin-right: 20px" position="bottom">
      <van-popover v-model:show="showPopover" placement="top">
        <van-grid
            square
            clickable
            :border="false"
            column-num="1"
            style="width: 80px;height: 450px"
        >
          <van-grid-item style="float: bottom;">
            <van-button type="primary" icon="back-top" @click="top" round></van-button>
          </van-grid-item>
          <van-grid-item style="float: bottom;">
            <van-button type="primary" icon="search" round></van-button>
          </van-grid-item>
          <van-grid-item style="float: bottom;">
            <van-button type="primary" @click="onLoad" icon="replay" round></van-button>
          </van-grid-item>
          <van-grid-item style="float: bottom;">
            <van-button type="primary" @click="setting" icon="setting-o" round></van-button>
          </van-grid-item>
          <van-grid-item style="float: bottom;">
            <van-button type="primary" @click="newvedio" icon="new-o" round></van-button>
          </van-grid-item>
          <van-grid-item style="float: bottom;">
            <van-button type="primary" @click="genvedio" icon="new-o" round></van-button>
          </van-grid-item>
        </van-grid>
        <template #reference>
          <van-button type="primary" icon="plus" round></van-button>
        </template>
      </van-popover>
    </van-sticky>
  </div>
</template>
<script>
import {search, edittitle, addtag, getById, deletetag, scan, newVedio, regen, scan2, gens,getPerfix} from "@/api/res";
import {RES_BASE_URL, IMG_BASE_URL} from "@/components/RES_BASE_URL";
import {ref} from 'vue';
import {Toast} from 'vant';
import {Dialog} from 'vant';

export default {
  name: 'vedio',
  setup() {
    const showPopover = ref(false);
    return {showPopover};
  },
  data() {
    return {
      activeName:'vedio',
      genning: false,
      vl: false,
      newList: [],
      checked: [],
      config: {
        IMG: '99.jpg'
      },
      vNew: false,
      vSetting: false,
      addTagValue: '',
      item: {},
      vTag: false,
      editTitleValue: '',
      id: '',
      vEdit: false,
      lt: "加载中...",
      RES_BASE_URL: RES_BASE_URL,
      IMG_BASE_URL: IMG_BASE_URL,
      pageNum: 0,
      pageSize: 20,
      total: 0,
      show: true,
      list: [],
      loading: true,
      finished: false,
      refreshing: false,
      vGen: false,
      vGl: false,
      form:{},
      prefixs:[]
    };
  },
  created() {
    getPerfix().then((response)=>{
      this.prefixs=response.data
      this.onLoad()
    })

  },
  methods: {
    change(){
      this.pageNum=1
      this.onRefresh()
    },
    saveNews() {
      this.genning = true
      newVedio(this.checked).then(() => {
        this.genning = false
        this.vNew = false
        this.vl = false
      });
    },
    newvedio() {
      this.newList = []
      this.vNew = true
      this.vl = true
      scan({prefix:this.activeName}).then((response) => {
        this.newList = response.data
        this.vl = false
      })
    },
    f1() {
      var that = this
      var i=0
      for (i = 0; i < this.checked.length; i++) {
        if (this.checked[i].imagePath == "NO_IMAGE") {
          break
        }
      }
      if(i==this.checked.length){
        this.genning=false
        return
      }
      that.checked[i].imagePath = "GENNING!!"
      gens([this.checked[i]]).then(() => {
        that.checked[i].imagePath = "FINISHED!!"
        that.f1()
      })
    },
    r_gens() {
      this.genning = true
      this.f1()
    },
    checkAll() {
      this.$refs.checkboxGroup.toggleAll(true);
    },
    toggleAll() {
      this.$refs.checkboxGroup.toggleAll();
    },
    genvedio() {
      this.newList = []
      this.vGen = true
      this.vGl = true
      scan2().then((response) => {
        this.newList = response.data
        this.vGl = false
      })
    },
    deletetag(item, tag) {
      let that = this
      Dialog.confirm({
        title: '确定删除？',
        message: tag,
      })
          .then(() => {
            deletetag({"id": item.id, "tag": tag}).then((response) => {
              response
              that.resById(item.id)
            })
            // on confirm
          })
          .catch(() => {
            // on cancel
          });
    },
    saveSetting() {
      this.vSetting = false
      this.onLoad()
    },
    setting() {
      this.vSetting = true
    },
    resById(id) {
      getById({"id": id}).then((response) => {
        let vedio = response.data
        for (let i = 0; i < this.list.length; i++) {
          if (this.list[i].id == vedio.id) {
            this.list[i] = vedio
          }
        }
      })
    },
    saveTag() {
      addtag({id: this.id, addTagValue: this.addTagValue}).then(((response) => {
        if (parseInt(response.data) > 0) {
          this.resById(this.id)
          Toast.success('添加成功');
          this.vTag = false
        }
      }))
    },
    addTag(item) {
      console.log(item)
      this.id = item.id
      this.item = item
      this.vTag = true
    },
    saveTitle() {
      edittitle({id: this.id, editTitleValue: this.editTitleValue}).then(((response) => {
        if (parseInt(response.data) > 0) {
          this.resById(this.id)
          this.vEdit = false
          Toast.success('修改成功');
        }
      }))
    },
    reGen() {
      this.genning = true
      regen({id: this.id}).then(((response) => {
        this.genning = false
        this.vEdit = false
        Toast.success(response)
      }))
    },
    editTitle(item) {
      console.log(item)
      this.editTitleValue = item.title
      this.id = item.id
      this.vEdit = true
    },
    top() {
      let k = document.documentElement.scrollTop / 100
      setTimeout(() => {
        this.top2(k)
      }, 10)
    },
    top2(i) {
      setTimeout(() => {
        document.documentElement.scrollTop = document.documentElement.scrollTop - i
        if (document.documentElement.scrollTop > 10) {
          this.top2(i)
        }
      }, 5)
    },
    close() {
      this.show = false;
    },
    onLoad() {
      this.loading = true
      this.list = []
      search({prefix:this.activeName,pageNum: this.pageNum, pageSize: this.pageSize}).then((response) => {
        this.list = response.data.records
        this.total = response.data.total
      })
    },
    onRefresh() {
      this.finished = false;
      this.loading - true;
      this.onLoad()
    }
  },
};
</script>

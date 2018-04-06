<template>
  <div>
    <div class="item" v-for="item in list">
      <div class="pic">
        <Icon type="ios-bookmarks-outline" :size="60"></Icon>
      </div>
      <div class="info">
        <div class="title">
          <a href="javascript:" @click="toDetail(item.id)">{{item.title}}</a>
          <Tag style="margin-left: 10px;">{{item.tags}}</Tag>
        </div>
        <div class="text">
          <Icon type="university"></Icon> {{item.collegeName}}
        </div>
        <div class="text">
          <Icon type="android-calendar"></Icon> {{formatDate(item.startTime)}} 开课
          <Icon type="android-time" style="margin-left: 30px;"></Icon>
          {{item.weeks}} 周，每周 {{item.period}} 课时
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      list: []
    }
  },
  mounted () {
    api('course/list', {}).then(result => {
      if (result) {
        this.list = result
      }
    }).catch(reason => {
      this.$Message.error(reason)
    })
  },
  methods: {
    formatDate (time) {
      let date = new Date(time * 1000)
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
    },
    toDetail (id) {
      this.$router.push(`/detail/${id}`)
    }
  }
}
</script>

<style lang="less" scoped>
.item {
  display: flex;
  margin: 20px;

  .pic {
    background-color: #eee;
    padding: 20px 60px;

    .ivu-icon {
      color: #ccc;
    }
  }

  .info {
    flex: 1;
    margin-left: 20px;

    .title {
      font-size: 24px;
    }

    .text {
      margin-top: 4px;
    }
  }
}
</style>

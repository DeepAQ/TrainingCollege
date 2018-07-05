<template>
  <div>
    <h2>消费分析</h2>
    <div>
      选择分析周期：
      <DatePicker v-model="startTime" type="month" placeholder="起始时间"></DatePicker>
      -
      <DatePicker v-model="endTime" type="month" placeholder="结束时间"></DatePicker>
      <Button type="primary" @click="loadData">更新数据</Button>
    </div>
    <h3>总体消费情况</h3>
    <div class=""></div>
    <h3>每月消费趋势</h3>
    <h3>消费课程对比</h3>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      startTime: new Date(new Date() - 6 * 30 * 24 * 3600000),
      endTime: new Date(),
      anal: {}
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      console.log('test')
      api('anal/student/consumption', {
        start: Math.floor(this.startTime.getTime() / 1000) + '',
        end: Math.floor(this.endTime.getTime() / 1000) + ''
      }).then(result => {
        if (result) {
          this.anal = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  }
}
</script>

<style lang="less" scoped>
h2, h3 {
  margin: 10px 0;
}
</style>

<template>
  <div>
    <h2>学习分析</h2>
    <div>
      选择分析周期：
      <DatePicker v-model="startTime" type="month" placeholder="起始时间"></DatePicker>
      -
      <DatePicker v-model="endTime" type="month" placeholder="结束时间"></DatePicker>
      <Button type="primary" @click="loadData">开始分析</Button>
    </div>
    <h3>总体上课情况</h3>
    <div>参加总课时数：<span style="font-size: 24px;">{{anal.total}}</span></div>
    <div>总体退课率：<span style="font-size: 24px;">{{Math.floor(anal.cancelRate * 10000) / 100}}%</span></div>
    <h3>每月参加课时数趋势</h3>
    <ve-line :data="chartData1" width="1100px"></ve-line>
    <h3>参加课时数对比</h3>
    <div style="display: flex;">
      <ve-pie :data="chartData2" width="560px"></ve-pie>
      <ve-pie :data="chartData3" width="560px"></ve-pie>
    </div>
  </div>
</template>

<script>
import api from '@/api'
import flatmap from '@/flatmap'

export default {
  data () {
    return {
      startTime: new Date(new Date() - 6 * 30 * 24 * 3600000),
      endTime: new Date(),
      anal: {},
      chartData1: {
        columns: [0, 1],
        rows: []
      },
      chartData2: {
        columns: [0, 1],
        rows: []
      },
      chartData3: {
        columns: [0, 1],
        rows: []
      }
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('anal/student/study', {
        start: Math.floor(this.startTime.getTime() / 1000) + '',
        end: Math.floor(this.endTime.getTime() / 1000) + ''
      }).then(result => {
        if (result) {
          this.anal = result
          this.chartData1.rows = flatmap(result.monthly)
          this.chartData2.rows = flatmap(result.byCourse)
          this.chartData3.rows = flatmap(result.byTags)
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

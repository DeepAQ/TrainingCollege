<template>
  <div>
    <h2>收入分析</h2>
    <div>
      选择分析周期：
      <DatePicker v-model="startTime" type="month" placeholder="起始时间"></DatePicker>
      -
      <DatePicker v-model="endTime" type="month" placeholder="结束时间"></DatePicker>
      <Button type="primary" @click="loadData">开始分析</Button>
    </div>
    <h3>总体收入情况</h3>
    <div>周期订单数：<span style="font-size: 24px;">{{anal.orders}}</span></div>
    <div>周期总收入：<span style="font-size: 24px;">¥ {{anal.total / 100}}</span></div>
    <div>平均订单价格：<span style="font-size: 24px;">¥ {{anal.avgPrice / 100}}</span></div>
    <div>周期订单成交率：<span style="font-size: 24px;">{{Math.floor(anal.finishRate * 10000) / 100}}%</span></div>
    <h3>线上 / 线下收入对比</h3>
    <ve-pie :data="chartData4" width="1100px"></ve-pie>
    <h3>每月订单数趋势</h3>
    <ve-line :data="chartData5" width="1100px"></ve-line>
    <h3>每月收入趋势</h3>
    <ve-line :data="chartData1" width="1100px"></ve-line>
    <h3>机构 / 类别订单数对比</h3>
    <div style="display: flex;">
      <ve-pie :data="chartData6" width="560px"></ve-pie>
      <ve-pie :data="chartData7" width="560px"></ve-pie>
    </div>
    <h3>机构 / 类别收入对比</h3>
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
      },
      chartData4: {
        columns: [0, 1],
        rows: []
      },
      chartData5: {
        columns: [0, 1],
        rows: []
      },
      chartData6: {
        columns: [0, 1],
        rows: []
      },
      chartData7: {
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
      api('anal/manager/income', {
        start: Math.floor(this.startTime.getTime() / 1000) + '',
        end: Math.floor(this.endTime.getTime() / 1000) + ''
      }).then(result => {
        if (result) {
          this.anal = result
          this.chartData1.rows = flatmap(result.monthlyTotal, 0.01)
          this.chartData2.rows = flatmap(result.byCollege, 0.01)
          this.chartData3.rows = flatmap(result.byTags, 0.01)
          this.chartData4.rows = [['线上', result.onlineTotal / 100], ['线下', result.offlineTotal / 100]]
          this.chartData5.rows = flatmap(result.monthlyOrders)
          this.chartData6.rows = flatmap(result.ordersByCollege)
          this.chartData7.rows = flatmap(result.ordersByTags)
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

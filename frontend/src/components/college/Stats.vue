<template>
  <div>
    <h2>财务概况</h2>
    <div style="margin: 10px 0;">
      已结算收入：<span style="font-size: 24px;">¥ {{stats.settledIncome / 100}}</span>
    </div>
    <h2>结算统计</h2>
    <Table stripe :columns="cols" :data="stats.settlements" style="margin-top: 10px;"></Table>
    <h2>课程统计</h2>
    <Table stripe :columns="cols2" :data="stats.courseStats" style="margin-top: 10px;"></Table>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      cols: [
        { title: '结算时间', render: (h, p) => h('div', `${new Date(p.row.time * 1000)}`) },
        { title: '原始收入', width: 200, render: (h, p) => h('div', `¥ ${p.row.origAmount / 100}`) },
        { title: '结算收入', width: 200, render: (h, p) => h('div', `¥ ${p.row.realAmount / 100}`) }
      ],
      cols2: [
        { title: '课程', render: (h, p) => h('router-link', {
              props: { to: `/detail/${p.row.id}` }
        }, p.row.name)},
        { title: '报名人数', width: 140, key: 'totalCount' },
        { title: '退订人数', width: 140, key: 'cancelCount' },
        { title: '总收入', width: 140, render: (h, p) => h('div', `¥ ${p.row.totalAmount / 100}`) },
      ],
      stats: {}
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('college/stats', {}).then(result => {
        if (result) {
          this.stats = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  }
}
</script>

<style lang="less" scoped>
h2 {
  margin-top: 10px;
}
</style>

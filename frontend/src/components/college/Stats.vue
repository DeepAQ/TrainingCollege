<template>
  <div>
    <h2>财务概况</h2>
    <div style="margin: 10px 0;">
      已结算收入：<span style="font-size: 24px;">¥ {{stats.settledIncome / 100}}</span>
    </div>
    <h2>结算统计</h2>
    <Table stripe :columns="cols" :data="stats.settlements" style="margin-top: 10px;"></Table>
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
      stats: {}
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('settlement/stats', {}).then(result => {
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

</style>

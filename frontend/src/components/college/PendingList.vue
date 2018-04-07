<template>
  <div>
    <h2>待配班订单</h2>
    <Table stripe :columns="cols" :data="orders"></Table>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      cols: [
        { title: '订单序号', key: 'id', width: 100 },
        { title: '课程名称', key: 'courseName' },
        { title: '人数', key: 'count', width: 60 },
        {
          title: '总价', width: 100,
          render: (h, p) => {
            return h('div', `¥ ${p.row.origPrice / 100}`)
          }
        },
        {
          title: '操作', width: 100,
          render: (h, p) => {
            return h('div', [
              h('Button', {
                props: { type: 'primary', size: 'small' },
                on: {
                  click: () => {
                    this.allotOrder(p.row.id)
                  }
                }
              }, '配班')
            ])
          }
        },
      ],
      orders: []
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('order/pending', {}).then(result => {
        if (result) {
          this.orders = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    allotOrder (id) {
      api('order/allot', { orderId: id }).then(result => {
        this.$Message.info('配班成功')
        this.loadData()
      }).catch(reason => {
        this.$Message.error(reason)
        this.loadData()
      })
    }
  }
}
</script>

<style lang="less" scoped>
h2, .ivu-row {
  padding: 10px 0;
}
</style>

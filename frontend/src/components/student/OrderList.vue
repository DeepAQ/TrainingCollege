<template>
  <div>
    <h2>订单列表</h2>
    <Table stripe :columns="cols" :data="orders"></Table>
    <PayOrder ref="pay" @update="loadData"/>
  </div>
</template>

<script>
import api from '@/api'
import PayOrder from './PayOrder'

export default {
  components: { PayOrder },
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
          title: '状态', width: 100,
          render: (h, p) => {
            switch (p.row.status) {
              case 'NOT_PAID':
                return h('div', '待支付')
              case 'PAID':
                return h('div', '已支付')
              case 'CANCELLED':
                return h('div', '已取消')
              case 'CLOSED':
                return h('div', '交易关闭')
            }
          }
        },
        {
          title: '操作', width: 140,
          render: (h, p) => {
            switch (p.row.status) {
              case 'NOT_PAID':
                return h('div', [
                  h('Button', {
                    props: { type: 'primary', size: 'small' },
                    on: {
                      click: () => {
                        this.$refs.pay.origPrice = p.row.origPrice
                        this.$refs.pay.orderId = p.row.id
                        this.$refs.pay.show = true
                      }
                    }
                  }, '支付'),
                  h('Button', {
                    props: { type: 'error', size: 'small' },
                    style: { marginLeft: '5px' },
                    on: {
                      click: () => {
                      }
                    }
                  }, '取消'),
                ])
              case 'PAID':
                return h('div', [
                  h('Button', {
                    props: { type: 'error', size: 'small' },
                    on: {
                      click: () => {
                      }
                    }
                  }, '退课')
                ])
              default:
                return h('div', '')
            }
          }
        },
      ],
      orders: []
    }
  },
  computed: {
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('order/my', {}).then(result => {
        if (result) {
          this.orders = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    cancelOrder (id) {
      
    }
  }
}
</script>

<style lang="less" scoped>
h2, .ivu-row {
  padding: 10px 0;
}
</style>

<template>
  <div>
    <h2>财务结算</h2>
    <Table stripe :columns="cols" :data="settlements" style="margin-top: 10px;"></Table>
    <Modal v-model="show" width="400" @on-ok="submit">
      <p slot="header">结算</p>
      <div>
        需结算收入：¥ {{(selected.totalIncome - selected.settledIncome) / 100}}
      </div>
      <div style="margin-top: 10px;">
        结算比例：<InputNumber :max="1" :min="0" :step="0.1" v-model="ratio"></InputNumber>
      </div>
      <div style="margin-top: 10px;">
        机构获得收入：¥ {{Math.floor((selected.totalIncome - selected.settledIncome) * ratio) / 100}}
      </div>
    </Modal>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      cols: [
        { title: '机构名称', key: 'collegeName' },
        { title: '总收入', width: 200, render: (h, p) => h('div', `¥ ${p.row.totalIncome / 100}`) },
        { title: '已结算收入', width: 200, render: (h, p) => h('div', `¥ ${p.row.settledIncome / 100}`) },
        {
          title: '操作', width: 100,
          render: (h, p) => {
            return h('div', [
              h('Button', {
                props: { type: 'primary', size: 'small', disabled: p.row.settledIncome >= p.row.totalIncome },
                on: {
                  click: () => {
                    this.selected = p.row
                    this.show = true
                  }
                }
              }, '结算')
            ])
          }
        }
      ],
      settlements: [],
      selected: {},
      ratio: 1,
      show: false
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('settlement/colleges', {}).then(result => {
        if (result) {
          this.settlements = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    submit () {
      api('settlement/add', {
        collegeId: this.selected.collegeId,
        origAmount: this.selected.totalIncome - this.selected.settledIncome,
        ratio: this.ratio * 10
      }).then(result => {
        this.$Message.info('结算成功')
        this.loadData()
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>

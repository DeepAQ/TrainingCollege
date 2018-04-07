<template>
  <div>
    <h2>余额与积分</h2>
    <div>账户余额：<span style="font-size: 24px;">¥ {{wallet.balance / 100}}</span></div>
    <div>积分：<span style="font-size: 24px;">{{wallet.points}}</span></div>
    <h2>消费与等级</h2>
    <div>总消费：<span style="font-size: 24px;">¥ {{wallet.consumption / 100}}</span></div>
    <div>折扣幅度：<span style="font-size: 24px;">{{wallet.discount}} %</span></div>
    <h2>积分兑换</h2>
    <div>
      兑换
      <InputNumber :max="99999" :min="1" v-model="count"></InputNumber>
      积分，可获得 {{count / 100}} 元余额。
    </div>
    <Button type="primary" style="margin-top: 10px;" @click="exchange">兑换</Button>
    <h2>积分与等级规则</h2>
    <div style="padding-left: 20px;">
      <ul>
        <li>每消费 1 元可获取 1 个积分。</li>
        <li>每 100 积分可兑换 1 元余额。</li>
        <li>若有取消订单，积分也将相应扣除。</li>
        <li>每累计消费 100 元即可获得 5% 折扣，封顶为 50%。</li>
      </ul>
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      wallet: {},
      count: 1
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('student/wallet', {}).then(result => {
        if (result) {
          this.wallet = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    exchange () {
      api('student/wallet/exchange', { count: this.count }).then(result => {
        this.$Message.info('兑换成功')
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
h2 {
  margin: 10px 0;
}
</style>

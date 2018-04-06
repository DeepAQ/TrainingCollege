<template>
  <div>
    <Modal v-model="show" width="400">
      <p slot="header">订单支付</p>
      <div>
        <div style="text-align: center; font-size: 48px;">
          ¥ {{origPrice / 100}}
        </div>
        <div style="margin: 10px 0; font-size: 14px;">
          为确保安全，请输入账户密码完成支付：
        </div>
        <div>
          <Input v-model="password" placeholder="密码" type="password" size="large"/>
        </div>
      </div>
      <div slot="footer" style="text-align: left;">
        <Button size="large" type="success" style="width: 100%;" @click="submit">支付</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      show: false,
      orderId: 0,
      origPrice: 0,
      password: ''
    }
  },
  watch: {
    show () {
      if (this.show) {
        this.loadData()
      }
    }
  },
  methods: {
    loadData() {
      if (this.orderId > 0) {
        
      }
    },
    submit () {
      api('order/pay', { orderId: this.orderId, password: this.password }).then(result => {
        this.$Message.info('支付成功')
        this.show = false
        this.$emit('update')
      }).catch(reason => {
        this.$Message.error(reason)
        this.show = false
        this.$emit('update')
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>

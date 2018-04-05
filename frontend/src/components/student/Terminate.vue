<template>
  <div>
    <h2>注销账户</h2>
    <Alert type="warning" show-icon>
      注意！
      <template slot="desc">
        注销账户后，您的订单、课程等数据不会删除，但您将无法再购买新课程。该操作不可逆转。
      </template>
    </Alert>
    <div>
      <Checkbox size="large" v-model="ack">
        我已充分了解注销账户的后果。
      </Checkbox>
    </div>
    <div style="margin-top: 10px;">
      <Button size="large" type="error" :disabled="!ack" @click="doTerminate">
        确认注销账户
      </Button>
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      ack: false
    }
  },
  methods: {
    doTerminate () {
      api('student/terminate', {}).then(result => {
        window.$state.logout()
        window.location = '/'
      }).catch(reason => {
        this.$Message.error(reason)
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

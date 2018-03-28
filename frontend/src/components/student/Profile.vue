<template>
  <div>
    <h2>个人资料</h2>
    <Row>
      <Col span="3"><Icon type="android-mail"></Icon> E-mail</Col>
      <Col>{{ profile.email }}</Col>
    </Row>
    <Row>
      <Col span="3"><Icon type="person"></Icon> 姓名</Col>
      <Col>{{ profile.name }}</Col>
    </Row>
    <Row>
      <Col span="3"><Icon type="unlocked"></Icon> 账户状态</Col>
      <Col>{{ status }}</Col>
    </Row>
  </div>
</template>

<script>
import api from '@/api'

export default {
  name: 'StudentProfile',
  data () {
    return {
      profile: {}
    }
  },
  computed: {
    status () {
      switch (this.profile.status) {
        case 'NOT_VERIFIED':
          return '未验证用户'
        case 'VERIFIED':
          return '已验证用户'
        case 'TERMINATED':
          return '已注销用户'
      }
    }
  },
  mounted () {
    api('student/profile', {}).then(result => {
      if (result) {
        this.profile = result
      }
    }).catch(reason => {
      this.$Message.error(reason)
    })
  }
}
</script>

<style lang="less" scoped>
h2, .ivu-row {
  padding: 10px 0;
}
</style>

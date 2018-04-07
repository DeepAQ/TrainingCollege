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
    <h2>密码修改</h2>
    <div>
      <div><Input v-model="password" type="password" placeholder="当前密码"/></div>
      <div><Input v-model="newPassword" type="password" placeholder="新密码"/></div>
      <div><Input v-model="newPassword2" type="password" placeholder="重复新密码"/></div>
      <Button type="primary" @click="modifyPassword">修改</Button>
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      profile: {},
      password: '',
      newPassword: '',
      newPassword2: ''
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
  },
  methods: {
    modifyPassword () {
      if (this.newPassword != this.newPassword2) {
        this.$Message.error('新密码不一致')
        return
      }
      api('student/modifypassword', {
        oldPassword: this.password,
        newPassword: this.newPassword
      }).then(result => {
        this.$Message.info('密码修改成功')
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

.ivu-icon {
  width: 16px;
}

.ivu-input-wrapper {
  width: 300px;
  margin-bottom: 10px;
}
</style>

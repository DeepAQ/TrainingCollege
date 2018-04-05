<template>
  <div>
    <Dropdown placement="bottom-end" trigger="click" @on-click="menuClick">
      <a href="javascript:">
        <span v-if="loggedIn">{{ loginName }} ({{ loginType }})</span>
        <span v-else>游客</span>
        <Icon type="arrow-down-b"></Icon>
      </a>
      <DropdownMenu slot="list" v-if="loggedIn">
        <DropdownItem name="logout">退出登录</DropdownItem>
      </DropdownMenu>
      <DropdownMenu slot="list" v-else>
        <DropdownItem name="login">登录</DropdownItem>
        <DropdownItem name="register">注册</DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <Modal v-model="modalLogin" width="400">
      <p slot="header">用户登录</p>
      <div style="text-align: center;">
        <RadioGroup v-model="userType" size="large" type="button">
          <Radio label="Student">学员登录</Radio>
          <Radio label="College">机构登录</Radio>
          <Radio label="Manager">经理登录</Radio>
        </RadioGroup>
        <Input v-model="user" size="large" :placeholder="loginPrompt" />
        <Input v-model="pass" size="large" type="password" placeholder="密码" />
      </div>
      <div slot="footer">
        <Button type="primary" size="large" :loading="loading" @click="loginClick">
          登录
        </Button>
      </div>
    </Modal>
    <Modal v-model="modalRegister" width="400">
      <p slot="header">用户注册</p>
      <div style="text-align: center;">
        <RadioGroup v-model="userType" size="large" type="button">
          <Radio label="Student">学员注册</Radio>
          <Radio label="College">机构注册</Radio>
        </RadioGroup>
        
        <Input v-model="user" size="large" type="email" placeholder="E-mail" v-if="userType == 'Student'" />
        <Input v-model="name" size="large" placeholder="姓名" v-if="userType == 'Student'" />
        
        <Input v-model="name" size="large" placeholder="机构名称" v-if="userType == 'College'" />
        <Input v-model="location" size="large" placeholder="机构地点" v-if="userType == 'College'" />
        <Input v-model="description" type="textarea" placeholder="机构简介" v-if="userType == 'College'" />
        
        <Input v-model="pass" size="large" type="password" placeholder="密码" />
        <Input v-model="pass2" size="large" type="password" placeholder="确认密码" />
      </div>
      <div slot="footer">
        <Button type="primary" size="large" :loading="loading" @click="regClick">
          注册
        </Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import api from '@/api'

export default {
  name: "UserInfo",
  data() {
    return {
      modalLogin: false,
      modalRegister: false,
      userType: 'Student',
      user: '',
      pass: '',
      pass2: '',
      name: '',
      location: '',
      description: '',
      loading: false
    };
  },
  computed: {
    loggedIn () {
      return window.$state.loggedIn
    },
    loginName () {
      return window.$state.loginName
    },
    loginType () {
      switch (window.$state.loginType) {
        case 'Student':
          return '学员'
        case 'College':
          return '机构'
        case 'Manager':
          return '经理'
      }
    },
    loginPrompt () {
      switch (this.userType) {
        case 'Student':
          return 'E-mail'
        case 'College':
          return '机构编号'
        case 'Manager':
          return '用户名'
      }
    }
  },
  methods: {
    menuClick (name) {
      switch (name) {
        case 'login':
          this.userType = 'Student'
          this.user = this.pass = ''
          this.modalLogin = true
          break
        case 'register':
          this.userType = 'Student'
          this.user = this.pass = this.pass2 = this.name = this.location = this.description = ''
          this.modalRegister = true
          break
        case 'logout':
          window.$state.logout()
          window.location = '/'
      }
    },
    processLogin (path, data) {
      return api(path, data).then(result => {
        localStorage.token = result.token
        if (result.needVerify) {
          this.$Modal.confirm({
            title: '提示',
            content: '该账号尚未激活，请前往邮箱接收用户激活邮件，并根据邮件指示激活账号。',
            cancelText: '重发激活邮件',
            onCancel: () => {
              api('auth/student/resend', {}).then(result => {
                this.$Message.info('已请求重发激活邮件')
              }).catch(reason => {
                this.$Message.error(reason)
              })
            }
          });
        } else {
          window.$state.login(result.token, result.displayName, result.userType)
          this.$Message.info('登录成功')
        }
        this.modalLogin = false
      }).catch(reason => {
        this.$Message.error(reason)
      }).then(() => {
        this.loading = false
      })
    },
    loginClick () {
      this.loading = true
      switch (this.userType) {
        case 'Student':
          this.processLogin('auth/student/login', {
            email: this.user,
            password: this.pass
          })
          break
        case 'College':
          this.processLogin('auth/college/login', {
            id: this.user,
            password: this.pass
          })
          break
        case 'Manager':
          this.processLogin('auth/manager/login', {
            username: this.user,
            password: this.pass
          })
          break
      }
    },
    regClick () {
      this.loading = true
      if (this.pass != this.pass2) {
        this.$Message.error('两次输入的密码不一致');
        return
      }
      switch (this.userType) {
        case 'Student':
          api('auth/student/register', {
            email: this.user,
            password: this.pass,
            name: this.name
          }).then(result => {
            this.$Modal.info({
              title: '提示',
              content: '注册成功，请前往邮箱接收用户激活邮件，并根据邮件指示激活账号。'
            });
            this.modalRegister = false
          }).catch(reason => {
            this.$Message.error(reason)
          }).then(() => {
            this.loading = false
          })
          break
        case 'College':
          api('auth/college/register', {
            password: this.pass,
            name: this.name,
            location: this.location,
            description: this.description
          }).then(result => {
            let id = '000000' + result;
            this.$Modal.info({
              title: '提示',
              content: `注册成功，您的机构编号为 ${id.substr(id.length - 7)}，请等待审批通过后登录。`
            });
            this.modalRegister = false
          }).catch(reason => {
            this.$Message.error(reason)
          }).then(() => {
            this.loading = false
          })
          break
      }
    }
  }
};
</script>

<style lang="less" scoped>
.ivu-dropdown {
  font-size: 14px;

  a {
    color: whitesmoke;
  }
}

.ivu-modal {
  .ivu-input-wrapper {
    margin-top: 10px;
  }

  .ivu-btn {
    width: 100%;
  }
}
</style>

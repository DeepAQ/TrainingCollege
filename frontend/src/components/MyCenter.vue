<template>
  <Layout>
    <Sider>
      <Menu width="auto" :active-name="active" v-if="loginType == 'Student'" @on-select="studentMenuSelect">
        <MenuGroup title="基本信息">
          <MenuItem name="profile">
            个人资料
          </MenuItem>
          <MenuItem name="level">
            会员级别
          </MenuItem>
          <MenuItem name="points">
            我的积分
          </MenuItem>
          <MenuItem name="terminate">
            账户注销
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="订单管理">
          <MenuItem name="orders">
            我的订单
          </MenuItem>
          <MenuItem name="classes">
            我的课程
          </MenuItem>
          <MenuItem name="stats">
            订单统计
          </MenuItem>
        </MenuGroup>
      </Menu>
    </Sider>
    <Content style="background-color: #fff; padding-left: 30px;">
      <router-view></router-view>
    </Content>
  </Layout>
</template>

<script>
export default {
  name: "MyCenter",
  data () {
    return {

    }
  },
  computed: {
    loginType () {
      return window.$state.loginType
    },
    active () {
      return this.$route.path.substring(this.$route.path.lastIndexOf('/') + 1)
    }
  },
  methods: {
    studentMenuSelect (name) {
      this.$router.push(`/my/student/${name}`)
    }
  }
}
</script>

<style lang="less" scoped>

</style>

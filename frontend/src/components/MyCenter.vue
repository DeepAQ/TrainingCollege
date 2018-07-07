<template>
  <Layout>
    <Sider style="background-color: white;">
      <Menu width="auto" :active-name="active" v-if="loginType == 'Student'" @on-select="studentMenuSelect">
        <MenuGroup title="基本信息">
          <MenuItem name="profile">
            <Icon type="person"></Icon> 个人资料
          </MenuItem>
          <MenuItem name="wallet">
            <Icon type="connection-bars"></Icon> 消费、余额与积分
          </MenuItem>
          <MenuItem name="terminate">
            <Icon type="close"></Icon> 账户注销
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="订单管理">
          <MenuItem name="orders">
            <Icon type="android-list"></Icon> 我的订单
          </MenuItem>
          <MenuItem name="records">
            <Icon type="university"></Icon> 我参加的
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="我的统计">
          <MenuItem name="anal-consumption">
            <Icon type="pie-graph"></Icon> 消费分析
          </MenuItem>
          <MenuItem name="anal-study">
            <Icon type="pie-graph"></Icon> 学习分析
          </MenuItem>
        </MenuGroup>
      </Menu>

      <Menu width="auto" :active-name="active" v-if="loginType == 'College'" @on-select="collegeMenuSelect">
        <MenuGroup title="基本信息">
          <MenuItem name="profile">
            <Icon type="person-stalker"></Icon> 机构信息
          </MenuItem>
          <MenuItem name="stats">
            <Icon type="pie-graph"></Icon> 财务统计
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="课程管理">
          <MenuItem name="courses">
            <Icon type="university"></Icon> 课程计划
          </MenuItem>
          <MenuItem name="orders">
            <Icon type="android-list"></Icon> 订单配班
          </MenuItem>
          <MenuItem name="sale">
            <Icon type="card"></Icon> 现场缴费
          </MenuItem>
          <MenuItem name="records">
            <Icon type="clipboard"></Icon> 成绩与记录
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="机构统计">
          <MenuItem name="anal-income">
            <Icon type="pie-graph"></Icon> 收入分析
          </MenuItem>
          <MenuItem name="anal-teaching">
            <Icon type="pie-graph"></Icon> 教学分析
          </MenuItem>
        </MenuGroup>
      </Menu>

      <Menu width="auto" :active-name="active" v-if="loginType == 'Manager'" @on-select="managerMenuSelect">
        <MenuGroup title="系统管理">
          <MenuItem name="permit">
            <Icon type="android-checkbox-outline"></Icon> 机构审批
          </MenuItem>
          <MenuItem name="settlements">
            <Icon type="card"></Icon> 财务结算
          </MenuItem>
        </MenuGroup>
        <MenuGroup title="平台统计">
          <MenuItem name="anal-income">
            <Icon type="pie-graph"></Icon> 收入分析
          </MenuItem>
          <MenuItem name="anal-teaching">
            <Icon type="pie-graph"></Icon> 教学分析
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
    },
    collegeMenuSelect (name) {
      this.$router.push(`/my/college/${name}`)
    },
    managerMenuSelect (name) {
      this.$router.push(`/my/manager/${name}`)
    }
  }
}
</script>

<style lang="less" scoped>
.ivu-icon {
  width: 12px;
}
</style>

<template>
  <div id="app">
    <Layout>
      <Header>
        <div class="layout-logo">
          <Icon type="university"></Icon> TrainingCollege
        </div>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-nav">
            <menu-item name="course">
              <Icon type="university"></Icon> 课程列表
            </menu-item>
            <menu-item name="my">
              <Icon type="person"></Icon> 个人中心
            </menu-item>
          </div>
        </Menu>
        <UserInfo/>
      </Header>
      <Content style="padding: 0 50px;">
        <Breadcrumb style="margin: 20px 0;">
          <BreadcrumbItem>Home</BreadcrumbItem>
          <BreadcrumbItem>Components</BreadcrumbItem>
          <BreadcrumbItem>Layout</BreadcrumbItem>
        </Breadcrumb>
        <Card>
          <router-view></router-view>
        </Card>
      </Content>
      <Footer>
        &copy; 2018 DeepAQ.
        技术构架 <a href="https://github.com/DeepAQ/Autumn" target="_blank">Autumn</a>.
      </Footer>
    </Layout>
  </div>
</template>

<script>
import UserInfo from './components/UserInfo'

export default {
  components: { UserInfo },
  name: "App"
};
</script>

<style lang="less" scoped>
#app {
  .ivu-layout-header {
    display: flex;
    align-items: center;

    .layout-logo {
      color: whitesmoke;
      font-size: 24px;
      flex: 1;
    }

    .ivu-menu {
      margin-right: 20px;
    }
  }

  .ivu-layout-footer {
    text-align: center;
  }
}
</style>

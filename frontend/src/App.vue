<template>
  <div id="app">
    <Layout>
      <Header>
        <div class="layout-logo">
          TrainingCollege
        </div>
        <Menu mode="horizontal" theme="dark" active-name="1">
          <div class="layout-nav">
            <menu-item name="1">
              <Icon type="ios-navigate"></Icon>
              Item 1
            </menu-item>
            <menu-item name="2">
              <Icon type="ios-keypad"></Icon>
              Item 2
            </menu-item>
            <menu-item name="3">
              <Icon type="ios-analytics"></Icon>
              Item 3
            </menu-item>
            <menu-item name="4">
              <Icon type="ios-paper"></Icon>
              Item 4
            </menu-item>
          </div>
        </Menu>
        <UserInfo/>
      </Header>
      <Content :style="{padding: '0 50px'}">
        <Breadcrumb :style="{margin: '20px 0'}">
          <BreadcrumbItem>Home</BreadcrumbItem>
          <BreadcrumbItem>Components</BreadcrumbItem>
          <BreadcrumbItem>Layout</BreadcrumbItem>
        </Breadcrumb>
        <Card>
          <div style="min-height: 300px;">
            Content
          </div>
        </Card>
      </Content>
      <Footer>
        &copy; 2018 DeepAQ.
        技术构架 <a href="https://github.com/DeepAQ/Autumn" target="_blank">Autumn</a>
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

    .layout-logo {
      color: whitesmoke;
      font-size: 24px;
      flex: 1;
    }
  }

  .ivu-layout-footer {
    text-align: center;
  }
}
</style>

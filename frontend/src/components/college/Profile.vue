<template>
  <div>
    <h2>机构信息</h2>
    <Row>
      <Col span="3">机构编号</Col>
      <Col>{{ collegeId }}</Col>
    </Row>
    <Row>
      <Col span="3">名称</Col>
      <Col v-if="edit" span="12"><Input v-model="edit_profile.name" size="large" type="text" /></Col>
      <Col v-else>{{ profile.current.name }}</Col>
    </Row>
    <Row>
      <Col span="3">地点</Col>
      <Col v-if="edit" span="12"><Input v-model="edit_profile.location" size="large" type="text" /></Col>
      <Col v-else>{{ profile.current.location }}</Col>
    </Row>
    <Row>
      <Col span="3">简介</Col>
      <Col v-if="edit" span="12"><Input v-model="edit_profile.description" size="large" type="textarea" /></Col>
      <Col v-else>{{ profile.current.description }}</Col>
    </Row>
    <div style="margin: 10px 0;">
      <Button type="primary" size="large" @click="saveClick" v-if="edit">保存</Button>
      <Button size="large" @click="edit = false" v-if="edit">取消</Button>
      <Button type="primary" size="large" @click="editClick" v-else>修改</Button>
    </div>
    <template v-if="profile.pending">
      <h2>待审批信息</h2>
      <Row>
        <Col span="3">名称</Col>
        <Col>{{ profile.pending.name }}</Col>
      </Row>
      <Row>
        <Col span="3">地点</Col>
        <Col>{{ profile.pending.location }}</Col>
      </Row>
      <Row>
        <Col span="3">简介</Col>
        <Col>{{ profile.pending.description }}</Col>
      </Row>
    </template>
  </div>
</template>

<script>
import api from '@/api'

export default {
  name: 'StudentProfile',
  data () {
    return {
      profile: {},
      edit_profile: {},
      edit: false
    }
  },
  computed: {
    collegeId () {
      let id = '000000' + this.profile.id
      return id.substr(id.length - 7)
    }
  },
  methods: {
    editClick () {
      this.edit_profile = Object.assign({}, this.profile.current)
      this.edit = true
    },
    saveClick () {
      api('college/profile/edit', this.edit_profile).then(result => {
        this.$Message.info('提交成功，请等待审核通过')
        this.profile.pending = this.edit_profile
        this.edit = false
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  },
  mounted () {
    api('college/profile', {}).then(result => {
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

.ivu-icon {
  width: 16px;
}
</style>

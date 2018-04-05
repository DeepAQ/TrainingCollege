<template>
  <div>
    <Modal v-model="show" width="400">
      <p slot="header">机构审批</p>
      <div>
        <template v-if="profile.current">
          <h2>原机构信息</h2>
          <Row>
            <Col span="3">名称</Col>
            <Col>{{ profile.current.name }}</Col>
          </Row>
          <Row>
            <Col span="3">地点</Col>
            <Col>{{ profile.current.location }}</Col>
          </Row>
          <Row>
            <Col span="3">简介</Col>
            <Col>{{ profile.current.description }}</Col>
          </Row>
        </template>
        <template v-if="profile.pending">
          <h2>新机构信息</h2>
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
      <div slot="footer" style="text-align: left;">
        <Button type="success" @click="permitPending(true)">审批通过</Button>
        <Button type="error" @click="permitPending(false)">审批不通过</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      profile: {},
      collegeId: 0,
      show: false
    }
  },
  watch: {
    show () {
      if (this.collegeId > 0) {
        this.loadData()
      }
    }
  },
  methods: {
    loadData () {
      api('college/profile/single', { collegeId: this.collegeId }).then(result => {
        if (result) {
          this.profile = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    permitPending (allow) {
      if (this.collegeId > 0) {
        api(`college/pendings/permit`, { collegeId: this.collegeId, allow: allow }).then(result => {
          this.$Message.info('审批成功')
          this.show = false
          this.$emit('update')
        }).catch(reason => {
          this.$Message.error(reason)
        })
      }
    }
  }
}
</script>

<style lang="less" scoped>
h2 {
  margin: 10px 0;
}
</style>

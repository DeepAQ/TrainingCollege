<template>
  <div>
    <Modal v-model="show" width="600">
      <p slot="header">班级管理</p>
      <div>
        <Table stripe :columns="cols" :data="classes"></Table>
      </div>
      <div slot="footer" style="text-align: left;">
        <div style="margin-top: 10px;">
          <Input v-model="newClass.teacher" size="large" placeholder="教师名称" style="width: 200px;"/>
          班级价格
          <InputNumber :min="100" v-model="newClass.price"
            :formatter="value => `${value / 100}`"
            :parser="value => Math.round(value * 100)"/>
          班级人数
          <InputNumber :min="1" v-model="newClass.limit"/>
          <Button type="primary" @click="addClass">添加班级</Button>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      show: false,
      course: 0,
      cols: [
        { title: '教师', key: 'teacher' },
        { title: '人数', key: 'limit' },
        {
          title: '价格',
          render: (h, params) => {
            return h('div', `${params.row.price / 100}`)
          }
        },
      ],
      classes: [],
      newClass: {
        teacher: '',
        price: 100,
        limit: 10
      }
    }
  },
  watch: {
    show () {
      if (this.show && this.course > 0) {
        this.loadData()
      }
    }
  },
  methods: {
    loadData () {
      api('course/classes', { courseId: this.course }).then(result => {
        if (result) {
          this.classes = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    addClass () {
      if (this.course > 0) {
        api(`course/addclass?courseId=${this.course}`, this.newClass).then(result => {
          this.newClass = {
            teacher: '',
            price: 100,
            limit: 10
          }
          this.$Message.info('添加成功')
          this.loadData()
        }).catch(reason => {
          this.$Message.error(reason)
        })
      }
    }
  }
}
</script>

<style lang="less" scoped>

</style>

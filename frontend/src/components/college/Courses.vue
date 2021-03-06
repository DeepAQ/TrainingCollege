<template>
  <div>
    <div style="display: flex; align-items: center;">
      <div style="flex: 1;">
        <h2>课程计划</h2>
      </div>
      <div>
        <Button size="large" type="success" @click="addClick">添加课程计划</Button>
      </div>
    </div>
    <Table stripe :columns="cols" :data="courses"></Table>
    <Modal v-model="addNew" width="400">
      <p slot="header">添加课程计划</p>
      <div>
        <Input v-model="newCourse.title" size="large" placeholder="课程名称" />
        <Input v-model="newCourse.tags" size="large" placeholder="课程类别" />
        <Input v-model="newCourse.description" size="large" autosize type="textarea" placeholder="课程简介" />
        <DatePicker v-model="startDate" type="date" size="large" placeholder="开始时间" @on-change="dateChange"/>
        <div>
          <InputNumber :max="10" :min="1" v-model="newCourse.period"></InputNumber> 课时 / 周 *
          <InputNumber :max="50" :min="1" v-model="newCourse.weeks"></InputNumber> 周
        </div>
      </div>
      <div slot="footer">
        <Button type="primary" size="large" @click="saveClick">
          保存
        </Button>
      </div>
    </Modal>
    <ClassManage ref="cm"/>
  </div>
</template>

<script>
import api from '@/api'
import ClassManage from './ClassManage'

export default {
  components: { ClassManage },
  data () {
    return {
      addNew: false,
      newCourse: {},
      startDate: null,
      cols: [
        { title: '课程名称', key: 'title' },
        { title: '课程类别', key: 'tags', width: 120 },
        {
          title: '开课时间', width: 100,
          render: (h, params) => {
            return h('div', this.formatDate(params.row.startTime))
          }
        },
        {
          title: '课时', width: 150,
          render: (h, params) => {
            return h('div', `${params.row.weeks} 周，每周 ${params.row.period} 课时`)
          }
        },
        {
          title: '操作',
          width: 120,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.$refs.cm.course = params.row.id
                    this.$refs.cm.show = true
                  }
                }
              }, '班级管理')
            ])
          }
        },
      ],
      courses: []
    }
  },
  methods: {
    loadList () {
      api('course/my', {}).then(result => {
        if (result) {
          this.courses = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    dateChange () {
      this.newCourse.startTime = this.startDate.getTime() / 1000
    },
    formatDate (time) {
      let date = new Date(time * 1000)
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
    },
    addClick () {
      this.newCourse = {
        period: 1,
        weeks: 1
      }
      this.startDate = null
      this.addNew = true
    },
    saveClick () {
      api('course/add', this.newCourse).then(result => {
        this.$Message.info('添加成功')
        this.addNew = false
        this.loadList()
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  },
  mounted () {
    this.loadList()
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

.ivu-modal {
  .ivu-input-wrapper, .ivu-date-picker {
    margin-bottom: 10px;
  }

  .ivu-btn, .ivu-date-picker {
    width: 100%;
  }
}
</style>

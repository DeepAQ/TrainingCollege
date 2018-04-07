<template>
  <div>
    <h2>成绩与记录</h2>
    <div style="margin-top: 10px;">
      <Select v-model="chosenCourse" placeholder="请选择课程" style="width: 300px;">
        <Option v-for="c in courses" :value="c.id">{{ c.title }}</Option>
      </Select>
      <Select v-model="chosenClass" placeholder="请选择教师" style="width: 300px;">
        <Option v-for="c in classes" :value="c.id">{{ c.teacher }}</Option>
      </Select>
    </div>
    <Table stripe :columns="cols" :data="participants" style="margin-top: 10px;"></Table>
    <RecordDetail ref="rd"/>
  </div>
</template>

<script>
import api from '@/api'
import RecordDetail from '../order/RecordDetail'

export default {
  components: { RecordDetail },
  data () {
    return {
      courses: [],
      chosenCourse: 0,
      classes: [],
      chosenClass: 0,
      participants: [],
      cols: [
        { title: '序号', key: 'id', width: 100 },
        { title: '学员姓名', key: 'name' },
        {
          title: '操作', width: 140,
          render: (h, p) => {
            return h('div', [
              h('Button', {
                props: { type: 'primary', size: 'small' },
                on: {
                  click: () => {
                    this.$refs.rd.participant = p.row.id
                    this.$refs.rd.show = true
                  }
                }
              }, '成绩与记录')
            ])
          }
        },
      ]
    }
  },
  watch: {
    chosenCourse () {
      if (this.chosenCourse > 0) {
        this.chosenClass = 0
        this.loadClasses(this.chosenCourse)
      }
    },
    chosenClass () {
      if (this.chosenClass > 0) {
        this.loadParticipants(this.chosenClass)
      }
    }
  },
  mounted () {
    this.loadCourses()
  },
  methods: {
    loadCourses () {
      api('course/my', {}).then(result => {
        if (result) {
          this.courses = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    loadClasses (courseId) {
      api('course/classes', { courseId: courseId }).then(result => {
        if (result) {
          this.classes = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    loadParticipants (classId) {
      api('course/participants', { classId: classId }).then(result => {
        if (result) {
          this.participants = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>

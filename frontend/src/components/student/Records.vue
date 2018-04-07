<template>
  <div>
    <h2>我参加的</h2>
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
      participants: [],
      cols: [
        {
          title: '课程',
          render: (h, p) => {
            return h('router-link', {
              props: { to: `/detail/${p.row.courseId}` }
            }, p.row.courseName)
          }
        },
        { title: '教师', key: 'teacher', width: 140, },
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
  mounted () {
    this.loadData()
  },
  methods: {
    loadData () {
      api('course/participated', {}).then(result => {
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

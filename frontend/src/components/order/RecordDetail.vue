<template>
  <div>
    <Modal v-model="show" width="800">
      <p slot="header">成绩与记录</p>
      <div style="display: flex;">
        <div style="flex: 1;">
          <h3><Icon type="android-checkbox-outline"></Icon> 听课记录</h3>
          <Table stripe :columns="record_cols" :data="records"></Table>
        </div>
        <div style="width: 10px;"></div>
        <div style="flex: 1;">
          <h3><Icon type="arrow-graph-up-right"></Icon> 成绩记录</h3>
          <Table stripe :columns="grade_cols" :data="grades"></Table>
        </div>
      </div>
      <div slot="footer" style="text-align: left;" v-if="loginType == 'College'">
        <div style="display: flex;">
          <div style="flex: 1;">
            <DatePicker v-model="rDate" type="date" placeholder="日期" style="width: 110px;" @on-change="rDateChange"/>
            <Input v-model="newRecord.note" placeholder="备注" style="width: 200px;"/>
            <Button type="primary" @click="addRecord">添加</Button>
          </div>
          <div style="width: 10px;"></div>
          <div style="flex: 1;">
            <DatePicker v-model="gDate" type="date" placeholder="日期" style="width: 110px;" @on-change="gDateChange"/>
            <Input v-model="newGrade.grade" placeholder="成绩" style="width: 100px;"/>
            <Input v-model="newGrade.note" placeholder="备注" style="width: 100px;"/>
            <Button type="primary" @click="addGrade">添加</Button>
          </div>
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
      participant: 0,
      records: [],
      grades: [],
      rDate: '',
      gDate: '',
      newRecord: { date: 0, note: '' },
      newGrade: { date: 0, grade: '', note: '' },
      record_cols: [
        { title: '日期', render: (h, p) => h('div', this.formatDate(p.row.date)) },
        { title: '备注', key: 'note' }
      ],
      grade_cols: [
        { title: '日期', render: (h, p) => h('div', this.formatDate(p.row.date)) },
        { title: '成绩', key: 'grade' },
        { title: '备注', key: 'note' }
      ]
    }
  },
  computed: {
    loginType () {
      return window.$state.loginType
    }
  },
  watch: {
    show () {
      if (this.show && this.participant > 0) {
        this.loadRecords()
        this.loadGrades()
      }
    }
  },
  methods: {
    rDateChange () {
      this.newRecord.date = this.rDate.getTime() / 1000
    },
    gDateChange () {
      this.newGrade.date = this.gDate.getTime() / 1000
    },
    formatDate (time) {
      let date = new Date(time * 1000)
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
    },
    loadRecords () {
      api('record/records', { participantId: this.participant }).then(result => {
        if (result) {
          this.records = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    loadGrades () {
      api('record/grades', { participantId: this.participant }).then(result => {
        if (result) {
          this.grades = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    addRecord () {
      if (this.newRecord.date > 0) {
        api('record/addrecord', {
          participantId: this.participant,
          date: this.newRecord.date,
          note: this.newRecord.note
        }).then(result => {
          this.$Message.info('添加成功')
          this.loadRecords()
        }).catch(reason => {
          this.$Message.error(reason)
        })
      }
    },
    addGrade () {
      if (this.newGrade.date > 0) {
        api('record/addgrade', {
          participantId: this.participant,
          date: this.newGrade.date,
          grade: this.newGrade.grade,
          note: this.newGrade.note
        }).then(result => {
          this.$Message.info('添加成功')
          this.loadGrades()
        }).catch(reason => {
          this.$Message.error(reason)
        })
      }
    }
  }
}
</script>

<style lang="less" scoped>
h3 {
  padding-bottom: 10px;
}

.ivu-icon {
  width: 20px;
  text-align: center;
}
</style>

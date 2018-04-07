<template>
  <div style="margin: 10px 40px;">
    <h1>{{detail.title}}</h1>
    <h3>基本信息</h3>
    <Icon type="university"></Icon> {{detail.college.name}}
    <Icon type="android-calendar" style="margin-left: 20px;"></Icon> {{formatDate(detail.startTime)}} 开课
    <Icon type="android-time" style="margin-left: 20px;"></Icon>
    {{detail.weeks}} 周，每周 {{detail.period}} 课时
    <h3>课程介绍</h3>
    <div>
      {{detail.description}}
    </div>
    <h3>机构介绍</h3>
    <div>
      <div><Icon type="ios-location"></Icon> {{detail.college.location}}</div>
      <div><Icon type="document-text"></Icon> {{detail.college.description}}</div>
    </div>
    <template v-if="loginType == 'Student'">
      <h3>报名课程</h3>
      <div>
        <RadioGroup v-model="chosenClass" vertical>
          <Radio size="large" :label="0" v-if="detail.avgPrice > 0">
            ¥ {{detail.avgPrice / 100}}（不选班级，开课前两周内配班）
          </Radio>
          <Radio size="large" :label="c.id" v-for="c in detail.classes">
            ¥ {{c.price / 100}}（教师：{{c.teacher}}，班级人数：{{c.limit}}）
          </Radio>
        </RadioGroup>
      </div>
      <Button size="large" type="primary" style="margin-top: 10px;" @click="toOrder">报名</Button>
      <OrderDetail ref="od"/>
      <OrderDetailNoClass ref="odnc"/>
    </template>
  </div>
</template>

<script>
import api from '@/api'
import OrderDetail from './order/OrderDetail'
import OrderDetailNoClass from './order/OrderDetailNoClass'

export default {
  components: { OrderDetail, OrderDetailNoClass },
  data () {
    return {
      detail: {},
      chosenClass: 0
    }
  },
  computed: {
    courseId () {
      return this.$route.params.id
    },
    loginType () {
      return window.$state.loginType
    }
  },
  watch: {
    courseId () {
      this.loadData()
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    formatDate (time) {
      let date = new Date(time * 1000)
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`
    },
    loadData () {
      api('course/detail', { courseId: this.courseId }).then(result => {
        if (result) {
          this.detail = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    toOrder () {
      if (this.chosenClass > 0) {
        this.$refs.od.classId = this.chosenClass
        this.$refs.od.show = true
      } else {
        this.$refs.odnc.courseId = this.detail.id
        this.$refs.odnc.avgPrice = this.detail.avgPrice
        this.$refs.odnc.show = true
      }
    }
  }
}
</script>

<style lang="less" scoped>
h3 {
  margin: 15px 0;
  border-bottom: #ccc solid 1px;
}

.ivu-icon {
  width: 16px;
}
</style>

<template>
  <div>
    <h2>现场缴费</h2>
    <h3>选择课程</h3>
    <RadioGroup v-model="chosenCourse" vertical>
      <Radio size="large" :label="c.id" v-for="c in courses">
        {{c.title}}
      </Radio>
    </RadioGroup>
    <template v-if="chosenCourse > 0">
      <h3>选择班级</h3>
      <RadioGroup v-model="chosenClass" vertical>
        <Radio size="large" :label="c.id" v-for="c in classes">
          ¥ {{c.price / 100}}（教师：{{c.teacher}}，班级人数：{{c.limit}}）
        </Radio>
      </RadioGroup>
    </template>
    <template v-if="chosenClass > 0">
      <h3>会员信息</h3>
      <div style="margin-top: 5px;">
        <Input v-model="email" placeholder="会员请填写邮箱，非会员请留空" style="width: 300px;" />
      </div>
      <h3>填写报名信息</h3>
      <div style="margin-top: 5px;">
        实时剩余名额：{{classInfo.available}}
      </div>
      <div style="margin-top: 5px;">
        报名人数：
        <InputNumber :max="classInfo.available" :min="1" v-model="count"></InputNumber>
        总价：¥ {{classInfo.courseClass.price * count / 100}}
      </div>
      <div>
        <Input v-model="names[idx - 1]" style="width: 100px; margin: 5px 10px 0 0;" :placeholder="`学员姓名 (${idx})`" v-for="idx in count"/>
      </div>
      <Button type="primary" size="large" style="margin-top: 20px;" @click="submit">确认现场缴费</Button>
    </template>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data () {
    return {
      courses: [],
      chosenCourse: 0,
      classes: [],
      chosenClass: 0,
      classInfo: {},
      count: 1,
      email: '',
      names: []
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
        this.loadClassInfo(this.chosenClass)
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
    loadClassInfo (classId) {
      api('order/classinfo', { classId: classId }).then(result => {
        if (result) {
          this.classInfo = result
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    submit () {
      let finalNames = []
      for (let i = 0; i < this.count; i++) {
        finalNames[i] = this.names[i]
      }
      api('order/newsale', {
        classId: this.chosenClass,
        email: this.email,
        names: finalNames
      }).then(result => {
        this.$Message.info('缴费成功')
        this.chosenCourse = 0
        this.chosenClass = 0
      }).catch(reason => {
        this.$Message.error(reason)
      })
    }
  }
}
</script>

<style lang="less" scoped>
h2, h3 {
  margin-top: 10px;
}
</style>

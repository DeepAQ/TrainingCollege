<template>
  <div>
    <Modal v-model="show" width="400">
      <p slot="header">课程报名</p>
      <div>
        <div style="margin-top: 5px;">
          报名人数：
          <InputNumber :max="9" :min="1" v-model="count"></InputNumber>
          本单限 9 人
        </div>
        <div style="margin-top: 5px;">
          总价：¥ {{avgPrice * count / 100}}（优惠可在支付时使用）
        </div>
        <div style="margin-top: 5px;">
          本订单将在开课前两周内配班
        </div>
        <Input v-model="names[idx - 1]" style="width: 100px; margin: 5px 10px 0 0;" :placeholder="`学员姓名 (${idx})`" v-for="idx in count"/>
      </div>
      <div slot="footer" style="text-align: left;">
        <Button type="primary" @click="submit">确认报名</Button>
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
      courseId: 0,
      avgPrice: 0,
      count: 1,
      names: []
    }
  },
  watch: {
    show () {
      if (this.show) {
        this.loadData()
      }
    }
  },
  methods: {
    loadData() {
      if (this.classId > 0) {
        api('order/classinfo', { classId: this.classId }).then(result => {
          if (result) {
            this.classInfo = result
          }
        }).catch(reason => {
          this.$Message.error(reason)
        })
      }
    },
    submit () {
      let finalNames = []
      for (let i = 0; i < this.count; i++) {
        finalNames[i] = this.names[i]
      }
      api('order/newnc', { courseId: this.courseId, names: finalNames }).then(result => {
        this.$Message.info('报名成功')
        this.show = false
      }).catch(reason => {
        this.$Message.error(reason)
        this.loadData()
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>

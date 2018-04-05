<template>
  <div>
    <h2>新注册机构</h2>
    <Table stripe :columns="cols" :data="newProfiles"></Table>
    <h2>机构信息修改</h2>
    <Table stripe :columns="cols" :data="editProfiles"></Table>
    <PermitCheck ref="ck" @update="loadData"/>
  </div>
</template>

<script>
import api from '@/api'
import PermitCheck from './PermitCheck'

export default {
  components: { PermitCheck },
  data () {
    return {
      cols: [
        {
          title: '机构编号',
          width: 100,
          render: (h, params) => {
            return h('div', this.formatId(params.row.id))
          }
        },
        { title: '机构名称', key: 'name' },
        {
          title: '操作',
          width: 100,
          render: (h, params) => {
            return h('Button', {
              props: {
                type: 'primary',
                size: 'small'
              },
              on: {
                click: () => {
                  this.$refs.ck.collegeId = params.row.id
                  this.$refs.ck.show = true
                }
              }
            }, '审批')
          }
        },
      ],
      newProfiles: [],
      editProfiles: []
    }
  },
  methods: {
    loadData () {
      api('college/pendings', {}).then(result => {
        if (result) {
          this.newProfiles = result.newProfiles
          this.editProfiles = result.editProfiles
        }
      }).catch(reason => {
        this.$Message.error(reason)
      })
    },
    formatId (result) {
      let id = '000000' + result;
      return id.substr(id.length - 7)
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>

<style lang="less" scoped>
h2 {
  padding: 10px 0;
}
</style>

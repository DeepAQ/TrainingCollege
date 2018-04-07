<template>
  <div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  mounted () {
    if (this.$route.params.token != '') {
      api(`auth/student/activate?token=${this.$route.params.token}`, {}).then(result => {
        this.$Message.info('激活成功，即将跳转至首页')
      }).catch(reason => {
        this.$Message.error(reason)
      }).then(() => setTimeout(() => window.location = '/', 3000))
    } else {
      window.location = '/'
    }
  }
}
</script>

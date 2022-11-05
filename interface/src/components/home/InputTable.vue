<template>
  <el-form>
    <el-row>
      <el-form-item v-for="domain in domains" :key="domain.key">
        <el-row>
          关系
          <el-input v-model="domain.name" style="width: 20%" placeholder="关系名"/>
          <el-input v-model="domain.col" style="width: 22%" placeholder="列数/属性数"/>
          <el-input v-model="domain.row" style="width: 22%" placeholder="行数/元组数"/>
          <el-button>创建表</el-button>
          <el-button @click.prevent="removeDomain(domain)">删除</el-button>
        </el-row>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item>
        <el-button @click="addDomain">新增关系</el-button>
      </el-form-item>
    </el-row>
  </el-form>
</template>

<script>
export default {
  name: 'InputTable',
  data () {
    return {
      domains: [{
        name: '',
        row: '',
        col: ''
      }]
    }
  },
  methods: {
    // 删除表单项
    removeDomain (item) {
      var index = this.domains.indexOf(item)
      if (index !== -1) {
        this.domains.splice(index, 1)
      }
    },
    // 新增表单项
    addDomain () {
      this.domains.push({
        name: '',
        row: '',
        col: '',
        key: Date.now()// 获取当前时间作为key，以确保表单的唯一key值
      })
    }
  }
}
</script>

<style scoped>

</style>

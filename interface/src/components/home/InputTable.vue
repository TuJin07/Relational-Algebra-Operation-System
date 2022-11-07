<template>
  <el-form>
    <el-row>
      <el-form-item v-for="domain in domains" :key="domain.key">
        <el-row>
          关系
          <el-input v-model="domain.name" style="width: 20%" placeholder="关系名"/>
          <el-input v-model="domain.row" style="width: 26%" placeholder="行数/元组数"/>
          <el-input v-model="domain.col" style="width: 26%" placeholder="列数/属性数"/>
          <el-button @click.prevent="removeDomain(domain)">删除</el-button>
        </el-row>
        <el-row>
          <el-input v-model="domain.col_name" placeholder="列名（用英文逗号分格）"/>
        </el-row>
        <el-row>
          <el-input v-model="domain.text"
                    type="textarea" autosize placeholder="输入关系表，属性间以英文逗号分隔，行间以回车分隔"/>
        </el-row>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item>
        <el-button @click="addDomain">新增关系</el-button>
        <el-button @click="submit">确认关系</el-button>
        <el-button @click="reset">重置</el-button>
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
        col: '',
        col_name: '',
        text: ''
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
      this.$axios
        .post('/delete/', {
          'name': item.name
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('删除成功！', '删除关系', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `action: ${action}`
                })
              }
            })
          }
        })
        .catch(failResponse => {
          this.$alert('删除失败！', '删除关系', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              })
            }
          })
        })
    },
    // 新增表单项
    addDomain () {
      this.domains.push({
        name: '',
        row: '',
        col: '',
        col_name: '',
        text: '',
        key: Date.now()// 获取当前时间作为key，以确保表单的唯一key值
      })
    },
    // 向后端传表单
    submit () {
      // todo 1. 取不到值 @serum  2. 回车的处理先删除了，调试没问题后再加上
      this.$axios
        .post('/insert/', {
          'relation_name': this.domains.name,
          'row_len': this.domains.row,
          'col_len': this.domains.col,
          'col_name': this.domains.col_name,
          'content': this.domains.text
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('输入关系表成功！', '确认关系', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `action: ${action}`
                })
              }
            })
          }
        })
        .catch(failResponse => {
          this.$alert('输入关系表失败！', '确认关系', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              })
            }
          })
        })
    },
    // 重置
    reset () {
      // .post()
    }
  }
}
</script>

<style scoped>

</style>

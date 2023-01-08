<template>
  <el-form>
    <el-row>
      <h3 style="float: left">添加自定义表</h3>
      <br><br><br><br>
      <el-form-item v-for="domain in domains" :key="domain.key">
        <el-row>
          关系
          <el-input v-model="domain.name" style="width: 18%" placeholder="关系名" :disabled="domain.dis"/>
          <el-input v-model="domain.row" style="width: 18%" placeholder="行数/元组数" :disabled="domain.dis"/>
          <el-input v-model="domain.col" style="width: 18%" placeholder="列数/属性数" :disabled="domain.dis"/>
          <el-button @click="submit(domain)" :disabled="domain.dis">确认关系</el-button>
          <el-button @click.prevent="removeDomain(domain)">删除</el-button>
        </el-row>
        <el-row>
          <el-input v-model="domain.col_name" style="width: 90%" placeholder="列名（用英文逗号分格）" :disabled="domain.dis"/>
        </el-row>
        <el-row>
          <el-input v-model="domain.text" style="width: 90%"
                    type="textarea" autosize placeholder="输入关系表，属性间以英文逗号分隔，行间以回车分隔" :disabled="domain.dis"/>
        </el-row>
      </el-form-item>
    </el-row>
    <el-row>
      <el-form-item>
        <el-button @click="addDomain">新增关系</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-row>
  </el-form>
</template>

<script>
export default {
  name: 'InputTable',
  inject: ['reload'],
  data () {
    return {
      domains: [{
        name: '',
        row: '',
        col: '',
        col_name: '',
        text: '',
        dis: false
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
        dis: false,
        key: Date.now()// 获取当前时间作为key，以确保表单的唯一key值
      })
    },
    // 向后端传表单
    submit (item) {
      this.$axios
        .post('/insert/', {
          'relation_name': item.name,
          'row_len': item.row,
          'col_len': item.col,
          'col_name': item.col_name,
          'content': item.text.replace(/\n|\r\n/g, ',') // 用逗号代替换行
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
            item.dis = true // 不可二次提交
          } else if (successResponse.data.code === 400) {
            this.$alert(successResponse.data.msg, '确认关系', {
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
      this.$axios
        .get('/delete_all/', {
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('重置成功！', '重置关系', {
              confirmButtonText: '确定',
              callback: action => {
                this.$message({
                  type: 'info',
                  message: `action: ${action}`
                })
              }
            })
          }
          this.reload() // 调用刷新
        })
        .catch(failResponse => {
          this.$alert('重置失败！', '重置关系', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              })
            }
          })
        })
    }
  }
}
</script>

<style scoped>

</style>

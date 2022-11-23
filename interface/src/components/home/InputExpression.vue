<template>
  <el-form>
    <el-form-item>
      <el-input v-model="expression"
                @blur="handleInputBlur"
                ref="inputArea"
                style="width: 80%"
                placeholder="请输入表达式"
      ></el-input>
    </el-form-item>
    <el-form-item>
      <el-button v-for="button in buttons" :key="button.key"
                 @click="labClick(button.name)"
      >{{button.name}}</el-button>
    </el-form-item>
    <el-form-item>
      <el-button @click="submit">提交</el-button>
    </el-form-item>

<!--    <el-dialog :visible.sync="dialogTableVisible">-->
<!--      <el-table>-->
<!--      </el-table>-->
<!--    </el-dialog>-->
    <el-table :data="list">
      <el-table-column label="id" prop="id"></el-table-column>
      <!-- slot="header"是为了插入表头的，这里遍历list[0],是因为表头都一样，所以取第一行数据的字段做为表头即可 -->
      <el-table-column v-for="(item, index) in list[0].dataList" :key="index">
        <template slot="header">
          {{item.num}}
        </template>
        <!-- index对应下面动态列（dataList）的索引，取出值渲染 -->
        <template slot-scope="scope">{{scope.row.dataList[index].data}}</template>
      </el-table-column>
    </el-table>
  </el-form>
</template>

<script>
export default {
  name: 'InputExpression',
  data () {
    return {
      cursorIndex: '',
      expression: '',
      buttons: [{
        name: '∪'
      }, {
        name: '∩'
      }, {
        name: '−'
      }, {
        name: '×'
      // }, {
      //   name: 'σ'
      // }, {
      //   name: 'π'
      // }, {
      //   name: '⋈'
      // }, {
      //   name: '÷'
      }],
      list: [{
        id: '',
        dataList: [{
          num: '',
          data: ''
        }]
      }]
    }
  },

  methods: {
    //  获取光标位置
    handleInputBlur (e) {
      this.cursorIndex = e.srcElement.selectionStart
    },
    // 点击标签按钮，处理文本框文本内容
    labClick (lab) {
      let s1 = ''
      let s2 = ''
      if (this.expression.length < this.cursorIndex) {
        this.expression = this.expression + lab
      } else {
        s1 = this.expression.toString()
        s2 = this.expression.toString()
        this.expression = s1.substring(0, this.cursorIndex) +
          lab +
          s2.substring(this.cursorIndex, this.expression.length)
      }
      this.$refs.inputArea.focus()
    },
    // 提交按钮
    submit () {
      let ans = this.expression
      for (let i = 0; i < ans.length; i++) {
        let n = ans[i]
        let s = ans
        if (n === '(') ans = s.substring(0, i + 1) + ' ' + s.substring(i + 1, s.length)
        else if (n === ')') ans = s.substring(0, i) + ' ' + s.substring(i, s.length)
        else if (n === '∪') ans = s.substring(0, i) + ' #or ' + s.substring(i + 1, s.length)
        else if (n === '∩') ans = s.substring(0, i) + ' #and ' + s.substring(i + 1, s.length)
        else if (n === '−') ans = s.substring(0, i) + ' #diff ' + s.substring(i + 1, s.length)
        else if (n === '×') ans = s.substring(0, i) + ' #prod ' + s.substring(i + 1, s.length)
      }
      this.$axios
        .post('/compute/', {
          'expression': ans
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            // 返回表格
            let row = successResponse.data.data.row_len
            let col = successResponse.data.data.col_len
            let name = successResponse.data.data.col_name.split(',')
            let content = successResponse.data.data.content.split(',')
            for (let i = 1; i <= row; i++) {
              let l = []
              for (let j = 0; j < col; j++) {
                l.push({
                  num: name[j],
                  data: content[j]
                })
              }
              content.splice(0, col)
              this.list.push({
                id: i,
                dataList: l
              })
            }
            this.list.splice(0, 1)
          } else if (successResponse.data.code === 400) {
            this.$alert('提交错误！', '计算表达式', {
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
    }
  }
}
</script>

<style scoped>

</style>

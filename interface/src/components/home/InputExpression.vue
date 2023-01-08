<template>
  <el-form>
    <h3 style="float: left">输入表达式</h3>
    <br><br><br><br>
    <el-form-item>
      <el-input v-model="expression"
                id="expression"
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
    <h3 style="float: left">结果表</h3>
    <br><br><br><br>
    <!-- 解决表头不刷新问题 -->
    <el-table :data="list" :default-sort="sortObj" @sort-change="handleChange" :key="mykey">
      <el-table-column label="id" prop="id"></el-table-column>
      <!-- slot="header"是为了插入表头的，这里遍历list[0],是因为表头都一样，所以取第一行数据的字段做为表头即可 -->
      <el-table-column v-for="(item, index) in list[0].dataList" :key="index">
        <template #header>
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
      }, {
        name: 'σ'
      }, {
        name: 'π'
      }, {
        name: '⋈'
      }, {
        name: '÷'
      }, {
        name: '∧'
      }, {
        name: '∨'
      }],
      list: [{
        id: '',
        dataList: [{
          num: '',
          data: ''
        }]
      }],
      sortObj: {},
      mykey: ''
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
        if (lab === 'σ' || lab === 'π') {
          this.expression = this.expression + lab + '[][]'
        } else {
          this.expression = this.expression + lab
        }
      } else {
        s1 = this.expression.toString()
        s2 = this.expression.toString()
        if (lab === 'σ' || lab === 'π') {
          this.expression = s1.substring(0, this.cursorIndex) +
            lab + '[][]' +
            s2.substring(this.cursorIndex, this.expression.length)
        } else {
          this.expression = s1.substring(0, this.cursorIndex) +
            lab +
            s2.substring(this.cursorIndex, this.expression.length)
        }
      }
      // 改变光标位置在第一个括号后 不能用ref
      if (lab === 'σ' || lab === 'π') {
        this.$nextTick(() => {
          const txt = document.getElementById('expression')
          txt.focus()
          txt.selectionStart = this.cursorIndex + 2
          txt.selectionEnd = this.cursorIndex + 2
        })
      } else {
        this.$nextTick(() => {
          const txt = document.getElementById('expression')
          txt.focus()
          txt.selectionStart = this.cursorIndex + 1
          txt.selectionEnd = this.cursorIndex + 1
        })
      }
    },
    // 提交中的嵌套函数
    nest (ans) {
      for (let i = 0; i < ans.length; i++) {
        let n = ans[i]
        let s = ans
        if (n === '(') ans = s.substring(0, i + 1) + ' ' + s.substring(i + 1, s.length)
        else if (n === ')') {
          ans = s.substring(0, i) + ' ' + s.substring(i, s.length)
          i++
        } else if (n === '∪') ans = s.substring(0, i) + ' #or ' + s.substring(i + 1, s.length)
        else if (n === '∩') ans = s.substring(0, i) + ' #and ' + s.substring(i + 1, s.length)
        else if (n === '−') ans = s.substring(0, i) + ' #diff ' + s.substring(i + 1, s.length)
        else if (n === '×') ans = s.substring(0, i) + ' #prod ' + s.substring(i + 1, s.length)
        else if (n === '⋈') ans = s.substring(0, i) + ' #join ' + s.substring(i + 1, s.length)
        else if (n === '÷') ans = s.substring(0, i) + ' #div ' + s.substring(i + 1, s.length)
        else if (n === 'σ' || n === 'π') {
          let x = n
          while (n !== '[') {
            i++
            n = ans[i]
          }
          let x1 = i + 1
          while (n !== ']') {
            i++
            n = ans[i]
          }
          let x2 = i - 1
          while (n !== '[') {
            i++
            n = ans[i]
          }
          let x3 = i + 1
          while (n !== ']') {
            i++
            n = ans[i]
          }
          let x4 = i - 1
          let exp = s.substring(x1, x2 + 1)
          let list = s.substring(x3, x4 + 1)
          // 处理条件式
          for (let k = 0; k < exp.length; k++) {
            let m = exp[k]
            let str = exp
            if (m === '(') exp = str.substring(0, k + 1) + '|' + str.substring(k + 1, str.length)
            else if (m === ')') exp = str.substring(0, k) + '|' + str.substring(k, str.length)
            else if (m === '∧') exp = str.substring(0, k) + '|$and|' + str.substring(k + 1, str.length)
            else if (m === '∨') exp = str.substring(0, k) + '|$or|' + str.substring(k + 1, str.length)
          }
          // 嵌套处理表
          list = this.nest(list)
          if (x === 'σ') {
            ans = s.substring(0, x1 - 2) + '#select[' + list + ',' + exp + ',1]'
            let len = ans.length
            ans += s.substring(i + 1, s.length)
            i = len - 1
          } else {
            let name = exp.split(',')
            ans = s.substring(0, x1 - 2) + '#project[' + list + ','
            for (let n = 0; n < name.length; n++) {
              ans += name[n] + ','
            }
            ans += name.length + ']'
            let len = ans.length
            ans += s.substring(i + 1, s.length)
            i = len - 1
          }
        }
      }
      return ans
    },
    // 提交按钮
    submit () {
      let ans = this.expression
      console.log(ans)
      ans = this.nest(ans)
      console.log(ans)
      this.$axios
        .post('/compute/', {
          'expression': ans
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            // 返回表格
            let row = successResponse.data.data.row_len
            let col = successResponse.data.data.col_len
            let name
            let content
            this.list = [{
              id: '',
              dataList: [{
                num: '',
                data: ''
              }]
            }]
            // 返回空表
            if (row === 0 && col === 0) {
              this.$alert('返回空表！', '计算表达式', {
                confirmButtonText: '确定',
                callback: action => {
                  this.$message({
                    type: 'info',
                    message: `action: ${action}`
                  })
                }
              })
            } else if (row === 0) {
              name = successResponse.data.data.col_name.split(',')
              let l = []
              for (let j = 0; j < col; j++) {
                l.push({
                  num: name[j],
                  data: ''
                })
              }
              this.list.push({
                id: '',
                dataList: l
              })
              this.list.splice(0, 1)
              this.reDrawTable()
              console.log(this.list)
              this.$alert('返回空表！', '计算表达式', {
                confirmButtonText: '确定',
                callback: action => {
                  this.$message({
                    type: 'info',
                    message: `action: ${action}`
                  })
                }
              })
            } else {
              name = successResponse.data.data.col_name.split(',')
              content = successResponse.data.data.content.split(',')
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
              this.reDrawTable()
            }
          } else if (successResponse.data.code === 400) {
            this.$alert(successResponse.data.msg, '计算表达式', {
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
    },
    handleChange ({ column, prop, order }) {
      this.sortObj = {
        prop,
        order
      }
    },
    reDrawTable () {
      this.mykey = Math.random()
      // 重新挂载拖拽监听
      this.$nextTick(() => {
        this.columnDrop()
        this.rowDrop()
      })
    }
  }
}
</script>

<style scoped>

</style>

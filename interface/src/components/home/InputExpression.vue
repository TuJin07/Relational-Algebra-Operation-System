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
    }
  }
}
</script>

<style scoped>

</style>

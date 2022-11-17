<template>
  <el-form>
    <el-row>
      <el-input v-model="expression" style="width: 80%" placeholder="请输入表达式" />
    </el-row>

    <el-row>
      <el-button>∪</el-button>
      <el-button>∩</el-button>
      <el-button>−</el-button>
      <el-button>×</el-button>
      <el-button>σ</el-button>
      <el-button>π</el-button>
      <el-button>⋈</el-button>
      <el-button>÷</el-button>
    </el-row>

  </el-form>
</template>

<script>
export default {
  name: 'InputExpression',
  data () {
    return {
      expression: ''
    }
  }
}
</script>

<style scoped>

</style>

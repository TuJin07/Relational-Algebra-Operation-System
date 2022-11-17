<template>
  <el-container>
    <el-header>
      <h1 align="center">关系代数运算系统</h1>
    </el-header>
    <el-container direction="horizontal">
      <el-aside style="width: 50%;margin-top: 20px">
        <InputTable></InputTable>
      </el-aside>

      <el-main style="width: 50%">
        <InputExpression></InputExpression>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
import InputExpression from './home/InputExpression'
import InputTable from './home/InputTable'
export default {
  name: 'Home',
  components: {InputTable, InputExpression}
}
</script>

<style scoped>

</style>

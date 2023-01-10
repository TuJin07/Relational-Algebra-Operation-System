<template>
  <el-container direction="vertical">
    <el-main>
      <h1 align="center">关系代数运算系统</h1>
      <Tips></Tips>
    </el-main>
    <el-container direction="horizontal">
      <el-aside style="width: 50%">
        <InputTable></InputTable>
        <TableData></TableData>
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
import TableData from './home/TableData'
import Tips from './home/Tips'
export default {
  name: 'Home',
  components: {TableData, InputTable, InputExpression, Tips}
}
</script>

<style scoped>

</style>

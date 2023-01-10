<template>
  <el-collapse @change="handleChange">
    <el-collapse-item title="Tips 使用指南">
      <div align="left">1. 自定义关系时，所有参数都不可以留空，关系名可以由英文大小写字母与阿拉伯数字构成，但必须以英文字母开头。</div>
      <div align="left">2. 键入多个列名之间使用英文逗号分隔，列名的命名规则同关系名。</div>
      <div align="left">3. 键入关系表内容时每一项之间通过英文逗号分隔，行间可以换行，也可以不换。</div>
      <div align="left">4. 通过点击“确认关系”来提交一个新增关系，点击“删除”来删除已提交的关系，点击“新增关系”以键入更多自定义关系，点击“重置”以删除所有已提交关系。</div>
      <div align="left">5. 表达式中的运算符请通过按钮输入，请勿自行输入，否则可能导致算符不识别。</div>
      <div align="left">6. 连接运算目前仅支持自然连接。</div>
      <div align="left">7. 投影运算的使用范例：π[Sno,Sage][Student]，左侧方括号内为需要投影的列列名，中间以英文逗号分隔，右侧方括号内为运算的关系名。</div>
      <div align="left">8. 选择运算的使用范例：σ[Sage≥19∧Ssex=女][Student]，左侧方括号内为条件表达式，支持多个不等式的与或以及括号，右侧方括号内为运算的关系名。</div>
      <div align="left">9. 关系表达式的默认优先级为：( ) > σ = π > ⋈ > − > × = ÷ > ∩ > ∪。</div>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
export default {
  name: 'Tips',
  methods: {
    handleChange (val) {
      console.log(val)
    }
  }
}
</script>

<style scoped>

</style>

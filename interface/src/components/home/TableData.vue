<template>
  <el-form>
    学生表 Student<el-button @click="submitStudent" style="float: right">添加</el-button>
    <el-table
      :data="Student"
      style="width: 100%">
      <el-table-column
        prop="Sno"
        label="Sno"
        width="180">
      </el-table-column>
      <el-table-column
        prop="Sname"
        label="Sname"
        width="180">
      </el-table-column>
      <el-table-column
        prop="Ssex"
        label="Ssex">
      </el-table-column>
      <el-table-column
        prop="Sage"
        label="Sage">
      </el-table-column>
    </el-table>
    课程表 Course<el-button @click="submitCourse" style="float: right">添加</el-button>
    <el-table
      :data="Course"
      style="width: 100%">
      <el-table-column
        prop="Cno"
        label="Cno"
        width="180">
      </el-table-column>
      <el-table-column
        prop="Cname"
        label="Cname"
        width="180">
      </el-table-column>
      <el-table-column
        prop="Ccredit"
        label="Ccredit">
      </el-table-column>
    </el-table>
    成绩表 SC<el-button @click="submitSC" style="float: right">添加</el-button>
    <el-table
      :data="SC"
      style="width: 100%">
      <el-table-column
        prop="Sno"
        label="Sno"
        width="180">
      </el-table-column>
      <el-table-column
        prop="Cno"
        label="Cno"
        width="180">
      </el-table-column>
      <el-table-column
        prop="Grade"
        label="Grade">
      </el-table-column>
    </el-table>
  </el-form>
</template>

<script>
export default {
  name: 'TableData',
  data () {
    return {
      Student: [{
        Sno: '95001',
        Sname: '李勇',
        Ssex: '男',
        Sage: '20'
      }, {
        Sno: '95002',
        Sname: '刘晨',
        Ssex: '女',
        Sage: '19'
      }],
      Course: [{
        Cno: '1',
        Cname: '数据库',
        Ccredit: '4'
      }, {
        Cno: '2',
        Cname: '操作系统',
        Ccredit: '3'
      }, {
        Cno: '3',
        Cname: '数据结构',
        Ccredit: '4'
      }],
      SC: [{
        Sno: '95001',
        Cno: '1',
        Grade: '92'
      }, {
        Sno: '95001',
        Cno: '2',
        Grade: '65'
      }, {
        Sno: '95002',
        Cno: '2',
        Grade: '90'
      }, {
        Sno: '95002',
        Cno: '3',
        Grade: '73'
      }]
    }
  },
  methods: {
    submitStudent () {
      this.$axios
        .post('/insert/', {
          'relation_name': 'Student',
          'row_len': 2,
          'col_len': 4,
          'col_name': 'Sno,Sname,Ssex,Sage',
          'content': '95001,李勇,男,20,95002,刘晨,女,19'
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('提交学生表成功！', '提交现成关系', {
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
    submitCourse () {
      this.$axios
        .post('/insert/', {
          'relation_name': 'Course',
          'row_len': 3,
          'col_len': 3,
          'col_name': 'Cno,Cname,Ccredit',
          'content': '1,数据库,4,2,操作系统,3,3,数据结构,4'
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('提交课程表成功！', '提交现成关系', {
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
    submitSC () {
      this.$axios
        .post('/insert/', {
          'relation_name': 'SC',
          'row_len': 4,
          'col_len': 3,
          'col_name': 'Sno,Cno,Grade',
          'content': '95001,1,92,95001,2,65,95002,2,90,95002,3,73'
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('提交成绩表成功！', '提交现成关系', {
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

<template>
  <el-form>
    <h3 style="float: left">添加现成表</h3>
    <br><br><br><br>
    <strong>学生表 Student</strong><el-button @click="submitStudent" style="float: right" :disabled="student.dis">添加</el-button>
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
    <br>
    <strong>课程表 Course</strong><el-button @click="submitCourse" style="float: right" :disabled="course.dis">添加</el-button>
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
    <br>
    <strong>成绩表 SC</strong><el-button @click="submitSC" style="float: right" :disabled="sc.dis">添加</el-button>
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
      student: {
        dis: false
      },
      course: {
        dis: false
      },
      sc: {
        dis: false
      },
      Student: [{
        Sno: '95001',
        Sname: 'LiHua',
        Ssex: 'Male',
        Sage: '20'
      }, {
        Sno: '95002',
        Sname: 'LiuFang',
        Ssex: 'Female',
        Sage: '19'
      }],
      Course: [{
        Cno: '1',
        Cname: 'DataBase',
        Ccredit: '4'
      }, {
        Cno: '2',
        Cname: 'OperationSystem',
        Ccredit: '3'
      }, {
        Cno: '3',
        Cname: 'DateStructure',
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
          'content': '95001,LiHua,Male,20,95002,LiuCheng,Female,19'
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
            this.student.dis = true
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
          'content': '1,DataBase,4,2,OperationSystem,3,3,DateStructure,4'
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
            this.course.dis = true
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
            this.sc.dis = true
          }
        })
    }
  }
}
</script>

<style scoped>

</style>

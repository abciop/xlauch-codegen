<template>
  <el-dialog v-model="visible" title="生成代码" :close-on-click-modal="false" draggable>
    <el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="表名" prop="tableName">
            <el-input v-model="dataForm.tableName" disabled placeholder="表名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="说明" prop="tableComment">
            <el-input v-model="dataForm.tableComment" placeholder="说明"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="类名" prop="className">
            <el-input v-model="dataForm.className" placeholder="类名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="baseclassId" label="继承">
            <el-select v-model="dataForm.baseclassId" placeholder="继承" style="width: 100%" clearable>
              <el-option v-for="item in baseClassList" :key="item.id" :label="item.code" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="模块名" prop="moduleName">
            <el-input v-model="dataForm.moduleName" placeholder="模块名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="功能名" prop="functionName">
            <el-input v-model="dataForm.functionName" placeholder="功能名"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目包名" prop="packageName">
            <el-input v-model="dataForm.packageName" placeholder="项目包名"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="版本号" prop="version">
            <el-input v-model="dataForm.version" placeholder="版本号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="默认作者" prop="author">
            <el-input v-model="dataForm.author" placeholder="默认作者"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作者邮箱" prop="email">
            <el-input v-model="dataForm.email" placeholder="作者邮箱"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="生成方式" prop="generatorType">
            <el-radio-group v-model="dataForm.generatorType">
              <el-radio :label="0">zip压缩包</el-radio>
              <el-radio :label="1">自定义路径</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="表单布局" prop="formLayout">
            <el-radio-group v-model="dataForm.formLayout">
              <el-radio :label="1">一列</el-radio>
              <el-radio :label="2">两列</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider></el-divider>

      <el-form-item label="所属项目" prop="projectId">
        <el-select v-model="dataForm.projectId" style="width: 100%" placeholder="请选择所属项目" @change="onProjectChange">
          <el-option v-for="project in projectList" :key="project.id" :label="project.projectName"
                     :value="project.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="dataForm.generatorType === 1" label="后端生成路径" prop="backendPath">
        <el-input v-model="dataForm.backendPath" disabled placeholder="后端生成路径"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.generatorType === 1" label="前端生成路径" prop="frontendPath">
        <el-input v-model="dataForm.frontendPath" disabled placeholder="前端生成路径"></el-input>
      </el-form-item>

      <el-form-item label="模板分组" prop="groupId">
        <el-select v-model="dataForm.groupId" placeholder="模板分组" style="width: 100%" clearable @change="onGroupChange">
          <el-option v-for="item in groupList" :key="item.id" :label="item.groupName" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择模板" prop="checkTemplates">
        <div>
          <el-checkbox v-model="isCheckAll" @change="handleCheckAllChange">全选</el-checkbox>
          <el-checkbox-group v-model="dataForm.checkTemplates">
            <el-checkbox v-for="template in templateList" :label="template.id"
                         :key="template.id">{{ template.simpleName }}
            </el-checkbox>
          </el-checkbox-group>
        </div>
      </el-form-item>

    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle()">保存</el-button>
      <el-button type="danger" @click="generatorHandle()">生成代码</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {reactive, ref, toRaw} from 'vue'
import {ElMessage} from 'element-plus/es'
import {useBaseClassListApi} from '@/api/baseClass'
import {useGeneratorApi, useDownloadApi, getGenerTemplates, useGeneratorApiWithTemplates} from '@/api/generator'
import {useTableApi, useTableSubmitApi} from '@/api/table'
import {getGroupList} from '@/api/templateGroup'
import {getProjectList} from "@/api/project";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()
const baseClassList = ref<any[]>([])
const dataForm = reactive({
  id: '',
  baseclassId: '',
  generatorType: 0,
  formLayout: 1,
  projectId:'',
  backendPath: '',
  frontendPath: '',
  packageName: '',
  email: '',
  author: '',
  version: '',
  moduleName: '',
  functionName: '',
  className: '',
  tableComment: '',
  tableName: '',
  groupId: null,
  // 选中的模板
  checkTemplates: ref<any[]>([])
})

// 全选复选框
const isCheckAll = ref(true)
// 模板组列表
const groupList = ref<any[]>([])
// 模板列表
const templateList = ref<any[]>([])
// 模板id集合
const templateIdList = ref<any[]>([])
// 项目列表
const projectList = ref<any[]>([])

/**
 * 初始化
 */
const init = (id: number) => {
  visible.value = true
  dataForm.id = ''
  templateList.value = []
  templateIdList.value= []

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields()
  }

  getBaseClassList()
  getTable(id)
  getProject()

}

/**
 * 获取基类
 */
const getBaseClassList = () => {
  useBaseClassListApi().then(res => {
    baseClassList.value = res.data
  })
}

/**
 * 获取表格数据
 */
const getTable = (id: number) => {
  useTableApi(id).then(res => {
    Object.assign(dataForm, res.data)
    getTemplateGroupList();
  })
}

// 获取项目列表
const getProject = () => {
  getProjectList().then(res => {
    projectList.value = res.data
  })
}


/**
 * 切换项目
 */
function onProjectChange(projectId: number){
  for (let i in projectList.value) {
    let project = projectList.value[i]
    if (project.id == projectId) {
      dataForm.backendPath = project.backendPath
      dataForm.frontendPath = project.frontendPath
      break
    }
  }
}

/**
 * 切换分组
 */
function onGroupChange(groupId: number) {
  changeTemplateList(groupId)
}




/**
 * 根据分组id切换模板列表
 */
function changeTemplateList(groupId: number) {
  for (let i in groupList.value) {
    let group = groupList.value[i]
    if (group.id == groupId) {
      templateList.value = group.templateList;
      checkAll(group.templateList)
      break
    }
  }
}

/**
 * 获取模板分组列表
 */
const getTemplateGroupList = () => {
  getGroupList().then(res => {
    if (res.data) {
      groupList.value = res.data
      let groupId = dataForm.groupId ? dataForm.groupId : res.data[0].groupId;
      changeTemplateList(groupId)
    }
  })
}

/**
 * 全选
 */
function checkAll(templateList: any) {
  templateList.forEach((item: any) => {
    //@ts-ignore
    dataForm.checkTemplates.push(item.id)
    templateIdList.value.push(item.id)
  })

  isCheckAll.value = true
}


const handleCheckAllChange = (val: any) => {
  dataForm.checkTemplates = val ? templateIdList.value : []
}

/**
 * 表单校验规则
 */
const dataRules = ref({
  tableName: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  tableComment: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  className: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  packageName: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  author: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  moduleName: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  projectId: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  functionName: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  generatorType: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  checkTemplates: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  formLayout: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  backendPath: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  frontendPath: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
  groupId: [{required: true, message: '必填项不能为空', trigger: 'blur'}]
})

// 保存
const submitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false
    }

    useTableSubmitApi(dataForm).then(() => {
      ElMessage.success({
        message: '操作成功',
        duration: 500,
        onClose: () => {
          visible.value = false
          emit('refreshDataList')
        }
      })
    })
  })
}

// 生成代码
const generatorHandle = () => {
  dataFormRef.value.validate(async (valid: boolean) => {
    if (!valid) {
      return false
    }

    // 先保存
    await useTableSubmitApi(dataForm)

    // 生成代码，zip压缩包
    if (dataForm.generatorType === 0) {
      useDownloadApi([dataForm.id], toRaw(dataForm.checkTemplates))
      visible.value = false
      return
    }

    // 生成代码，自定义路径
    useGeneratorApiWithTemplates([dataForm.id], toRaw(dataForm.checkTemplates)).then(() => {
      ElMessage.success({
        message: '操作成功',
        duration: 500,
        onClose: () => {
          visible.value = false
          emit('refreshDataList')
        }
      })
    })
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>
.generator-code .el-dialog__body {
  padding: 15px 30px 0 20px;
}
</style>

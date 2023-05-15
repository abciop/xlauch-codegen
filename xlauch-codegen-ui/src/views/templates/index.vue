<template>
  <el-card>
    <div style="margin-bottom: 20px">
      <el-button type="primary" @click="addGroup"> 新增分组</el-button>
    </div>

    <el-tabs v-model="activeName" type="border-card" @tab-change="tabChange">
      <el-tab-pane v-for="group in dataForm.groupList" :name="group.groupName">
        <template #label>
          <div style="display: flex; align-items: center">
						<span class="el-dropdown-link">
							{{ group.groupName }}
						</span>
            <el-dropdown v-if="activeName === group.groupName">
              <el-icon style="margin-left: 5px">
                <Setting/>
              </el-icon>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="editGroup(group)">修改</el-dropdown-item>
                  <el-dropdown-item @click="onDeleteGroup(group.id)">删除</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </template>
        <div style="margin-bottom: 10px">
          <el-button link type="primary" size="small" @click="onAdd(group.id)">新增模板</el-button>
          <el-button link type="primary" size="small" @click="onImportTemplates(group.id)">批量导入</el-button>
          <el-button link type="primary" size="small" @click="exportTemplate(group.id)">导出模板</el-button>
        </div>
        <el-table :data="group.templateList" border style="width: 100%">
          <el-table-column prop="simpleName" label="模板名称" width="180"></el-table-column>
          <el-table-column prop="templateName" label="文件名称" width="180"></el-table-column>
          <el-table-column prop="generatorPath" label="文件目录"></el-table-column>
          <el-table-column fixed="right" width="180" label="操作">
            <template #default="{row}">
              <el-button link type="primary" size="small" @click="onEdit(row)">修改</el-button>
              <el-button link type="primary" size="small" @click="onDelete(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </el-card>

  <el-dialog v-model="visible" :title="isEdit ? '编辑分组' : '新增分组'" width="30%" draggable @closed="onClose">
    <el-form ref="dataFormRef" :model="dataForm" :rules="dataRules">
      <el-form-item label="分组名称" label-width="100px" prop="groupName">
        <el-input v-model="dataForm.groupName"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle()">确定</el-button>
    </template>
  </el-dialog>

  <el-dialog title="批量导入模板" v-model="importDialogVisible" @closed="onImportDialogClosed">
    <el-upload v-if="!importLoading" drag multiple accept=".zip" action="http://xx.nothing" :limit="20"
               :show-file-list="false"
               :http-request="uploadTemplates">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>


    <el-table v-if="importLoading" :data="importFiles" style="width: 100%">
      <el-table-column prop="name" label="文件" width="180"></el-table-column>
      <el-table-column prop="state" label="状态"></el-table-column>
    </el-table>
  </el-dialog>

  <!--編輯-->
  <Edit v-model="editVisible" :form-data="editFormData" :is-add="isAdd" @refreshDataList="getTmplatesGroupList()"/>
</template>

<script setup lang="ts">
import {useCrud} from '@/hooks'
import {reactive, ref} from 'vue'
import {Setting} from '@element-plus/icons-vue'
import {IHooksOptions} from '@/hooks/interface'
import {getGroupList, saveGroup, deleteGroup, downloadTemplate} from '@/api/templateGroup'
import {saveTemplate, deleteTemplate} from '@/api/template'
import {ElMessage, ElMessageBox} from 'element-plus'
import Edit from './edit.vue'
import service from "@/utils/request";

const activeName = ref()
const state: IHooksOptions = reactive({
  dataListUrl: '/gen/templates/page',
  deleteUrl: '/gen/templates'
})

const visible = ref(false)
const isEdit = ref(false)
const editVisible = ref(false)

// 上传
const importDialogVisible = ref(false)
const importLoading = ref(false)
const importFiles = []
const importGroupId = ref("") ;


const dataForm = reactive({
  groupList: [],
  groupName: '',
  id: null
})
const editFormData = ref(null)
const isAdd = ref(true)

const dataFormRef = ref()
const dataRules = ref({
  groupName: [{required: true, message: '必填项不能为空', trigger: 'blur'}]
})

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * 文件上传
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * **/
function onImportTemplates(groupId) {
  importFiles.value = []
  importLoading.value = false
  importDialogVisible.value = true
  importGroupId.value = groupId;
  console.log(groupId)
}

function uploadTemplates(target) {
  importLoading.value = true
  const file = target.file
  importFiles.push({
    name: file.name,
    state: "上传中..."
  })

  //执行上传
  const ossData = new FormData()
  //key就代表文件层级和阿里云上的文件名
  let filename = file.name
  ossData.append('name', filename)
  ossData.append('file', file, filename)


  fetch(import.meta.env.VITE_API_URL + "/gen/templateGroup/upload/"+ importGroupId.value, {
    method: 'post',
    body: ossData
  })
  .then(res => {
    ElMessage.success('导入成功')
  })
  .catch((res) =>  {
    ElMessage.success('导入成功')
  })
  .finally(() => {
    importFiles.value = []
    importLoading.value = false
    importDialogVisible.value = false
    getTmplatesGroupList()
  })

}


/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * 编辑
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * **/
function onEdit(row: any) {
  editVisible.value = true
  isAdd.value = false
  editFormData.value = row
}

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * 新增
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * **/
function onAdd(groupId: number) {
  editVisible.value = true
  isAdd.value = true
  editFormData.value = {
    groupId: groupId,
    templateContent: `

    `
  }
}


function onClose() {
  dataForm.id = null
  dataForm.groupName = ''
}

function onImportDialogClosed() {

}

/**
 * 删除模板
 */
function onDelete(id: number) {
  ElMessageBox.confirm('确定进行删除操作?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(() => {
        deleteTemplate(id).then(() => {
          ElMessage.success('删除成功')
          getTmplatesGroupList()
        })
      })
      .catch(() => {
      })
}

/**
 * 删除分组
 */
function onDeleteGroup(id: number) {
  ElMessageBox.confirm('确定进行删除操作?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(() => {
        deleteGroup(id).then(() => {
          ElMessage.success('删除成功')
          getTmplatesGroupList()
          activeName.value = null;
        })
      })
      .catch(() => {
      })
}

/**
 * tab 切换
 */
function tabChange(tabName) {
  activeName.value = tabName;
}

/**
 * 获取模板列表
 */
const submitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false
    }

    saveGroup(dataForm).then(() => {
      ElMessage.success({
        message: '操作成功',
        duration: 500,
        onClose: () => {
          visible.value = false
          getTmplatesGroupList()
        }
      })
    })
  })
}

/**
 * 导出模板
 */
const exportTemplate = (id?: number) => {
  downloadTemplate(id)
}

/**
 * 获取模板列表
 */
const getTmplatesGroupList = () => {
  getGroupList().then(res => {
    if (res.data) {
      dataForm.groupList = res.data
      if (res.data) {
        activeName.value = activeName.value ? activeName.value : res.data[0].groupName
      }
    }
  })
}

/**
 * 新增分组
 */
const addGroup = () => {
  isEdit.value = false
  visible.value = true
}

/**
 * 修改分组
 */
const editGroup = (group: any) => {
  console.log(group)
  Object.assign(dataForm, group)
  isEdit.value = true
  visible.value = true
}

getTmplatesGroupList()
</script>

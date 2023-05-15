<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="120px" @keyup.enter="submitHandle()">
			<el-form-item label="项目名称" prop="projectName">
				<el-input v-model="dataForm.projectName" placeholder="项目名称，如：xlauch"></el-input>
			</el-form-item>
			<el-form-item label="包名" prop="packageName">
				<el-input v-model="dataForm.packageName" placeholder="包名，如：com.xlauch"></el-input>
			</el-form-item>
			<el-form-item label="版本号" prop="version">
				<el-input v-model="dataForm.version" placeholder="版本号"></el-input>
			</el-form-item>
			<el-form-item label="后端项目路径" prop="backendPath">
				<el-input v-model="dataForm.backendPath" placeholder="后端项目路径"></el-input>
			</el-form-item>
			<el-form-item label="前端项目路径" prop="frontendPath">
				<el-input v-model="dataForm.frontendPath" placeholder="前端项目路径"></el-input>
			</el-form-item>
			<el-form-item label="作者" prop="author">
				<el-input v-model="dataForm.author" placeholder="作者"></el-input>
			</el-form-item>
			<el-form-item label="邮箱地址" prop="email">
				<el-input v-model="dataForm.email" placeholder="邮箱地址"></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus/es'
import { useProjectApi, useProjectSubmitApi } from '@/api/project'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	dbType: '',
	projectName: '',
	packageName: '',
	version: '',
	backendPath: '',
  frontendPath: '',
  author: '',
})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	// id 存在则为修改
	if (id) {
		getProject(id)
	}
}

const getProject = (id: number) => {
	useProjectApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

const dataRules = ref({
	dbType: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
	projectName: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
	packageName: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
	version: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
	backendPath: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
  frontendPath: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
  author: [{ required: true, message: '必填项不能为空', trigger: 'blur' }],
})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useProjectSubmitApi(dataForm).then(() => {
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

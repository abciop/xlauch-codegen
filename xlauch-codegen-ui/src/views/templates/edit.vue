<template>
	<el-dialog fullscreen v-model="editViisble" :title="props.isAdd ? '新增模板':'编辑模板'" @closed="onClose" @opened="onOpen">
		<el-row :gutter="20">
			<el-col :span="16">
				<el-form label-position="top" :rules="rules" ref="formRef" :model="formData">
<!--					<el-form-item label="组名称" prop="groupId">-->
<!--						<el-select v-model="formData.groupId" placeholder="选择模板所在组">-->
<!--							<el-option v-for="item in groupData" :key="item.id" :label="item.groupName" :value="item.id">-->
<!--								{{ item.groupName }}-->
<!--							</el-option>-->
<!--						</el-select>-->
<!--					</el-form-item>-->

					<el-form-item label="模板名称" prop="simpleName">
						<el-input v-model="formData.simpleName" show-word-limit maxlength="64" />
					</el-form-item>

					<el-form-item prop="templateName" label="文件名称">
						<el-input v-model="formData.templateName" placeholder="可使用velocity变量" show-word-limit maxlength="100" />
					</el-form-item>

          <el-form-item prop="generatorPath" label="文件目录">
            <el-input v-model="formData.generatorPath" placeholder="为空则是模板名" show-word-limit maxlength="200" />
          </el-form-item>

					<el-form-item prop="templateContent" label="模板内容">
						<div>
							<el-link type="primary" :underline="false" href="https://www.cnblogs.com/xiaoqiangzai/p/14526605.html" target="_blank">
                Freemarker 语法
							</el-link>

							<codemirror @ready="readyHandle" v-model="formData.templateContent" :extensions="extensions" />
						</div>
					</el-form-item>
				</el-form>
			</el-col>
			<el-col :span="8">
				<el-affix :offset="0">
					<div style="font-size: 14px;max-height: calc(100vh - 40px);overflow: auto">
						<h4 style="margin: 5px 0">
							Velocity变量
							<span class="velocity-tip"> 点击变量直接插入 </span>
						</h4>
						<el-tabs v-model="activeName" @tab-click="onTabClick">
							<el-tab-pane v-for="item in velocityConfig" :key="item.name" :label="item.label" :name="item.name" :content="item" />
						</el-tabs>
						<div class="velocity-var">
							<el-tree :data="treeData" :props="defaultProps" :indent="4" accordion>
								<template #default="{ data }">
									<span v-if="data.children && data.children.length > 0">{{ data.expression }}</span>
									<span v-else>
										<a @click="onExpressionClick(data.expression)" class="linkFont">{{ data.expression }}</a
										>：{{ data.text }}
									</span>
								</template>
							</el-tree>
						</div>
					</div>
				</el-affix>
			</el-col>
		</el-row>
		<div style="position: fixed;left:0;width: 100%;bottom: 0;display: flex;justify-content: center;height: 60px">
			<el-button @click="editViisble = false">返回</el-button>
			<el-button type="primary" @click="submit">保存</el-button>
		</div>
	</el-dialog>
</template>

<script setup>
import { reactive, ref, watch, defineEmits, defineProps } from 'vue'
import { Codemirror } from 'vue-codemirror'
import { java } from '@codemirror/lang-java'
import { saveTemplate } from '@/api/template'
import { ElMessage } from 'element-plus/es'

const props = defineProps({
  modelValue:Boolean,
  isAdd:Boolean,
  formData:{
    type:Object,
    default:()=>({})
  }
})
const emits = defineEmits(['update:modelValue','refreshDataList'])
const formRef = ref()
const editViisble = ref(false)
const extensions = [java()]
const formData = reactive({
  simpleName: '',
	groupId: null,
	generatorPath: '',
	templateName: '',
	templateContent: ''
})
const rules = {
  simpleName:{required:true,message:'请填写模板名称'},
  generatorPath:{required:true,message:'请填写模板名称'},
  groupId:{required:true,message:'选择模板所在组'},
  templateName:{required:true,message:'选填写文件名称'},
  templateContent:{required:true,message:'选填写模板内容'}
}
const editor = ref()
const groupData = ref([])

const activeName = ref('java')
const velocityConfig = ref([
	{
		name: 'java',
		label: 'Java变量'
	}
])
const treeData = ref([
	{
		expression: '基础信息',
		text: '模板数据汇总',
		children: [
			{
				expression: '${dbType}',
				text: '数据库类型'
			},
			{
				expression: '${package}',
				text: '项目包名，如：com.xlauch'
			},
			{
				expression: '${packagePath}',
				text: '包名路径：如：com/xlauch'
			},
			{
				expression: '${version}',
				text: '项目版本号'
			},
			{
				expression: '${moduleName}',
				text: '项目模块名'
			},
			{
				expression: 'ModuleName',
				text: '项目模块名，首字母大写'
			},
			{
				expression: '${functionName}',
				text: '项目功能名'
			},
			{
				expression: '${FunctionName}',
				text: '项目功能名，首字母大写'
			},
			{
				expression: '${formLayout}',
				text: '表单布局'
			},
			{
				expression: '${author}',
				text: '作者（开发者）'
			},
      {
        expression: '${email}',
        text: '作者邮箱'
      },
      {
        expression: '${datetime}',
        text: '当前时间，如：2022-09-26 22:31:38'
      },
      {
        expression: '${date}',
        text: '当前日期，如：2022-09-26'
      },
      {
        expression: '${importList}',
        text: '实体类，需要导入的包名'
      },
      {
        expression: '${tableName}',
        text: '表名'
      },
      {
        expression: '${tableComment}',
        text: '表说明'
      },
      {
        expression: '${ClassName}',
        text: '类名'
      },
      {
        expression: '${className}',
        text: '类名，首字段小写'
      },
      {
        expression: '${baseClass}',
        text: '基类，实体类继承的类'
      },
      {
        expression: '${fieldList}',
        text: '全部字段列表 array[object]'
      },
      {
        expression: '${primaryList}',
        text: '主键字段列表 array[object]'
      },
      {
        expression: '${formList}',
        text: '表单字段 array[object]'
      },
      {
        expression: '${gridList}',
        text: '表单字段 array[object]'
      },
      {
        expression: '${列表字段}',
        text: '表单字段 array[object]'
      },
      {
        expression: '${queryList}',
        text: '查询字段 array[object]'
      },
      {
        expression: '${backendPath}',
        text: '后端路径'
      },
      {
        expression: '${frontendPath}',
        text: '前端路径'
      }
		]
	},
	{
		expression: '${baseClass}',
		text: 'baseClass数据',
		children: [
			{
				expression: '${packageName}',
				text: '包名，如：net.maku.framework.common.entity'
			},
			{
				expression: '${baseClass.code}',
				text: '基类类名：如：BaseEntity'
			},
			{
				expression: '${baseClass.fields}',
				text: '基类字段，多个用英文逗号分隔'
			}
		]
	},
	{
		expression: '${field}',
		text: '字段数据',
		children: [
      {
        expression: '${field.fieldName}',
        text: '字段名称'
      },
      {
        expression: '${field.fieldType}',
        text: '字段类型'
      },
      {
        expression: '${field.fieldComment}',
        text: '字段说明'
      },
      {
        expression: '${field.attrName}',
        text: '属性名称'
      },
      {
        expression: '${field.attrType}',
        text: '属性类型'
      },
      {
        expression: '${field.autoFill}',
        text: '自动填充 【DEFAULT、INSERT、UPDATE、INSERT_UPDATE】'
      },
      {
        expression: '${field.primaryPk}',
        text: '是否主键'
      },
      {
        expression: '${field.baseField}',
        text: '是否基类字段'
      },
      {
        expression: '${field.formItem}',
        text: '是否表单字段'
      },
      {
        expression: '${field.formRequired}',
        text: '是否表单必填'
      },
      {
        expression: '${field.formType}',
        text: '表单类型'
      },
      {
        expression: '${field.formDict}',
        text: '表单字典类型'
      },
      {
        expression: '${field.formValidator}',
        text: '表单效验'
      },
      {
        expression: '${field.gridItem}',
        text: '是否列表字段'
      },
      {
        expression: '${field.gridSort}',
        text: '列表排序'
      },
      {
        expression: '${field.gridItem}',
        text: '是否列表字段'
      },
      {
        expression: '${field.queryItem}',
        text: '是否查询字段'
      },
      {
        expression: '${field.queryType}',
        text: '查询方式'
      },
      {
        expression: '${field.queryFormType}',
        text: '查询表单类型'
      }
    ]
	}
])
const defaultProps = reactive({
	children: 'children',
	label: 'expression'
})
watch(()=>props.modelValue,nv=>{
  editViisble.value = nv
})
watch(()=>editViisble.value,nv=>emits('update:modelValue',nv))

function readyHandle(ref) {
	editor.value = ref
}

function onTabClick(tab) {
	console.log(tab)
}

function onExpressionClick(exp) {
	const { view } = editor.value
	view.dispatch(
		view.state.changeByRange(range => {
			return {
				changes: [{ from: range.from, to: range.to, insert: exp }],
				range
			}
		})
	)
}

function onOpen(){
  // if(!props.isAdd){
  //   /*现在是编辑模式，可以给formData进行回填数据*/
  //   /*从props.formData拿到传递过来的数据*/
  //   Object.assign(formData, props.formData)
  // }
  Object.assign(formData, props.formData)
}

function onClose(){
  formData.simpleName = ''
  formData.groupId = null
  formData.generatorPath = ''
  formData.templateName = ''
  formData.templateContent = `

  `
}

/**
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * 提交
 * ++++++++++++++++++++++++++++++++++++++++++++++++
 * **/
function submit(){
  formRef.value.validate(valid=>{
    if(valid){

      saveTemplate(formData).then(() => {
        ElMessage.success({
          message: '操作成功',
          duration: 500,
          onClose: () => {
            editViisble.value = false
            emits('refreshDataList')
          }
        })
      })
    }
  })
}

</script>

<style scoped>
.hasFix {
	position: fixed;
	top: 0;
}
.linkFont {
	color: #409eff;
	font-weight: 500;
}
</style>

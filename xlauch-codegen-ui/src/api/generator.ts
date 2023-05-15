import service from '@/utils/request'

// 生成代码（zip压缩包）
export const useDownloadApi = (tableIds: any[] , templates: any[]) => {
	location.href = import.meta.env.VITE_API_URL + '/gen/generator/download?tableIds=' + tableIds.join(',') + "&templates="+templates.join(",")
}

// 生成代码（自定义目录）
export const useGeneratorApi = (tableIds: any[]) => {
	return service.post('/gen/generator/code', tableIds)
}
export const useGeneratorApiWithTemplates = (tableIds: any[] , templates: any[]) => {
	return service.post('/gen/generator/codeWithTemplate', {
		tableIds : tableIds ,
		templates : templates
	})
}

// 获取模板
export const getGenerTemplates = () => {
	return service.post('/gen/generator/getTemplates')
}
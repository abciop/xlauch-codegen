import service from '@/utils/request'

export const useProjectApi = (id: number) => {
	return service.get('/gen/project/' + id)
}

export const getProjectList = () => {
	return service.get('/gen/project/list')
}

export const useProjectSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/gen/project', dataForm)
	} else {
		return service.post('/gen/project', dataForm)
	}
}



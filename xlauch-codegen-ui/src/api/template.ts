import service from '@/utils/request'


// 保存模板
export const saveTemplate = (dataForm: any) => {
    if (dataForm.id) {
        return service.put('/gen/template', dataForm)
    } else {
        return service.post('/gen/template', dataForm)
    }
}

// 删除模板
export const deleteTemplate = (id: number) => {
    if(id){
        return service.get('/gen/template/delete/' + id)
    }
}


// 导出模板（zip压缩包）
export const downloadTemplate = (id?: number) => {
    location.href = import.meta.env.VITE_API_URL + '/gen/template/download?id=' + id
}
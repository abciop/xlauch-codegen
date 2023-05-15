import service from '@/utils/request'


// 获取模板列表
export const getGroupList = () => {
    return service.get('/gen/templateGroup/list')
}

// 获取模板列表
export const saveGroup = (dataForm: any) => {
    if (dataForm.id) {
        return service.put('/gen/templateGroup', dataForm)
    } else {
        return service.post('/gen/templateGroup', dataForm)
    }
}

// 删除模板
export const deleteGroup = (id: number) => {
    if(id){
        return service.get('/gen/templateGroup/delete/' + id)
    }
}


// 导出模板（zip压缩包）
export const downloadTemplate = (id?: number) => {
    location.href = import.meta.env.VITE_API_URL + '/gen/templateGroup/download?id=' + id
}
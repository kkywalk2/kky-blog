import axios from 'axios'

// TODO: blob 타입 확인필요
const uploadImage = (blob) => {
    let headers = {
        'Content-type': 'multipart/form-data'
    }

    let formData = new FormData()
    formData.append("imageFile", blob)


    return axios.post(`/image`, formData, { headers })
}

const getImage = (path) => {
    let headers = {
        'Content-type': 'multipart/form-data'
    }

    return axios.get(`/image/${path}`, { headers })
}

export default { uploadImage, getImage }

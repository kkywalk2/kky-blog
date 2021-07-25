import axios from 'axios'

const uploadImage = (blob) => {
    let headers = {
        'Content-type': 'multipart/form-data'
    }

    let formData = new FormData()
    formData.append("imageFile", blob)


    return axios.post(`${process.env.VUE_APP_SERVER_ADDRESS}/image`, formData, {headers})
}

const getImage = (path) => {
    let headers = {
        'Content-type': 'multipart/form-data'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/image/${path}`, {headers})
}

export default {uploadImage, getImage}
import axios from 'axios'

const createPost = (token, title, content, category) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    let postInfo = {
        title: title,
        content: content,
        category: category
    };

    return axios.post(`${process.env.VUE_APP_SERVER_ADDRESS}/post`, postInfo, {headers})
}

const getPosts = (token) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post`, {headers})
}

const getPost = (token, id) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/${id}`, {headers})
}

const getCategories = (token) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/category`, {headers})
}

const getPostByCategory = (token, category) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/category/${category}`, {headers})
}

export default {createPost, getPosts, getPost, getCategories, getPostByCategory}
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

const getPosts = () => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post`, {headers})
}

const getPost = (id) => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/${id}`, {headers})
}

const getCategories = () => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/category`, {headers})
}

const getPostByCategory = (category) => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/category/${category}`, {headers})
}

const updatePost = (token, postId, title, content, category) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    let postInfo = {
        postId: postId,
        title: title,
        content: content,
        category: category
    };

    return axios.put(`${process.env.VUE_APP_SERVER_ADDRESS}/post`, postInfo, {headers})
}

export default {createPost, getPosts, getPost, getCategories, getPostByCategory, updatePost}
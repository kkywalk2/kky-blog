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

    return axios.post(`${process.env.VUE_APP_SERVER_ADDRESS}/post`, postInfo, { headers })
}

const getPosts = (page, perPage) => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post?page=${page}&per_page=${perPage}`, { headers })
}

const getPost = (id) => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/${id}`, { headers })
}

const getCategories = () => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/category`, { headers })
}

const getPostByCategory = (category, page, perPage) => {
    let headers = {
        'Content-type': 'application/json'
    }

    return axios.get(`${process.env.VUE_APP_SERVER_ADDRESS}/post/category/${category}?page=${page}&per_page=${perPage}`, { headers })
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

    return axios.put(`${process.env.VUE_APP_SERVER_ADDRESS}/post`, postInfo, { headers })
}

const deletePost = (token, postId) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.delete(`${process.env.VUE_APP_SERVER_ADDRESS}/post/${postId}`, { headers })
}

export default { createPost, getPosts, getPost, getCategories, getPostByCategory, updatePost, deletePost }
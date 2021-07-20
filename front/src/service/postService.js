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

    return axios.post('http://localhost:9000/post', postInfo, {headers})
}

const getPosts = (token) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.get('http://localhost:9000/post', {headers})
}

export default {createPost, getPosts}
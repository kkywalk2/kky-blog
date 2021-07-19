import axios from 'axios'

const createPost = (token, content, category) => {
    let postInfo = {
        content: content,
        category: category
    };

    return axios.post('http://localhost:9000/post', postInfo, {headers: {'Authorization': token}})
}

const getPosts = (token) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    return axios.get('http://localhost:9000/post', {headers})
}

export default {createPost, getPosts}
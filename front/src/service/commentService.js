import axios from 'axios'

const addComment = (token, postId, content) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    let postInfo = {
        postId: postId,
        content: content
    };

    return axios.post(`${process.env.VUE_APP_SERVER_ADDRESS}/comment`, postInfo, {headers})
}

export default {addComment}
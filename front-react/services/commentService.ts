import axios from 'axios'

const addComment = (token: string, postId: string, content: string) => {
    let headers = {
        'Content-type': 'application/json',
        'Authorization': `${token}`
    }

    let postInfo = {
        postId: postId,
        content: content
    };

    return axios.post(`/comment`, postInfo, {headers})
}

export default {addComment}

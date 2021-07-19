// src/service/index.js
import accountService from './accountService'
import postService from './postService'

const login = async (accountName, password) => {
    try {
        const loginResponse = await accountService.login(accountName, password)
        return loginResponse
    } catch (err) {
        console.error(err)
    }
}

const createPosts = async (token, content, category) => {
    try {
        const res = await postService.createPost(token, content, category)
        if(res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
    }
}

const getPosts = async (token) => {
    try {
        const res = await postService.getPosts(token)
        if(res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
    }
}

export {login, createPosts, getPosts}
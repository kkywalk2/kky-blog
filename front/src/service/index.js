// src/service/index.js
import accountService from '@/service/accountService'
import postService from '@/service/postService'
import imageService from "@/service/imageService";
import commentService from "@/service/commentService";

const login = async (accountName, password) => {
    try {
        return await accountService.login(accountName, password)
    } catch (err) {
        console.error(err)
        return null
    }
}

const createPosts = async (token, title, content, category) => {
    try {
        const res = await postService.createPost(token, title, content, category)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const getPosts = async (token) => {
    try {
        const res = await postService.getPosts(token)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const uploadImage = async (blob) => {
    try {
        const res = await imageService.uploadImage(blob)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const getImage = async (path) => {
    try {
        const res = await imageService.getImage(path)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const getPost = async (token, id) => {
    try {
        const res = await postService.getPost(token, id)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const getCategories = async (token) => {
    try {
        const res = await postService.getCategories(token)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const getPostByCategory = async (token, category) => {
    try {
        const res = await postService.getPostByCategory(token, category)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const addComment = async (token, postId, comment) => {
    try {
        const res = await commentService.addComment(token, postId, comment)
        if (res.status === 200)
            return res.data
        else
            return null
    } catch (err) {
        console.error(err)
        return null
    }
}

const checkAuthentication = async (token) => {
    try {
        const res = await accountService.checkAuthentication(token)
        if(res.status === 200)
            return true
        else
            return false
    } catch (err) {
        return false
    }
}

export {login, createPosts, getPosts, uploadImage, getImage, getPost, getCategories, getPostByCategory, addComment, checkAuthentication}
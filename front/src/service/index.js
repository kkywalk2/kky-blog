// src/service/index.js
import accountService from '@/service/accountService'
import postService from '@/service/postService'
import imageService from "@/service/imageService";

const login = async (accountName, password) => {
    try {
        const loginResponse = await accountService.login(accountName, password)
        return loginResponse
    } catch (err) {
        console.error(err)
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
    }
}

export {login, createPosts, getPosts, uploadImage, getImage}
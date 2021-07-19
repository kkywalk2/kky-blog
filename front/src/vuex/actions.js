// src/vuex/actions.js
import {TOKEN, IS_AUTH, ERROR_STATE} from './mutation_types'
import {login} from '../service'

let setToken = ({commit}, data) => {
    commit(TOKEN, data)
}

let setErrorState = ({commit}, data) => {
    commit(ERROR_STATE, data)
}

let setIsAuth = ({commit}, data) => {
    commit(IS_AUTH, data)
}

// 백엔드에서 반환한 결과값을 가지고 로그인 성공 실패 여부를 vuex에 넣어준다.
let processResponse = (store, loginResponse) => {
    switch (loginResponse) {
        case 'noAuth':
            setErrorState(store, 'Wrong ID or Password')
            setIsAuth(store, false)
            break
        case 'done':
            setErrorState(store, 'No period')
            setIsAuth(store, false)
            break
        default:
            setToken(store, 'Bearer ' + loginResponse.data.token)
            //console.log(store.getters.getToken)
            setErrorState(store, '')
            setIsAuth(store, true)
    }
}

export default {
    async loginAction(store, {accountName, password}) {
        let loginResponse = await login(accountName, password)
        processResponse(store, loginResponse)
        return store.getters.getIsAuth  // 로그인 결과를 리턴한다
    }

    /*async getPostsAction(store, { token }) {
        return await getPosts(token)
    },

    async createPostsAction(store, { token, content, category }) {
        return await createPosts(token, content, category)
    }*/
}
import React from "react";
import loadable from "@loadable/component";
import { Route, Routes, Navigate } from 'react-router-dom'

const Login = loadable(() => import("@pages/Login"))
const SignUp = loadable(() => import("@pages/SignUp"))
const PostList = loadable(() => import("@pages/PostList"))

const App = () => {
    return (
        <Routes>
            <Route path="/" element={<Navigate replace to="/login" />}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/signup" element={<SignUp/>}/>
            <Route path="/list" element={<PostList/>}/>
        </Routes>
    )
}

export default App;

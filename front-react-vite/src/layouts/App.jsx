import React from 'react'
import { Outlet } from 'react-router-dom'
import AuthorCard from '@/components/AuthorCard'

function App() {
  return (
    <>
      <div className='dark:bg-gray-900 block h-16'></div>
      <div className="flex flex-row">
        <AuthorCard/>
        <Outlet/>
      </div>
    </>
  )
}

export default App

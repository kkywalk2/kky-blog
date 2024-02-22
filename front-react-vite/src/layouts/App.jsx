import React from 'react'
import { Outlet } from 'react-router-dom'
import AuthorCard from '@/components/AuthorCard'

function App() {
  return (
    <>
      <div className='dark:bg-gray-900 block h-16'></div>
      <div className="flex flex-col md:flex-row py-6 lg:py-10 px-6 lg:px-16 space-x-4">
        <AuthorCard className='w-1/4'/>
        <Outlet className='w-3/4'/>
      </div>
    </>
  )
}

export default App

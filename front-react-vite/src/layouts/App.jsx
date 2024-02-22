import React from 'react'
import { Outlet } from 'react-router-dom'
import AuthorCard from '@/components/AuthorCard'

function App() {
  return (
    <>
      <div className="flex flex-col py-6 lg:py-10 px-6 lg:px-16 md:flex-row space-x-4">
        <AuthorCard className='w-1/4'/>
        <Outlet className='w-full'/>
      </div>
    </>
  )
}

export default App

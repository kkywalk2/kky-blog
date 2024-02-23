package com.example.blog.post.services.ports

import com.example.blog.post.domains.PostDomain
import java.util.Optional

interface PostRepository {

    fun save(postDomain: PostDomain): PostDomain

    fun findById(id: Long): Optional<PostDomain>


}

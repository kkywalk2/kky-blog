import React from 'react';
import { Container, Post, HeaderPost, BodyPost, PostContent, ContainerInfos, PostedBy, ContainerTags, Tags } from '@pages/Post/style'

const PostCard = () => {
    return (
        <Container>
            <Post>
                <HeaderPost>
                    <img src="https://images.pexels.com/photos/2529973/pexels-photo-2529973.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" alt=""/>
                </HeaderPost>
                <BodyPost>
                    <PostContent>
                        <h1>Lorem Ipsum</h1>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci animi assumenda cumque deserunt dolorum ex exercitationem.</p>
                        <ContainerInfos>
                            <PostedBy>
                                <span>author</span>
                                John Doe
                            </PostedBy>
                            <ContainerTags>
                                <span>tags</span>
                                <Tags>
                                    <ul>
                                        <li>design</li>
                                        <li>front end</li>
                                    </ul>
                                </Tags>
                            </ContainerTags>
                        </ContainerInfos>
                    </PostContent>
                </BodyPost>
            </Post>
        </Container>
    )
}

export default PostCard;

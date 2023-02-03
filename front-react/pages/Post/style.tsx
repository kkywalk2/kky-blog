import styled from "@emotion/styled";

export const Container = styled.div`
  width: 100%;
  height: 100vh;
  background-color: #232323;
  display: flex;
  justify-content: center;
  align-items: center;
`

export const Post = styled.div`
    width: 350px;
    height: 500px;
    display: flex;
    overflow: hidden;
    flex-direction: column;
    position: relative;

    &:hover {
      .header_post {
        margin-top: -20px;
      }

      .body_post {
        height: 50%;
      }
       
       img {
          transform: translatey(-10px) translatex(-5px) scale(1.05)

       }
    }
`

export const HeaderPost = styled.div`
    width: 100%;
      height: 40%;
      background: #ddd;
      position: absolute;
      top: 0;
      -webkit-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -moz-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -ms-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -o-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      & > img {
        max-width: 100%;
        height: auto;
         transition: ease-in-out 600ms;
      }

`

export const BodyPost = styled.div`
    width: 100%;
      height: 60%;
      background: #fff;
      position: absolute;
      bottom: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      -webkit-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -moz-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -ms-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -o-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      cursor: pointer;
`

export const PostContent = styled.div`
    width: 80%;
        height: 80%;
        background: #fff;
        position: relative;

        & > h1 {
          font-size: 20px;
          font-weight: bold;
        }

        & > p {
          font-size: 14px;
          font-weight: normal;
        }
`

export const ContainerInfos = styled.div`
    width: 100%;
          display: flex;
          justify-content: space-between;
          position: absolute;
          bottom: 0;
          border-top: 1px solid rgba(0, 0, 0, .2);
          padding-top: 25px;
`

export const PostedBy = styled.div`
    display: flex;
            flex-direction: column;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-size: 12px;

            & > span {
              font-size: 12px;
              text-transform: uppercase;
              opacity: 0.5;
              letter-spacing: 1px;
              font-weight: bold;
            }
`

export const ContainerTags = styled.div`
    display: flex;
            flex-direction: column;

            & > span {
              font-size: 12px;
              text-transform: uppercase;
              opacity: 0.5;
              letter-spacing: 1px;
              font-weight: bold;
            }
`

export const Tags = styled.div`
    & > ul {
                display: flex;
                & > li {
                  font-size: 12px;
                  letter-spacing: 2px;
                  list-style: none;
                  margin-left: 8px;
                  text-transform: uppercase;
                  position: relative;
                  z-index: 1;
                  display: flex;
                  justify-content: center;
                  cursor: pointer;

                  &:first-child {
                    margin-left: 0px;
                  }

                  &:before {
                    content: '';
                    text-align: center;
                    width: 100%;
                    height: 5px;
                    background: #FC6042;
                    opacity: 0.5;
                    position: absolute;
                    bottom: 0;
                    z-index: -1;
                    padding: 0px 1px;
      -webkit-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -moz-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -ms-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      -o-transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
      transition: cubic-bezier(0.68, -0.55, 0.27, 01.55) 320ms;
                  }

                  &:hover:before {
                    height: 18px;
                  }
                }
              }
`

package com.campers.data

class CommonData {
    companion object {
        /**
         * 현재 로그인한 유저 아이디
         */
        var myId = 0

        /**
         * 현재 로그인한 유저 닉네임
         */
        var myNickName = ""

        /**
         * 게시판 아이디
         */
        var boardId = 0

        /**
         * 게시판 타입
         */
        var boardType = ""

        /**
         * 게시판 게시물 아이디
         */
        var boardContentId = 0

        /**
         * 게시판 게시물을 작성한 유저 아이디
         */
        var userBoardContentId = 0
    }
}
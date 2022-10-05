
(function(){
//    var boardId = document.forms['editForm'].bord_id;
})();
//
function onClickEdit(form, action){
    form.action = (action == 'delete') ? '/delete' : '/edit';
    form.submit();
}
//
//function onClickAddReply(form){
//    var container = document.getElementById('replyFormArea');
//    var errorArea = container.querySelector('.error-msg');
//    console.log(errorArea)
//    errorArea.innerText = '';
//    var content = form.content.value;
//    var author = form.author.value;
//    var password = form.password.value;
//    var errorMsg = null;
//
//    console.log(content);
//    console.log(password);
//
//    if(content == ''){
//        errorMsg = '내용을 입력해 주세요.';
//    }else if(author == ''){
//        errorMsg = '작성자를 입력해 주세요.'
//    }else if(password == ''){
//        errorMsg = '비밀번호를 입력해 주세요.'
//    }
//    if(errorMsg != null){
//        errorArea.innerText = errorMsg;
//        return;
//    }
//
//    form.reset();
//    document.getElementById('btnShowReplyForm').click();
//
//
//}
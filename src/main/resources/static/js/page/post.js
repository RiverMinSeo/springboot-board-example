
(function(){
//    var boardId = document.forms['editForm'].bord_id;
loadReplies();
})();
function onClickAddReply(form){
    var data = $(form).serialize();
    var options = {
        method: 'POST',
        headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
    };
    fetch(boardId + '/reply', options)
    .then(response=>response.text())
    .then(html=>{
        $('#replyForm').replaceWith(html);
        loadReplies();
    })
    .catch(err=>{
        console.log(err)
    });
}

function loadReplies() {
    fetch('/'+boardId+'/replies')
    .then(response=>response.text())
    .then(html=>{
        $('#replies').replaceWith(html);
    })
    .catch(err=>{
       console.log(err)
    });
}

function onClickToggleEdit(form, action) {
    var btnEdit = form.querySelector('.btn-edit')
    var btnCancel = form.querySelector('.btn-cancel')
    var displayContent   = form.querySelector('.display-content')
    var editContent   = form.querySelector('.edit-content')

    btnEdit.classList.add((action == 'edit') ? 'd-none' : 'btn-block')
    displayContent.classList.add((action == 'edit') ? 'd-none' : 'd-block')
    btnEdit.classList.remove((action == 'edit') ? 'btn-block' : 'd-none')
    displayContent.classList.remove((action == 'edit') ? 'd-block' : 'd-none')

    btnCancel.classList.add((action == 'edit') ? 'btn-block' : 'd-none')
    editContent.classList.add((action == 'edit') ? 'd-block' : 'd-none')
    btnCancel.classList.remove((action == 'edit') ? 'd-none' : 'btn-block')
    editContent.classList.remove((action == 'edit') ? 'd-none' : 'd-block')
    if(action == 'edit') {
        form.content.focus()
    }
}

function onClickDeleteReply(form) {
    var errorArea = form.querySelector('.error-password')
    errorArea.innerText='';
    var data = {
        id: form.id.value,
        password: form.password.value
    }
    var options = {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    };
    fetch('/reply/delete', options)
    .then(response=>response.json())
    .then(json=>{
        if(!json.success){
            errorArea.innerText = json.message;
            return;
        }
        loadReplies()
    })
    .catch(err=>{
        console.log(err);
    });
}

function onClickSaveReply(form) {
    var pwdErrArea = form.querySelector('.error-password')
    var contentErrArea = form.querySelector('.error-content')
    pwdErrArea.innerText='';
    contentErrArea.innerText='';
    var data = {
        id: form.id.value,
        content: form.content.value,
        password: form.password.value
    }
    var options = {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    };
    fetch('/reply/save', options)
    .then(response=>response.json())
    .then(json=>{
        if(!json.success){
            pwdErrArea.innerText = json['error-password'] || '';
            contentErrArea.innerText = json['error-content'] || '';
            return;
        }
        loadReplies()
    })
    .catch(err=>{
        console.log(err);
    });
}
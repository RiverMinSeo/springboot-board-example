(function(){

})();


function onClickDelete(form) {
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
    fetch('/delete', options)
    .then(response=>response.json())
    .then(json=>{
        if(!json.success){
            errorArea.innerText = json.message;
            return;
        }
        window.location.replace('/')
    })
    .catch(err=>{
        console.log(err);
    });
}


// 获取用户信息并显示在页面上
fetch('/users/person')
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('网络错误');
    })
    .then(data => {
        const username = data.data.username;
        const email = data.data.email;
        const usernameElement = document.querySelector('.profile-info p:nth-child(1)');
        const emailElement = document.querySelector('.profile-info p:nth-child(2)');
        usernameElement.innerText = `姓名：${username}`;
        emailElement.innerText = `邮箱：${email}`;
    })
    .catch(error => {
        console.log(error.message);
    });

const changePasswordForm = document.querySelector('#change-password-form');
changePasswordForm.addEventListener('submit', function(event) {
    event.preventDefault();
    const oldPassword = document.querySelector('#old-password').value;
    const newPassword = document.querySelector('#new-password').value;
    const confirmPassword = document.querySelector('#confirm-password').value;
    const url = `/users/xiugai?oldpassword=${oldPassword}&newpassword=${newPassword}&realypassword=${confirmPassword}`;
    fetch(url, { method: 'GET' })
        .then(response => {
            if (response.ok) {
                console.log('密码修改成功');
                alert("密码修改成功！")
            } else {
                throw new Error('密码修改失败');
            }
        })
        .catch(error => {
            console.log(error.message);
        });
});
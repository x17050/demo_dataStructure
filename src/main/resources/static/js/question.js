const feedbackForm = document.getElementById('feedback-form');
feedbackForm.addEventListener('submit', function(event) {
    event.preventDefault();
    const problem = document.getElementById('input-problem').value;
    const description = document.getElementById('problem-description').value;
    const type = document.getElementById('problem-type').value;
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const url = `/users/feedback?problem=${problem}&description=${description}&type=${type}&name=${name}&email=${email}`;
    fetch(url, {method: 'GET'})
        .then(response => {
            if (response.ok) {
                alert("提交成功");
                return response.json();
            }
            throw new Error('网络错误');
        })
        .then(data => {
            // 处理响应数据
            console.log(data);
        })
        .catch(error => {
            // 处理异常情况
            console.log(error.message);
        });
});
// 获取页面元素
const usernameSpan = document.querySelector('.username');
// 发送 GET 请求获取用户信息
fetch('/student/name')
    .then(response => response.json())
    .then(data => {
        if (data.state === 200) {
            usernameSpan.textContent = `欢迎，${data.data}同学`;
        } else {
            throw new Error(data.message);
        }
    })
    .catch(error => {
        console.error('获取用户信息失败：', error);
    });

const logoutBtn = document.querySelector('#logout-btn');
logoutBtn.addEventListener('click', () => {
  localStorage.removeItem('token');
  alert('退出登录成功');
  window.location.href = 'login.html#';
});




const contentDivs = document.querySelectorAll('.main-content');
const navItems = document.querySelectorAll('.sidebar li');
function toggleContent(event) {
  event.preventDefault();
  contentDivs.forEach(content => {
    content.style.display = 'none';
  });
  const targetContent = this.dataset.content;
  const targetDiv = document.getElementById(targetContent);
  targetDiv.style.display = 'block';
  navItems.forEach(item => {
    item.classList.remove('active');
  });
  this.classList.add('active');
}

navItems.forEach(item => {
  item.addEventListener('click', toggleContent);
});

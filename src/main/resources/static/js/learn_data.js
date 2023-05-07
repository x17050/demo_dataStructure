fetch('/teacher/list_student')
    .then(response => response.json())
    .then(data => {
        const studentList = data.data;
        const progressList = document.getElementById('progress-list');
        studentList.forEach(student => {
            var tr = document.createElement('tr');
            var nameTd = document.createElement('td');
            nameTd.innerText = student.name;
            var progressTd = document.createElement('td');
            progressTd.innerText = (student.processing !== null && student.processing !== undefined) ? student.processing + '%' : '';
            var remindTd = document.createElement('td');
            var remindBtn = document.createElement('button');
            remindBtn.innerText = student.ad ? '已提醒' : '提醒';
            remindBtn.disabled = !!student.ad;
            remindBtn.addEventListener('click', function() {
                if (!student.ad) {
                    student.ad = '已提醒';
                    remindBtn.innerText = student.ad;
                    remindBtn.disabled = true;
                    sendRemindMessage(student);
                }
            });
            remindTd.appendChild(remindBtn);
            tr.appendChild(nameTd);
            tr.appendChild(progressTd);
            tr.appendChild(remindTd);
            progressList.appendChild(tr);
        });
        document.getElementById('learn_data').style.display = 'block';
    })
    .catch(error => console.error('获取学生列表数据失败：', error));

function sendRemindMessage(student) {
    console.log(student);
    const data = {
        name: student.name,
        processing: student.processing,
        ad: student.ad
    };
    console.log("name:"+data.name+"  processing:"+data.processing+"   ad:"+data.ad)
    fetch('/teacher/remain', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('发送提醒数据失败');
            }
            return response.json();
        })
        .then(data => alert("提醒成功"))
        .catch(error => console.error('发送提醒数据失败：', error));
}
const sectionList = document.getElementById('section-list');
const sections = ['第一章：数据结构绪论', '第二章：线性表', '第三章：栈和队列','第四章：串','第五章：树和二叉树'];
sections.forEach((section) => {
  const listItem = document.createElement('li');
  const link = document.createElement('a');
  link.textContent = section;
  link.href = '#'; // 可根据实际情况更改
  link.addEventListener('click', (event) => {
    event.preventDefault();
    showSectionDetail(section);
  });
  listItem.appendChild(link);
  sectionList.appendChild(listItem);
});
function showSectionDetail(section) {
    const sectionDetail = document.getElementById('section-detail');
    if (section === '第一章：数据结构绪论') {
        sectionDetail.innerHTML = '<h4>第一章：数据结构绪论</h4>' +
            '<p>这里是数据结构绪论的详细介绍</p>' +
            '<p>1.什么是数据结构</p>' +
            '<p>   数据结构是计算机中存储、组织数据的方式，指数据元素之间的逻辑关系和物理关系。</p>' +
            '<p>2.基本概念和术语</p>' +
            '<p>  数据对象、数据元素、数据项、数据类型、抽象数据类型、算法等。</p>';
    } else if (section === '第二章：线性表') {
        sectionDetail.innerHTML = '<h4>第二章：线性表</h4>' +
            '<p>这里是线性表的详细介绍</p>' +
            '<p>1.线性表的定义</p>' +
            '<p>  线性表是具有相同数据类型的 n 个数据元素的有限序列。</p>' +
            '<p>2.线性表的基本操作</p>' +
            '<p>  包括初始化、销毁、清空、判空、求长度、取值、查找、插入、删除等操作。</p>';
    } else if (section === '第三章：栈和队列') {
        sectionDetail.innerHTML = '<h4>第三章：栈和队列</h4>' +
            '<p>这里是栈和队列的详细介绍</p>' +
            '<p>1.栈和队列的定义</p>' +
            '<p>  栈是一种特殊的线性表，只允许在表的一端进行插入和删除操作；队列也是一种特殊的线性表，只允许在表的两端进行插入和删除操作。</p>' +
            '<p>2.栈和队列的基本操作</p>' +
            '<p>  包括进栈、出栈、取栈顶元素、队列的入队、出队、取队头元素等操作。</p>';
    } else if (section === '第四章：串') {
        sectionDetail.innerHTML = '<h4>第四章：串</h4>' +
            '<p>这里是串的详细介绍</p>' +
            '<p>1.串的定义和基本操作</p>' +
            '<p>  串是由零个或多个字符组成的有限序列，基本操作包括串的赋值、比较、连接等操作。</p>' +
            '<p>2.模式匹配算法</p>' +
            '<p>  模式匹配是指在一个文本串中查找一个模式串出现的位置，常用算法有朴素的暴力匹配算法、KMP 算法、Boyer-Moore 算法等。</p>';
    } else if (section === '第五章：树和二叉树') {
        sectionDetail.innerHTML = '<h4>第五章：树和二叉树</h4>' +
            '<p>这里是树和二叉树的详细介绍</p>' +
            '<p>1.树和二叉树的定义</p>' +
            '<p>  树是 n(n>0) 个结点的有限集合，二叉树是一种特殊的树，每个结点最多只有两个子结点。</p>' +
            '<p>2.树的遍历和线索二叉树</p>' +
            '<p>  树的遍历包括前序遍历、中序遍历、后序遍历和层次遍历，线索二叉树是一种改进的二叉树，将空指针指向某种遍历次序下的前驱或后继结点，便于遍历操作。</p>';
    }
}
const form = document.getElementById('upload-form');
const submitBtn = document.getElementById('submit-btn_1');
form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(form);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/teacher/upload');
    xhr.send(formData); // 发送 POST 请求
    console.log("www"+formData);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                const data = JSON.parse(xhr.responseText);
                alert('上传成功');
            } else { // 请求失败

                alert('上传失败,请求未完成');
            }
        }
    };
});
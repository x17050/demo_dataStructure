fetch('/student/allmeg')
    .then(response => response.json())
    .then(data => {
        if (data.data.ad) {
            const confirmStudy = confirm('你的进度太慢，老师提醒你学习');
            if (confirmStudy) {
                data.data.ad = '';
                sendStudentDataToServer(data.data);
            }
        }
    })
    .catch(error => {
        console.error(error);
    });
function sendStudentDataToServer(student) {
    console.log("数据"+JSON.stringify(student));
    const data = {
        name: student.name,
        processing: student.processing,
        ad: student.ad
    };
    console.log("name:" + data.name + "  processing:" + data.processing + "   ad:" + data.ad)
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
        .then(data => alert("收到提醒成功！"))
        .catch(error => console.error('发送提醒数据失败：', error));
}

let progress = 0;
const maxProgress = 100;
const courseId = Math.floor(Math.random() * 1000000);
const courseIdElement = document.getElementById('course-id');
courseIdElement.textContent = courseId;
fetch('/student/teacher')
    .then(response => response.text())
    .then(data => {
        const courseTeacherElement = document.getElementById('course-teacher');
        courseTeacherElement.textContent = data;
    })
    .catch(error => {
        console.error(error);
    });

function updatePageFromServer() {
    const url = `/student/processing`;
    console.log("什么情况嘞"+url);
    fetch(url)
        .then(response => response.json())
        .then(data => {
            progress = data.data;
            updateProgressFromServer(progress);
        })
        .catch(error => {
            console.error(error);
            updateProgressFromServer(null);
        });
}
const chapters = ['第一章：数据结构绪论', '第二章：线性表', '第三章：栈和队列','第四章：串','第五章：树和二叉树'];
const chapterList = document.getElementById('chapter-list');
chapters.forEach(chapter => {
    const listItem = document.createElement('li');
    const link = document.createElement('a');
    link.textContent = chapter;
    link.href = '#';
    link.addEventListener('click', (event) => {
        event.preventDefault();
        if (progress < maxProgress) {
            progress += 20;
            updateProgress();
            if (progress === maxProgress) {
                console.log('Finished learning all chapters.');
            } else {
                sendProgressToServer(progress);
            }
        }
        showChapterDetail(chapter);
    });
    listItem.appendChild(link);
    chapterList.appendChild(listItem);
});
function updateProgress() {
    const progressPercent = document.getElementById('progress-percent');
    progressPercent.textContent = progress;
    const progressBar = document.getElementById('progress');
    progressBar.style.width = `${progress}%`;
}
function sendProgressToServer(progress) {
    const url = `/student/learn?processing=${progress}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
        })
        .catch(error => {
            console.error(error);
        });
}
function updateProgressFromServer(serverProgress) {
    if (serverProgress === undefined || serverProgress === null) {
        progress = 0;
    } else {
        progress = serverProgress;
    }
    updateProgress();
}
function showChapterDetail(chapter) {
    const sectionDetail = document.getElementById('chapter-detail');
    if (chapter === '第一章：数据结构绪论') {
        sectionDetail.innerHTML = '<h4>第一章：数据结构绪论</h4>' +
            '<p>这里是数据结构绪论的详细介绍</p>' +
            '<p>1.什么是数据结构</p>' +
            '<p>   数据结构是计算机中存储、组织数据的方式，指数据元素之间的逻辑关系和物理关系。</p>' +
            '<p>2.基本概念和术语</p>' +
            '<p>  数据对象、数据元素、数据项、数据类型、抽象数据类型、算法等。</p>';
    } else if (chapter === '第二章：线性表') {
        sectionDetail.innerHTML = '<h4>第二章：线性表</h4>' +
            '<p>这里是线性表的详细介绍</p>' +
            '<p>1.线性表的定义</p>' +
            '<p>  线性表是具有相同数据类型的 n 个数据元素的有限序列。</p>' +
            '<p>2.线性表的基本操作</p>' +
            '<p>  包括初始化、销毁、清空、判空、求长度、取值、查找、插入、删除等操作。</p>';
    } else if (chapter === '第三章：栈和队列') {
        sectionDetail.innerHTML = '<h4>第三章：栈和队列</h4>' +
            '<p>这里是栈和队列的详细介绍</p>' +
            '<p>1.栈和队列的定义</p>' +
            '<p>  栈是一种特殊的线性表，只允许在表的一端进行插入和删除操作；队列也是一种特殊的线性表，只允许在表的两端进行插入和删除操作。</p>' +
            '<p>2.栈和队列的基本操作</p>' +
            '<p>  包括进栈、出栈、取栈顶元素、队列的入队、出队、取队头元素等操作。</p>';
    } else if (chapter === '第四章：串') {
        sectionDetail.innerHTML = '<h4>第四章：串</h4>' +
            '<p>这里是串的详细介绍</p>' +
            '<p>1.串的定义和基本操作</p>' +
            '<p>  串是由零个或多个字符组成的有限序列，基本操作包括串的赋值、比较、连接等操作。</p>' +
            '<p>2.模式匹配算法</p>' +
            '<p>  模式匹配是指在一个文本串中查找一个模式串出现的位置，常用算法有朴素的暴力匹配算法、KMP 算法、Boyer-Moore 算法等。</p>';
    } else if (chapter === '第五章：树和二叉树') {
        sectionDetail.innerHTML = '<h4>第五章：树和二叉树</h4>' +
            '<p>这里是树和二叉树的详细介绍</p>' +
            '<p>1.树和二叉树的定义</p>' +
            '<p>  树是 n(n>0) 个结点的有限集合，二叉树是一种特殊的树，每个结点最多只有两个子结点。</p>' +
            '<p>2.树的遍历和线索二叉树</p>' +
            '<p>  树的遍历包括前序遍历、中序遍历、后序遍历和层次遍历，线索二叉树是一种改进的二叉树，将空指针指向某种遍历次序下的前驱或后继结点，便于遍历操作。</p>';
    }
}
const downloadsList = document.getElementById("downloads-list");
function getFileExtension(filename) {
    return filename.split('.').pop();
}
const xhr = new XMLHttpRequest();
xhr.open('GET', '/student/loading');
xhr.onload = function() {
    if (this.status === 200) {
        const data = JSON.parse(this.responseText);
        data.data.forEach(material => {
            const li = document.createElement("li");
            const name = document.createElement("span");
            const type = document.createElement("span");
            const downloadButton = document.createElement("button");
            let previewButton;

            name.textContent = material.name;
            type.textContent = material.type;

            if (getFileExtension(material.content) === 'jpg' || getFileExtension(material.content) === 'png' || getFileExtension(material.content) === 'gif') {
                previewButton = document.createElement("button");
                previewButton.textContent = "预览";
                previewButton.addEventListener("click", () => {
                    const imagePreview = window.open("", "Image Preview");
                    imagePreview.document.write("<img src='http://localhost:8989/files/" + material.content + "'>");
                });
                li.appendChild(previewButton);
            }
            downloadButton.textContent = "下载";
            downloadButton.addEventListener("click", () => {
                const xhrDownload = new XMLHttpRequest();
                xhrDownload.open('GET', 'http://localhost:8989/files/' + material.content, true);
                xhrDownload.responseType = 'blob';
                xhrDownload.onload = function(e) {
                    if (this.status === 200) {
                        const blob = new Blob([this.response], { type: 'application/octet-stream' });
                        const url = URL.createObjectURL(blob);
                        const a = document.createElement('a');
                        a.href = url;
                        a.download = material.content;
                        document.body.appendChild(a);
                        a.click();
                        document.body.removeChild(a);
                    }
                };
                xhrDownload.send();
            });
            li.appendChild(name);
            li.appendChild(type);
            li.appendChild(downloadButton);

            downloadsList.appendChild(li);
        });
    } else {
        console.error('Failed to fetch data: ' + this.statusText);
    }
};
xhr.onerror = function() {
    console.error('Failed to fetch data');
};
xhr.send();
updatePageFromServer();
async function fetchData() {
    try {
        const response = await fetch('/teacher/view_dis');
        const data = await response.json();
        if (data.state === 200) {
            return data.data;
        } else {
            console.error('Error fetching data:', data.message);
            return [];
        }
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}
async function renderDiscussionsAndComments() {
    const discussionListElement = document.getElementById('discussion-list');
    discussionListElement.innerHTML = '';
    console.log("讨论列表"+discussionListElement.textContent);
    const rawData = await fetchData();
    const discussions = rawData.filter(item => item.answer_id === null);
    const comments = rawData.filter(item => item.answer_id !== null);

    discussions.forEach(discussion => {
        const discussionElement = document.createElement('li');
        discussionElement.setAttribute('data-id', discussion.id);
        discussionElement.innerHTML = `<strong>发布老师：${discussion.publisher_id}  </strong>标题：${discussion.title}<em> 问题：${discussion.content}   </em>`;
        discussionElement.style.cursor = 'pointer';

        const commentListElement = document.createElement('ul');
        commentListElement.style.display = 'none';
        const relatedComments = comments.filter(comment => comment.title === discussion.title);
        relatedComments.forEach(comment => {
            const commentElement = document.createElement('li');
            commentElement.setAttribute('data-id', comment.id);
            commentElement.innerHTML = `回答学生：${comment.answer_id}  <em>回答评论：${comment.content}   </em>`;
            const deleteButton = document.createElement('button');
            deleteButton.innerText = '删除';
            deleteButton.addEventListener('click', async () => {
                if (confirm('确定要删除此讨论吗?')) {
                    const response = await fetch(`/teacher/delete_comment?id=${comment.id}`, {
                        method: 'GET'
                    });
                    const data = await response.json();
                    if (data.state === 200) {
                        commentElement.remove();
                    } else {
                        console.error('Error deleting comment:', data.message);
                    }
                }
            });
            commentElement.appendChild(deleteButton);
            const editButton = document.createElement('button');
            editButton.innerText = '修改';
            editButton.addEventListener('click', async () => {
                if (confirm('确定要修改此评论吗?')) {
                    const newContent = prompt('请输入新的评论内容：');
                    if (newContent) {
                        const response = await fetch(`/teacher/edit_comment?id=${comment.id}&content=${newContent}`, {
                            method: 'GET',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                        });
                        const data = await response.json();
                        if (data.state === 200) {
                            comment.content = newContent;
                            commentElement.querySelector('em').innerText = `回答评论：${newContent}`;
                        } else {
                            console.error('Error editing comment:', data.message);
                        }
                    }
                }
            });
            commentElement.appendChild(editButton);
            commentListElement.appendChild(commentElement);
        });


        // 添加删除讨论按钮
        const deleteDiscussionButton = document.createElement('button');
        deleteDiscussionButton.innerText = '删除讨论';
        deleteDiscussionButton.addEventListener('click', async () => {
            if (confirm('删除讨论将会将其下是所有讨论也一起删除，确定要删除此讨论吗?')) {
                const response = await fetch(`/teacher/delete_discussion?id=${discussion.id}`, {
                    method: 'GET'
                });
                const data = await response.json();
                if (data.state === 200) {
                    discussionElement.remove();
                    commentListElement.remove();
                } else {
                    console.error('Error deleting discussion:', data.message);
                }
            }
        });
        discussionElement.appendChild(deleteDiscussionButton);


        // 点击讨论时展开或收起评论列表
        discussionElement.addEventListener('click', () => {
            if (commentListElement.style.display === 'none') {
                commentListElement.style.display = 'block';
            } else {
                commentListElement.style.display = 'none';
            }
        });

        discussionListElement.appendChild(discussionElement);
        discussionListElement.appendChild(commentListElement);
    });
    document.getElementById('discussion-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const title = document.getElementById('title').value;
        const content = document.getElementById('content').value;

        const response = await fetch('/teacher/discussion_tea?title=' + title + '&content=' + content, {
            method: 'GET'
        });
        const data = await response.json();

        if (data.state === 200) {
            await renderDiscussionsAndComments();
            document.getElementById('title').value = '';
            document.getElementById('content').value = '';
        } else {
            console.error('Error adding discussion:', data.message);
        }
    });
}
renderDiscussionsAndComments();
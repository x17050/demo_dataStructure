fetch('/student/disscussionstu')
.then(response => response.json())
.then(data => {
  console.log("数据"+JSON.stringify(data));
  const discussionMap = {};
  data.data.forEach(item => {
    if (item.answer_id === null) {
      discussionMap[item.title] = { discussion: item, comments: [] };
    } else {
      const discussion = discussionMap[item.title];
      if (discussion) {
        discussion.comments.push({...item, content: `${item.answer_id}: ${item.content}`});
      }
    }
  });

  const discussionList = document.getElementById('discussion-list');
  Object.values(discussionMap).forEach(({ discussion }) => {
    const listItem = document.createElement('li');
    const link = document.createElement('a');
    console.log("讨论详情"+JSON.stringify(discussion));
    const publisher = discussion.publisher_id ? `(发布者ID：${discussion.publisher_id})` : '';
    link.textContent = `${discussion.title} ${publisher}`;
    link.href = '#';
    link.addEventListener('click', (event) => {
      event.preventDefault();
      showDiscussion(discussion, discussionMap);
    });
    listItem.appendChild(link);
    discussionList.appendChild(listItem);
  });
})
.catch(error => {
  console.error(error);
});

let currentDiscussion = null;
function showDiscussion(discussion, discussionMap) {
  const discussionTitle = document.getElementById('discussion-title');
  const discussionContent = document.getElementById('discussion-content');
  const commentList = document.getElementById('comment-list');
  const commentForm = document.getElementById('comment-form');

  if (currentDiscussion === discussion) {
    // 隐藏讨论和评论表单
    discussionTitle.textContent = '';
    discussionContent.textContent = '';
    commentList.innerHTML = '';
    commentForm.style.display = 'none';
    currentDiscussion = null;
    return;
  }

  // 显示讨论和评论表单
  discussionTitle.textContent = discussion.title;
  discussionContent.textContent = discussion.content;
  commentList.innerHTML = '';
  const comments = discussionMap[discussion.title].comments;
  if (comments.length > 0) {
    comments.forEach(comment => {
      const commentItem = document.createElement('li');
      commentItem.textContent = comment.content;
      commentList.appendChild(commentItem);
    });
  } else {
    const commentItem = document.createElement('li');
    commentItem.textContent = '暂无评论';
    commentList.appendChild(commentItem);
  }
  commentForm.style.display = 'block';
  // 更新当前讨论
  currentDiscussion = discussion;
}
const commentForm = document.getElementById('comment-form');
const submitBtn = document.getElementById('submit-btn_2');

submitBtn.addEventListener('click', (event) => {
  event.preventDefault();
  const nameInput = document.getElementById('comment-name');
  const contentInput = document.getElementById('comment-content');
  const title = currentDiscussion.title;
  const id = currentDiscussion.publisher_id;
  // 使用 GET 请求将数据发送到后端
  fetch(`/student/submitdis?content=${contentInput.value}&title=${title}&publishId=${id}`)
      .then(response => response.json())
      .then(data => {
        // 使用新评论更新评论列表
        const commentList = document.getElementById('comment-list');
        const commentItem = document.createElement('li');
        commentItem.textContent = `${nameInput.value}: ${contentInput.value}`;
        commentList.appendChild(commentItem);

        // 重置评论表单
        nameInput.value = '';
        contentInput.value = '';
      })
      .catch(error => {
        console.error(error);
      });
});
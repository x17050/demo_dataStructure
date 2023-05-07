// 获取作业数据
$.ajax({
    url: '/student/view_homework_now',
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        if (response.state === 200) {
            var assignments = response.data;
            var $assignmentsList = $('#assignment-list');
            for (var i = 0; i < assignments.length; i++) {
                var assignment = assignments[i];
                var $newAssignment = $('<li>').text(assignment.name).data('assignment', assignment);
                $assignmentsList.append($newAssignment);
            }
            // 为作业列表项添加点击事件处理程序
            $('#assignment-list li').on("click",function() {
                var assignment = $(this).data('assignment');
                $('#assignment-details-container').html('<p>作业名称：' + assignment.name + '</p><p>截止日期：' + assignment.deadline + '</p><p>内容：' + assignment.content + '</p>');
                $('#submit-assignment-form').data('assignment-id', assignment.id);
            });
        } else {
            console.log('获取历史作业失败');
        }
    },
    error: function(xhr, status, error) {
        console.log('获取历史作业时发生错误');
    }
});

// 提交作业
$('#submit-assignment-form').on('submit', function(event) {
    event.preventDefault();
    var homeworkId = $(this).data('assignment-id');
    var content = $('#assignment-content').val();
    $.ajax({
        url: '/student/submit',
        type: 'GET',
        data: {homework_id: homeworkId, content: content},
        dataType: 'json',
        success: function(response) {
            if (response.state === 200) {
                alert('作业提交成功');
            } else {
                alert('作业提交失败');
            }
        },
        error: function(xhr, status, error) {
            alert('作业提交时发生错误');
        }
    });
});

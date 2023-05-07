$.ajax({
    url: '/student/view_homework_histroy',
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        if (response.state === 200) {
            var assignments = response.data;
            var $assignmentsList = $('#assignment-history-list');
            for (var i = 0; i < assignments.length; i++) {
                var assignment = assignments[i];
                var $newAssignment = $('<li>').text('标题:'+assignment.name + '------截止日期:' + assignment.deadline+'------内容：'+assignment.content);
                $assignmentsList.append($newAssignment);
            }
        } else {
            console.log('获取历史作业失败');
        }
    },
    error: function(xhr, status, error) {
        console.log('获取历史作业时发生错误');
    }
});
$.ajax({
    url: '/student/view_homework_now',
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        if (response.state === 200) {
            var assignments = response.data;
            var $assignmentsList = $('#current-assignment-list');
            for (var i = 0; i < assignments.length; i++) {
                var assignment = assignments[i];
                var $newAssignment = $('<li>').text('标题:'+assignment.name + '------截止日期:' + assignment.deadline+'------内容：'+assignment.content);
                $assignmentsList.append($newAssignment);
            }
        } else {
            console.log('获取历史作业失败');
        }
    },
    error: function(xhr, status, error) {
        console.log('获取历史作业时发生错误');
    }
});




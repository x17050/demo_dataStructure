$("#submit_btn_2").on("click",function () {
    $.ajax({
        url: "/teacher/set",
        type: "GET",
        data: {
            title: $("#title_2").val(),
            content: $("#content_1").val(),
            deadline: $("#deadline").val(),
            score: $("#total_score").val()//$("#assignment-form").serialize(),
        },
        dataType: "JSON",
        success: function (json) {
            if (json.state == 200) {
                alert("上传成功");
            } else {
                alert("上传失败");
            }
        },
        error: function (xhr) {
            alert("上传时产生未知的错误!"+xhr.status);
        }
    });
});
$.ajax({
    url: '/teacher/histroy',
    type: 'GET',
    dataType: 'json',
    success: function(response) {
        if (response.state === 200) {
            var assignments = response.data;
            var $assignmentsList = $('#assignments-list_1');
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

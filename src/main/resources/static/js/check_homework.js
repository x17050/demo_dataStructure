const assignmentsList = document.getElementById('assignments-list');
const gradeForm = document.querySelector('.grade-assignments');

function showAssignments(assignments) {
    assignmentsList.innerHTML = '';
    assignments.forEach(assignment => {
        const li = document.createElement('li');
        const score = assignment.score !== null ? `已批改，得分：${assignment.score}` : '未批改';
        li.innerHTML = `<span class="assignment-title">${assignment.student_id}</span> <span class="assignment-content">${assignment.status}</span> <span class="assignment-score">${score}</span> <button class="grade-btn" data-score="${assignment.score}" data-student-id="${assignment.student_id}" data-homework-id="${assignment.homework_id}" data-submit-time="${assignment.submit_time}">批改作业</button>`;
        assignmentsList.appendChild(li);

        const gradeBtn = li.querySelector('.grade-btn');
        if (assignment.score !== null) {
            gradeBtn.disabled = true;
            gradeBtn.textContent = '已批改';
        } else {
            gradeBtn.disabled = false;
            gradeBtn.addEventListener('click', (event) => {
                const homeworkId = event.target.dataset.homeworkId;
                const studentId = event.target.dataset.studentId;
                const title = homeworkId;
                const content = assignment.status;
                const submitTime = event.target.dataset.submitTime;
                const score = event.target.dataset.score;
                document.getElementById('assignment-content').textContent = content;
                document.getElementById('assignment-title').textContent = title;
                document.getElementById('submit-time').textContent = submitTime;
                document.getElementById('grade').value = score === null ? '' : score;
                document.getElementById('student-id').textContent = studentId;
                gradeForm.style.display = 'block';
                gradeForm.addEventListener('submit', (event) => {
                    event.preventDefault();
                    const score = document.getElementById('grade').value;
                    const url = `/teacher/score?score=${score}&studentId=${studentId}&homeworkId=${homeworkId}`;
                    fetch(url, {
                        method: 'GET',
                        headers: {'Content-Type': 'application/json'},
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log('Success:', data);
                            gradeForm.style.display = 'none';
                            const scoreSpan = gradeBtn.parentNode.querySelector('.assignment-score');
                            scoreSpan.textContent = `已批改，得分：${score}`;
                            event.target.dataset.score = score;
                            gradeBtn.disabled = true;
                            gradeBtn.textContent = '已批改';
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                });
            });
        }
    })
}
        function initPage() {
            fetch('/teacher/view')
                .then(response => response.json())
                .then(data => {
                    showAssignments(data.data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
initPage();
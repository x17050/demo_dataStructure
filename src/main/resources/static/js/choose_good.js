fetch('/teacher/list_student')
    .then(response => response.json())
    .then(data => {
        const select = document.getElementById('student-select');
        data.data.forEach(student => {
            const option = document.createElement('option');
            option.value = student.id;
            option.textContent = student.name;
            select.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });

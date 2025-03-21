async function fetchTeachers() {
    try {
        const response = await fetch('/api/v1/teacher');
        if (!response.ok) {
            throw new Error('Fehler beim Laden der Daten');
        }
        const teachers = await response.json();

        const tableBody = document.getElementById('teacherTableBody');
        tableBody.innerHTML = ""; // Tabelle leeren

        teachers.forEach(teacher => {
            const row = `<tr>
                <td>${teacher.id}</td>
                <td>${teacher.surname}</td>
                <td>${teacher.name}</td>
                <td>${teacher.nameshort}</td>
                <td>${teacher.email}</td>
                <td>${teacher.trainingtime}</td>
            </tr>`;
            tableBody.innerHTML += row;
        });
    } catch (error) {
        console.error('Fehler:', error);
    }
}

// Beim Laden der Seite die Lehrer-Daten abrufen
window.onload = fetchTeachers;

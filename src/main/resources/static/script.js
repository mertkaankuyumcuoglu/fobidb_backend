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
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${teacher.id}</td>
                <td>${teacher.surname}</td>
                <td>${teacher.name}</td>
                <td>${teacher.nameshort}</td>
                <td>${teacher.email}</td>
                <td>${teacher.trainingtime}</td>
                <td>
                    <button onclick="deleteTeacher(${teacher.id})" class="btn btn-danger">Löschen</button>
                </td>
            `;

            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error('Fehler:', error);
    }
}

// Funktion zum Löschen eines Lehrers
async function deleteTeacher(id) {
    if (!confirm("Möchtest du diesen Lehrer wirklich löschen?")) {
        return;
    }

    try {
        const response = await fetch(`/api/v1/teacher/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Fehler beim Löschen des Lehrers");
        }

        // Tabelle aktualisieren
        fetchTeachers();
    } catch (error) {
        console.error("Fehler:", error);
    }
}

// Beim Laden der Seite die Lehrer-Daten abrufen
window.onload = fetchTeachers;
